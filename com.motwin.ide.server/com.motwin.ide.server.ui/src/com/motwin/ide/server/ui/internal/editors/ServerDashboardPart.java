/**
 * 
 */
package com.motwin.ide.server.ui.internal.editors;

import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.motwin.ide.dashboard.ui.editors.AbstractUADashboardPart;

/**
 * @author ctranxuan
 * 
 */
public class ServerDashboardPart extends AbstractUADashboardPart {

    public ServerDashboardPart() {
        super(DashboardConstants.DASHBOARD_EXTENSION_POINT_ID, "Motwin Server");
    }
}
