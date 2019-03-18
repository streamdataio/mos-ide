/**
 * 
 */
package com.motwin.ide.internal.ui.startup;

import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.IStartup;
import org.osgi.service.prefs.BackingStoreException;

import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.motwin.ide.dashboard.ui.DashboardPluginStartup;

/**
 * @author ctranxuan
 * 
 */
public class DashboardStartup implements IStartup {

    private static final String STS_DASHBOARD_PLUGIN_ID                   = "org.springsource.ide.eclipse.dashboard.ui"; //$NON-NLS-1$
    private static final String STS_PREF_OPEN_DASHBOARD_STARTUP           = STS_DASHBOARD_PLUGIN_ID
                                                                              + "dashboard.startup";                    //$NON-NLS-1$

    public static final String  MOTWIN_IDE_DASHBOARD_PLUGIN_ID            = "com.motwin.ide.dashboard.ui";              //$NON-NLS-1$
    public static final String  MOTWIN_IDE_DEFAULT_OPEN_DASHBOARD_STARTUP = MOTWIN_IDE_DASHBOARD_PLUGIN_ID
                                                                              + ".dashboard.startup";                   //$NON-NLS-1$

    @Override
    public void earlyStartup() {
        startDashboard();
    }

    private void startDashboard() {

        IEclipsePreferences dashboardPreference;
        dashboardPreference = InstanceScope.INSTANCE.getNode(MOTWIN_IDE_DASHBOARD_PLUGIN_ID);

        if (dashboardPreference != null
            && dashboardPreference.getBoolean(DashboardConstants.DEFAULT_OPEN_DASHBOARD_STARTUP, true)) {

            disableSTSDashboard();
            DashboardPluginStartup.start();
        }

    }

    /**
     * @throws BackingStoreException
     */
    private void disableSTSDashboard() {
        try {
            IEclipsePreferences stsPreference;
            stsPreference = InstanceScope.INSTANCE.getNode(STS_DASHBOARD_PLUGIN_ID);
            stsPreference.putBoolean(STS_PREF_OPEN_DASHBOARD_STARTUP, false);

            stsPreference.flush();

        } catch (BackingStoreException e) {
            DashboardStartupPlugin
                    .getDefault()
                    .getLog()
                    .log(new Status(Status.ERROR, DashboardStartupPlugin.PLUGIN_ID, "Failed to disable STS dashboard",
                            e));
        }
    }
}
