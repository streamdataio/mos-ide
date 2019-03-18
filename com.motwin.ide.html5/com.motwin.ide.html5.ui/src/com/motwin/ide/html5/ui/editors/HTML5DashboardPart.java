/**
 * 
 */
package com.motwin.ide.html5.ui.editors;

import org.eclipse.swt.widgets.Composite;

import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.motwin.ide.dashboard.ui.editors.AbstractUADashboardPart;
import com.motwin.ide.dashboard.ui.editors.UALinkDescriptor.Type;

/**
 * @author ctranxuan
 * 
 */
public final class HTML5DashboardPart extends AbstractUADashboardPart {

    public HTML5DashboardPart() {
        super(DashboardConstants.DASHBOARD_EXTENSION_POINT_ID, "Motwin HTML5 SDK");
    }

    @Override
    protected void createSubParts(final Composite aComposite) {
        createNewProjectsSubPart(aComposite);
        createExportsSubPart(aComposite);
        createSamplesSubPart(aComposite);
        createCheatSheetsSubPart(aComposite);
        createDocsSubPart(aComposite);
    }

    protected void createExportsSubPart(final Composite aParent) {
        createSubPart(aParent, "Export", Type.EXPORT, 1);
    }

}
