/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.helloworld.server.actions;

import org.eclipse.core.runtime.IPath;

import com.motwin.ide.cheatsheets.actions.ISampleAction;
import com.motwin.ide.server.cheatsheets.actions.AbstractServerProjectAction;
import com.motwin.ide.server.cheatsheets.samples.helloworld.HelloWorldCheatSheetActivator;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractHelloWorldAction extends AbstractServerProjectAction implements ISampleAction {

    private static final String HELLO_WORLD_APPLICATION_PROJECT_NAME = "HelloWorld-Server";

    private static final String SAMPLE_PROJECT_LOCATION_PATH         = "/code/server";

    private static final String SAMPLE_BASE_PACKAGE                  = "com.motwin.application.helloworld";

    @Override
    public String getProjectName() {
        return HELLO_WORLD_APPLICATION_PROJECT_NAME;
    }

    @Override
    public String getPluginID() {
        return HelloWorldCheatSheetActivator.PLUGIN_ID;
    }

    @Override
    public String getSampleCodePath() {
        return SAMPLE_PROJECT_LOCATION_PATH;
    }

    @Override
    public String getJavaBasePackage() {
        return SAMPLE_BASE_PACKAGE;
    }

    public String getApplicationXml() {
        return getSrcSpringFolder() + IPath.SEPARATOR + "application.xml";
    }

}
