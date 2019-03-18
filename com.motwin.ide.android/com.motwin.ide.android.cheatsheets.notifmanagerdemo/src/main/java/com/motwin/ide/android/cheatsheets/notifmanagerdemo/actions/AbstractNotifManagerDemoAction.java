/**
 * 
 */
package com.motwin.ide.android.cheatsheets.notifmanagerdemo.actions;

import com.motwin.ide.android.cheatsheets.actions.AbstractAndroidProjectAction;
import com.motwin.ide.android.cheatsheets.notifmanagerdemo.internal.NotifManagerDemoCheatSheetPlugin;
import com.motwin.ide.cheatsheets.actions.ISampleAction;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractNotifManagerDemoAction extends AbstractAndroidProjectAction implements ISampleAction {

    private static final String NOTIF_MANAGER_DEMO_APPLICATION_PROJECT_NAME = "NotificationManagerDemo-Android";

    private static final String SAMPLE_PROJECT_LOCATION_PATH                = "/code/notifmanagerdemo";

    private static final String SAMPLE_BASE_PACKAGE                         = "com.motwin.demo.notifmanager";

    @Override
    public String getProjectName() {
        return NOTIF_MANAGER_DEMO_APPLICATION_PROJECT_NAME;
    }

    @Override
    public String getPluginID() {
        return NotifManagerDemoCheatSheetPlugin.PLUGIN_ID;
    }

    @Override
    public String getSampleCodePath() {
        return SAMPLE_PROJECT_LOCATION_PATH;
    }

    @Override
    public String getJavaBasePackage() {
        return SAMPLE_BASE_PACKAGE;
    }
}
