package com.motwin.ide.dashboard.internal.ui.preferences;

import static com.motwin.ide.dashboard.ui.DashboardConstants.DEFAULT_OPEN_DASHBOARD_STARTUP;
import static com.motwin.ide.dashboard.ui.DashboardConstants.RSS_ALL_PRODUCTS;
import static com.motwin.ide.dashboard.ui.DashboardConstants.RSS_AVAILABLE_PRODUCTS_LIST;
import static com.motwin.ide.dashboard.ui.DashboardConstants.RSS_PRODUCTS_PREFERENCES_PREFIX;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.internal.ui.editors.rss.FeedCategory;
import com.motwin.ide.dashboard.internal.ui.editors.rss.FeedsUtil;

public class DashboardPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
    private final List<String>        gsProducts;

    private Button                    showOnStartupButton;
    private final Map<String, Button> gsProductButtons;

    public DashboardPreferencePage() {
        gsProducts = FeedsUtil.getAvailableGSProductIDs();
        gsProductButtons = Maps.newHashMapWithExpectedSize(gsProducts.size());
    }

    @Override
    public void init(final IWorkbench aWorkbench) {
    }

    @Override
    protected Control createContents(final Composite aParent) {
        Composite composite;
        composite = new Composite(aParent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));

        createShowOnStartupButton(composite);
        createFeedFilters(composite);
        return composite;
    }

    private void createFeedFilters(final Composite aParent) {
        Group group;
        group = new Group(aParent, SWT.SHADOW_ETCHED_IN);
        group.setText("Feeds categories");
        group.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().span(2, 2).applyTo(group);

        for (String gsProduct : gsProducts) {
            if (FeedCategory.contains(gsProduct)) {
                createGSProductButton(group, gsProduct);

            }
        }
    }

    /**
     * @param aParent
     * @param aGSProductID
     */
    private void createGSProductButton(final Group aParent, final String aGSProductID) {
        Button button;
        button = new Button(aParent, SWT.CHECK);
        button.setText(FeedCategory.get(aGSProductID).getLabel());
        button.setData(aGSProductID);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(button);

        IPreferenceStore preferenceStore;
        preferenceStore = getPreferenceStore();

        boolean isAllSelected;
        isAllSelected = preferenceStore.getBoolean(getTagPreferenceKey(RSS_ALL_PRODUCTS));

        if (isAllSelected && !RSS_ALL_PRODUCTS.equals(aGSProductID)) {
            button.setSelection(!isAllSelected);

        } else {
            button.setSelection(preferenceStore.getBoolean(getTagPreferenceKey(aGSProductID)));

        }

        button.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(final SelectionEvent anEvent) {
                selectButton(anEvent);
            }

            @Override
            public void widgetDefaultSelected(final SelectionEvent anEvent) {
                selectButton(anEvent);

            }

            private void selectButton(final SelectionEvent anEvent) {
                Button button;
                button = (Button) anEvent.getSource();

                if (button.getSelection()) {

                    if (RSS_ALL_PRODUCTS.equals(button.getData())) {
                        deselectAllButNotAllTagButton();

                    } else {
                        gsProductButtons.get(RSS_ALL_PRODUCTS).setSelection(false);

                    }
                }
            }

        });

        gsProductButtons.put(aGSProductID, button);
    }

    private void createShowOnStartupButton(final Composite aParent) {
        showOnStartupButton = new Button(aParent, SWT.CHECK);
        showOnStartupButton.setText("Show Dashboard On Startup");
        GridDataFactory.fillDefaults().span(2, 1).applyTo(showOnStartupButton);
        showOnStartupButton.setSelection(getPreferenceStore().getBoolean(DEFAULT_OPEN_DASHBOARD_STARTUP));
    }

    @Override
    protected void performDefaults() {
        IPreferenceStore preferenceStore;
        preferenceStore = getPreferenceStore();

        showOnStartupButton.setSelection(true);
        preferenceStore.setValue(DEFAULT_OPEN_DASHBOARD_STARTUP, showOnStartupButton.getSelection());

        Collection<Button> buttons;
        buttons = gsProductButtons.values();

        for (Button button : buttons) {
            if (RSS_ALL_PRODUCTS.equals(button.getData())) {
                button.setSelection(true);

            } else {
                button.setSelection(false);

            }

            storeTagPreference(button);
        }

        storeAvailableTags();

        super.performDefaults();
    }

    @Override
    public boolean performOk() {
        IPreferenceStore preferenceStore;
        preferenceStore = getPreferenceStore();

        preferenceStore.setValue(DEFAULT_OPEN_DASHBOARD_STARTUP, showOnStartupButton.getSelection());

        Collection<Button> buttons;
        buttons = gsProductButtons.values();

        for (Button button : buttons) {
            storeTagPreference(button);
        }

        return super.performOk();
    }

    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return DashboardPlugin.getDefault().getPreferenceStore();
    }

    private void deselectAllButNotAllTagButton() {
        Button allTagButton;
        allTagButton = gsProductButtons.get(RSS_ALL_PRODUCTS);

        Collection<Button> otherButtons;
        otherButtons = gsProductButtons.values();

        for (Button button : otherButtons) {
            if (button != allTagButton) {
                button.setSelection(false);

            }
        }
    }

    private String getTagPreferenceKey(final String aTag) {
        Preconditions.checkNotNull(aTag, "aTag cannot be null");
        return RSS_PRODUCTS_PREFERENCES_PREFIX + aTag;
    }

    private void storeAvailableTags() {
        getPreferenceStore().setValue(RSS_AVAILABLE_PRODUCTS_LIST, Joiner.on(",").join(gsProducts));
    }

    private void storeTagPreference(final Button aTagButton) {
        Preconditions.checkNotNull(aTagButton, "aTagButton cannot be null");
        Preconditions.checkNotNull(aTagButton.getData(), "aTagButton.getData() cannot be null");

        String key;
        key = FeedsUtil.computeGSProductPreference(aTagButton.getData().toString());

        boolean selection;
        selection = aTagButton.getSelection();

        getPreferenceStore().setValue(key, selection);
    }
}
