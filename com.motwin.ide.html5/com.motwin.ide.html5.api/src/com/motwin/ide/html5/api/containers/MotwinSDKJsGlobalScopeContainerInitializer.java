/**
 * 
 */
package com.motwin.ide.html5.api.containers;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.api.HTML5APIPlugin;

/**
 * @author fbou
 * 
 */
public class MotwinSDKJsGlobalScopeContainerInitializer extends MotwinJsGlobalScopeContainerInitializer {

    public static final String DESCRIPTION  = "Motwin SDK";
    public static final String CONTAINER_ID = "com.motwin.ide.html5.api.MotwinSDK";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getDescription(IPath aContainerPath, IJavaScriptProject aProject) {
        return DESCRIPTION;
    }

    @Override
    protected String getPluginId() {
        return HTML5APIPlugin.PLUGIN_ID;
    }

    @Override
    protected String getLibraryFile() {
        return "motwin-sdk.js";
    }

    @Override
    public IPath getPath() {
        return new Path(CONTAINER_ID);
    }

}
