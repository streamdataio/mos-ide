/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

import com.motwin.ide.dashboard.ui.editors.AbstractDashboardPart;

/**
 * @author ctranxuan
 * 
 */
public class SupportDashboardPart extends AbstractDashboardPart {

    @Override
    public Control createPartContent(final Composite aParent) {
        FormToolkit toolkit;
        toolkit = getToolkit();

        Section section;
        section = toolkit.createSection(aParent, ExpandableComposite.TITLE_BAR);
        section.setText("Support");
        section.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().grab(true, false).applyTo(section);

        Composite composite;
        composite = toolkit.createComposite(section);
        GridLayout layout;
        layout = new GridLayout(2, false);
        layout.horizontalSpacing = 10;
        composite.setLayout(layout);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

        addExternalBrowserLink(composite, "Community Forum", "https://getsatisfaction.com/motwin/");
        addExternalBrowserLink(composite, "Motwin Commercial Support", "http://www.motwin.com/about/contact/");
        addExternalBrowserLink(composite, "Product Page", "http://www.motwin.com/motwin-platform/motwin-platform/");

        section.setClient(composite);
        return section;
    }

    private Hyperlink addExternalBrowserLink(final Composite aParent, final String aTitle, final String aURL) {
        Hyperlink hyperlink;
        hyperlink = getToolkit().createHyperlink(aParent, aTitle, SWT.UNDERLINE_LINK);
        GridDataFactory.fillDefaults().applyTo(hyperlink);
        hyperlink.addHyperlinkListener(new HyperlinkAdapter() {

            @Override
            public void linkActivated(final HyperlinkEvent anEvent) {
                // FIXME isolate the dependency to Mylyn or copy / paste their
                // code
                // TasksUiUtil.openUrl(aURL);
                Program.launch(aURL);
            }
        });
        return hyperlink;
    }
}
