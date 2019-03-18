/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.notifmanagerdemo.actions;

import org.eclipse.core.runtime.IPath;

import com.motwin.ide.cheatsheets.actions.ISampleAction;
import com.motwin.ide.server.cheatsheets.actions.AbstractServerProjectAction;
import com.motwin.ide.server.cheatsheets.samples.notifmanagerdemo.internal.NotifManagerDemoCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractNotifManagerDemoAction extends AbstractServerProjectAction implements ISampleAction {

    @Override
    public String getJavaBasePackage() {
        return "com.motwin.application.interceptor";
    }

    @Override
    public String getSampleCodePath() {
        return "/code";
    }

    @Override
    public String getProjectName() {
        return "NotificationManagerDemo-Server";
    }

    @Override
    public String getPluginID() {
        return NotifManagerDemoCheatSheetPlugin.PLUGIN_ID;
    }

    public String getApplicationXml() {
        return getSrcSpringFolder() + IPath.SEPARATOR + "application.xml";
    }

}
