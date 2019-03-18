/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.mylyn.commons.ui.CommonImages;
import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.osgi.service.prefs.BackingStoreException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.internal.ui.editors.rss.AggregateFeedsJob;
import com.motwin.ide.dashboard.internal.ui.editors.rss.FeedDataSource;
import com.motwin.ide.dashboard.internal.ui.editors.rss.FeedsProvider;
import com.motwin.ide.dashboard.internal.ui.editors.rss.FeedsReader;
import com.motwin.ide.dashboard.internal.ui.editors.rss.FeedsUtil;
import com.motwin.ide.dashboard.internal.ui.editors.rss.IFeedDataSourceListener;
import com.motwin.ide.dashboard.internal.ui.editors.rss.SyndEntryComparator;
import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.motwin.ide.dashboard.ui.DashboardImages;
import com.motwin.ide.dashboard.ui.editors.AbstractDashboardPart;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author ctranxuan
 * 
 */
public class RSSDashboardPart extends AbstractDashboardPart implements IFeedDataSourceListener {
    private static final String          DISCLAIMER_PAGE                = "disclaimer_page";
    private static final String          FEEDS_PAGE                     = "feeds_page";
    public static final String           RESOURCE_DASHBOARD_FEEDS_BLOGS = "dashboard.feeds.blogs";
    public static final String           PREF_FEED_ENTRY_READ_STATE     = DashboardPlugin.PLUGIN_ID
                                                                            + ".feed.item.state";

    private final Set<AggregateFeedsJob> unfinishedJobs;

    private static final String          ICON_BLOG_INCOMING             = "icons/obj16/overlay-incoming.png";
    private static final String          ICON_BLOG_BLANK                = "icons/obj16/blank.png";

    private static final int             FEEDS_TEXT_WRAP_INDENT         = 80;
    private static final int             UPDATE_INDENTATION             = 22;
    private static final int             FEEDS_DESCRIPTION_MAX          = 200;

    private static final String          PROXY_PREF_PAGE_ID             = "org.eclipse.ui.net.NetPreferences";

    private final Set<Control>           feedControls;
    private final List<SyndEntry>        displayedEntries;

    private Action                       refreshAction;
    private ScrolledPageBook             pageBook;
    private Composite                    feedsComposite;

    public RSSDashboardPart() {
        feedControls = Sets.newHashSet();
        displayedEntries = Lists.newArrayList();
        unfinishedJobs = new CopyOnWriteArraySet<AggregateFeedsJob>();
        FeedsProvider.INSTANCE.addDataSourceListener(this);
    }

    @Override
    public Control createPartContent(final Composite aParent) {
        FormToolkit toolkit;
        toolkit = getToolkit();

        final Section section;
        section = toolkit.createSection(aParent, ExpandableComposite.TITLE_BAR);
        section.setText(DashboardConstants.RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME);
        section.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().grab(true, false).hint(50, 300).applyTo(section);

        final ScrolledForm form;
        form = getManagedForm().getForm();
        form.addControlListener(new ControlAdapter() {
            @Override
            public void controlResized(final ControlEvent anEvent) {
                GridData data;
                data = (GridData) section.getLayoutData();

                data.heightHint = form.getSize().y - FEEDS_TEXT_WRAP_INDENT;
            }
        });

        createToolbar(section);
        createPageBook(section);

        section.setClient(pageBook);

        refreshAction.run();
        return section;
    }

    private void createToolbar(final Section aSection) {
        FormToolkit toolkit;
        toolkit = getToolkit();

        Composite headerComposite;
        headerComposite = toolkit.createComposite(aSection, SWT.NONE);

        RowLayout rowLayout;
        rowLayout = new RowLayout();
        rowLayout.marginTop = 0;
        rowLayout.marginBottom = 0;
        headerComposite.setLayout(rowLayout);
        headerComposite.setBackground(null);

        ToolBarManager toolBarManager;
        toolBarManager = new ToolBarManager(SWT.FLAT);
        toolBarManager.createControl(headerComposite);
        aSection.setTextClient(headerComposite);

        Action configureFeedsAction = new Action("Configure", DashboardImages.RSS_CONFIGURE) {

            @Override
            public void run() {
                PreferenceDialog dialog;
                dialog = PreferencesUtil.createPreferenceDialogOn(getManagedForm().getForm().getShell(),
                        DashboardConstants.DASHBOARD_PREFERENCE_PAGE_ID,
                        new String[] { DashboardConstants.DASHBOARD_PREFERENCE_PAGE_ID }, null);
                dialog.open();
            }
        };

        refreshAction = new Action("Refresh feeds", CommonImages.REFRESH) {
            @Override
            public void run() {
                FeedDataSource feedDataSource;
                feedDataSource = FeedsProvider.INSTANCE
                        .getDataSource(DashboardConstants.RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME);

                displayFeeds(feedDataSource, aSection);
            }
        };

        toolBarManager.add(configureFeedsAction);
        toolBarManager.add(refreshAction);
        toolBarManager.update(true);
    }

    private void createPageBook(final Section aSection) {
        FormToolkit toolkit;
        toolkit = getToolkit();

        pageBook = toolkit.createPageBook(aSection, SWT.V_SCROLL);
        pageBook.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().grab(true, true).applyTo(pageBook);

        createFeedPage(aSection);
        createDisclaimerPage();

    }

    private void createDisclaimerPage() {
        Composite disclaimerComposite;
        disclaimerComposite = pageBook.createPage(DISCLAIMER_PAGE);
        disclaimerComposite.setLayout(new TableWrapLayout());
        GridDataFactory.fillDefaults().grab(true, false).applyTo(disclaimerComposite);

        FormText disclaimer;
        disclaimer = getToolkit().createFormText(disclaimerComposite, true);
        disclaimer
                .setText(
                        "<form><p>No entries found. Ensure <a href=\"proxy\">firewall and proxy settings</a> are appropriately configured.</p></form>",
                        true, false);
        disclaimer.addHyperlinkListener(new HyperlinkAdapter() {
            @Override
            public void linkActivated(final HyperlinkEvent e) {
                if ("proxy".equals(e.data)) {
                    PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PROXY_PREF_PAGE_ID, null,
                            null);
                    dialog.open();
                }
            }
        });
    }

    private void createFeedPage(final Section aSection) {
        final ScrolledForm form;
        form = getManagedForm().getForm();

        feedsComposite = pageBook.createPage(FEEDS_PAGE);
        feedsComposite.setLayout(new TableWrapLayout());
        GridDataFactory.fillDefaults().grab(true, false).applyTo(feedsComposite);
        feedsComposite.addPaintListener(new PaintListener() {

            @Override
            public void paintControl(final PaintEvent e) {
                GridData data = (GridData) feedsComposite.getLayoutData();
                data.widthHint = aSection.getSize().x - FEEDS_TEXT_WRAP_INDENT;
                data.heightHint = form.getSize().y - 50;
            }
        });

        aSection.addControlListener(new ControlAdapter() {

            @Override
            public void controlResized(final ControlEvent e) {
                GridData data = (GridData) pageBook.getLayoutData();
                data.heightHint = form.getSize().y - 50;
                data.grabExcessVerticalSpace = false;
                pageBook.setSize(aSection.getSize().x - 40, form.getSize().y - 50);

                for (Control feedControl : feedControls) {
                    ((TableWrapData) feedControl.getLayoutData()).maxWidth = aSection.getSize().x
                        - FEEDS_TEXT_WRAP_INDENT;
                }

                feedsComposite.pack();
            };
        });
    }

    private void displayFeeds(final FeedDataSource aFeedDataSource, final Section aSection) {
        Preconditions.checkNotNull(aFeedDataSource, "aFeedDataSource cannot be null");
        Preconditions.checkNotNull(aSection, "aSection cannot be null");

        final AggregateFeedsJob job;
        job = new AggregateFeedsJob(aFeedDataSource);
        job.addJobChangeListener(new JobChangeAdapter() {

            @Override
            public void done(final IJobChangeEvent aEvent) {
                unfinishedJobs.remove(job);
                displayFeedsAsync(job.getFeedsReader(), aSection);
            }
        });

        unfinishedJobs.add(job);
        job.schedule();
    }

    private void displayFeedsAsync(final FeedsReader aFeedsReader, final Section aSection) {
        Preconditions.checkNotNull(aFeedsReader, "aFeedsReader cannot be null");
        Preconditions.checkNotNull(aSection, "aSection cannot be null");

        ScrolledForm dashboardForm;
        dashboardForm = getManagedForm().getForm();

        if (!dashboardForm.isDisposed()) {
            // dashboardForm.getShell().getDisplay()
            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    Collection<SyndFeed> feeds;
                    feeds = aFeedsReader.getFeeds(DashboardConstants.RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME,
                            new NullProgressMonitor());

                    displayFeeds(feeds, aSection);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void displayFeeds(final Collection<SyndFeed> aFeeds, final Section aSection) {
        Preconditions.checkNotNull(aFeeds, "aFeeds cannot be null");
        Preconditions.checkNotNull(aSection, "aSection cannot be null");

        Set<SyndEntry> entries;
        entries = Sets.newTreeSet(new SyndEntryComparator());

        for (SyndFeed feed : aFeeds) {
            entries.addAll(feed.getEntries());
        }

        if (entries.isEmpty()) {
            displayedEntries.clear();
            pageBook.showPage(DISCLAIMER_PAGE);

        } else /*
                * if (!(displayedEntries.containsAll(anEntries) &&
                * anEntries.containsAll(displayedEntries)))
                */{
            displayedEntries.clear();
            displayedEntries.addAll(entries);

            Control[] children;
            children = feedsComposite.getChildren();

            int counter = 0;
            for (SyndEntry entry : entries) {
                displayFeed(entry, feedsComposite, aSection, counter, children);
                counter++;
            }

            for (int i = counter * 2; i < children.length; i++) {
                children[i].dispose();
            }

            pageBook.showPage(FEEDS_PAGE);
        }
    }

    private void displayFeed(final SyndEntry anEntry, final Composite aParent, final Section aSection,
                             final int aPosition, final Control[] aChildren) {
        Preconditions.checkNotNull(anEntry, "anEntry cannot be null");
        Preconditions.checkNotNull(aParent, "aParent cannot be null");
        Preconditions.checkNotNull(aSection, "aSection cannot be null");
        Preconditions.checkNotNull(aChildren, "aChildren cannot be null");

        ImageHyperlink link;
        link = null;

        FormText text;
        text = null;

        if (aPosition < aChildren.length / 2) {
            link = (ImageHyperlink) aChildren[aPosition * 2];
            link.setVisible(true);

            text = (FormText) aChildren[aPosition * 2 + 1];
            text.setVisible(true);

        } else {
            link = createFeedHyperLink(aParent);
            final TableWrapData data = new TableWrapData();
            data.indent = UPDATE_INDENTATION;
            data.maxWidth = aSection.getSize().x - FEEDS_TEXT_WRAP_INDENT;
            data.grabVertical = true;

            text = new FormText(aParent, SWT.WRAP | SWT.MULTI | SWT.NO_BACKGROUND | SWT.NO_FOCUS);
            text.setHyperlinkSettings(getToolkit().getHyperlinkGroup());
            text.setBackground(getToolkit().getColors().getBackground());
            text.setLayoutData(data);
            text.addPaintListener(new PaintListener() {
                @Override
                public void paintControl(final PaintEvent e) {
                    data.maxWidth = aSection.getSize().x - FEEDS_TEXT_WRAP_INDENT;
                }
            });
            feedControls.add(text);
        }

        TableWrapData linkData;
        linkData = new TableWrapData();

        if (!getPreferenceStore().getBoolean(PREF_FEED_ENTRY_READ_STATE + ":" + anEntry.getLink())) {
            link.setImage(DashboardImages.INSTANCE.getImage(ICON_BLOG_INCOMING));
            linkData.indent = 0;

        } else {
            linkData.indent = UPDATE_INDENTATION - 1;

        }
        link.setText(FeedsUtil.removeHtmlEntities(anEntry.getTitle()));
        link.setLayoutData(linkData);
        link.setData(anEntry);

        String description;
        description = trimText(FeedsUtil.getDescription(anEntry));
        text.setText(description, false, false);
    }

    private IPreferenceStore getPreferenceStore() {
        return DashboardPlugin.getDefault().getPreferenceStore();
    }

    private ImageHyperlink createFeedHyperLink(final Composite aParent) {
        Preconditions.checkNotNull(aParent, "aParent cannot be null");

        final ImageHyperlink hyperLink;
        hyperLink = getToolkit().createImageHyperlink(aParent, SWT.UNDERLINE_LINK);
        feedControls.add(hyperLink);

        hyperLink.addHyperlinkListener(new HyperlinkAdapter() {
            @Override
            public void linkActivated(final HyperlinkEvent anEvent) {
                try {
                    Object source;
                    source = anEvent.getSource();

                    if (source instanceof ImageHyperlink && ((ImageHyperlink) source).getData() != null) {
                        SyndEntry entry = (SyndEntry) ((ImageHyperlink) source).getData();
                        String url = entry.getLink();

                        if (url != null) {

                            int urlPos;
                            urlPos = url.indexOf("?");

                            String newUrl;
                            newUrl = null;
                            if (urlPos > 0) {
                                newUrl = new String(url.substring(0, urlPos + 1))
                                    + new String(url.substring(urlPos + 1)).replaceAll("\\?", "&");

                            } else {
                                newUrl = url;

                            }

                            TasksUiUtil.openUrl(url);
                            getPreferenceStore().setValue(PREF_FEED_ENTRY_READ_STATE + ":" + newUrl, true);
                            InstanceScope.INSTANCE.getNode(DashboardPlugin.PLUGIN_ID).flush();
                            hyperLink.setImage(DashboardImages.INSTANCE.getImage(ICON_BLOG_BLANK));
                        }
                    }
                } catch (BackingStoreException e) {
                    StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
                            "An unexpected error occurred while saving feed preferences.", e));

                }
            }
        });

        return hyperLink;
    }

    private String trimText(final String aString) {
        Preconditions.checkNotNull(aString, "aString cannot be null");

        String result;
        result = null;

        // Remove html encoded entities
        String text;
        text = StringEscapeUtils.unescapeHtml(aString);

        // Remove line breaks and tabs
        text = aString.replaceAll("\n\t", " ");

        if (text.length() > FEEDS_DESCRIPTION_MAX) {
            int index;
            index = text.indexOf(' ', FEEDS_DESCRIPTION_MAX);

            if (index > 0) {
                result = new String(text.substring(0, index)) + "...";

            } else {
                result = new String(text.substring(0, FEEDS_DESCRIPTION_MAX));

            }

        } else {
            result = text;

        }

        return result;
    }

    public void cancelUnfinishedJobs() {
        for (AggregateFeedsJob job : unfinishedJobs) {
            job.cancel();
        }
        unfinishedJobs.clear();
    }

    @Override
    public void dispose() {
        FeedsProvider.INSTANCE.removeDataSourceListener(this);
        super.dispose();
    }

    @Override
    public void dataSourceChanged(final DataSourceChangedEvent anEvent) {
        Preconditions.checkNotNull(anEvent, "anEvent cannot be null");
        FeedDataSource dataSource;
        dataSource = anEvent.getFeedDataSource();

        if (DashboardConstants.RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME.equals(dataSource.getName())) {
            refreshAction.run();
        }
    }

}
