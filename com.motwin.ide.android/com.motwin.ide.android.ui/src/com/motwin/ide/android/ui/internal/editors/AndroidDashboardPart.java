/**
 * 
 */
package com.motwin.ide.android.ui.internal.editors;

import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.motwin.ide.dashboard.ui.editors.AbstractUADashboardPart;

/**
 * @author ctranxuan
 * 
 */
public final class AndroidDashboardPart extends AbstractUADashboardPart {

    public AndroidDashboardPart() {
        super(DashboardConstants.DASHBOARD_EXTENSION_POINT_ID, "Motwin Android SDK");
    }
}
