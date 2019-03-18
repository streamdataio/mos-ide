/**
 * 
 */
package com.motwin.ide.intro.internal.operations;

import org.eclipse.ui.PlatformUI;

/**
 * @author ctranxuan
 * 
 */
public class ExpandUADashboardPartOperation implements IOperation {
    private final String dashboarPartID;

    /**
     * @param aUADashboardPartID
     */
    public ExpandUADashboardPartOperation(final String aUADashboardPartID) {
        dashboarPartID = aUADashboardPartID;
    }

    @Override
    public void execute() {
        PlatformUI.getPreferenceStore().setValue(dashboarPartID + ".expansion", true);

    }

}
