/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.ui.DashboardConstants;

/**
 * @author ctranxuan
 * 
 */
public class DashboardPreferenceInitializer extends AbstractPreferenceInitializer {

    public DashboardPreferenceInitializer() {
        super();
    }

    @Override
    public void initializeDefaultPreferences() {
        IEclipsePreferences preference;
        preference = DefaultScope.INSTANCE.getNode(DashboardPlugin.PLUGIN_ID);
        preference.putBoolean(DashboardConstants.DEFAULT_OPEN_DASHBOARD_STARTUP, true);
    }

}
