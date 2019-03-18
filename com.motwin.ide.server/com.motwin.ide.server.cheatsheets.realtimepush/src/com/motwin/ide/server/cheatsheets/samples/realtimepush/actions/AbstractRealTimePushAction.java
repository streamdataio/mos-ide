/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.realtimepush.actions;

import org.eclipse.core.runtime.IPath;

import com.motwin.ide.cheatsheets.actions.ISampleAction;
import com.motwin.ide.server.cheatsheets.actions.AbstractServerProjectAction;
import com.motwin.ide.server.cheatsheets.samples.realtimepush.internal.RealTimePushCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractRealTimePushAction extends AbstractServerProjectAction implements ISampleAction {

    @Override
    public String getJavaBasePackage() {
        return "com.motwin.realTimePush.connectors";
    }

    @Override
    public String getSampleCodePath() {
        return "/code";
    }

    @Override
    public String getProjectName() {
        return "RealTimePush-Server";
    }

    @Override
    public String getPluginID() {
        return RealTimePushCheatSheetPlugin.PLUGIN_ID;
    }

    public String getApplicationXml() {
        return getSrcSpringFolder() + IPath.SEPARATOR + "application.xml";
    }

    public String getVirtualDbXml() {
        return getSrcSpringFolder() + IPath.SEPARATOR + "virtualdb.xml";
    }
}
