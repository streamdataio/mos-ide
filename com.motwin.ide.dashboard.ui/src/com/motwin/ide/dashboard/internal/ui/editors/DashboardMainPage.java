/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors;

import java.util.Collection;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.motwin.ide.dashboard.ui.editors.AbstractDashboardPage;
import com.motwin.ide.dashboard.ui.editors.AbstractDashboardPart;

/**
 * @author ctranxuan
 * 
 */
public class DashboardMainPage extends AbstractDashboardPage {

    public static final String PAGE_ID           = "com.motwin.ide.dashboard.ui.page.overview";
    public static final String UA_LEFT_LOCATION  = "com.motwin.ide.dashboard.ui.page.overview.left";
    public static final String UA_RIGHT_LOCATION = "com.motwin.ide.dashboard.ui.page.overview.right";

    private FormToolkit        toolkit;
    private ScrolledForm       form;

    /**
     * @param aEditor
     * @param aId
     * @param aTitle
     */
    public DashboardMainPage(final FormEditor aEditor) {
        super(DashboardConstants.DASHBOARD_EXTENSION_POINT_ID, aEditor, PAGE_ID, "Dashboard");
    }

    @Override
    protected void createFormContent(final IManagedForm aManagedForm) {
        toolkit = aManagedForm.getToolkit();
        form = aManagedForm.getForm();

        toolkit.decorateFormHeading(form.getForm());

        GridLayout compositeLayout = new GridLayout(2, true);
        compositeLayout.marginHeight = 0;
        compositeLayout.marginTop = 5;
        compositeLayout.verticalSpacing = 0;

        Composite body = form.getBody();
        body.setLayout(compositeLayout);

        Composite leftComposite = toolkit.createComposite(body);
        leftComposite.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().grab(true, true).applyTo(leftComposite);

        Composite rightComposite = toolkit.createComposite(body);
        rightComposite.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().grab(true, true).applyTo(rightComposite);

        createDashboardParts(leftComposite, UA_LEFT_LOCATION);
        createDashboardParts(rightComposite, UA_RIGHT_LOCATION);
    }

    protected void createDashboardParts(final Composite aParent, final String aLocation) {
        Collection<AbstractDashboardPart> dashboardParts;
        dashboardParts = contributeParts(aParent, aLocation);

        for (AbstractDashboardPart dashboardPart : dashboardParts) {
            dashboardPart.initialize(getManagedForm());
            dashboardPart.createPartContent(aParent);
        }
    }
}
