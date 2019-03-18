/**
 * 
 */
package com.motwin.ide.android.cheatsheets.samples.helloworld.actions;

import com.motwin.ide.android.cheatsheets.actions.AbstractAndroidProjectAction;
import com.motwin.ide.android.cheatsheets.samples.helloworld.internal.HelloWorldCheatSheetPlugin;
import com.motwin.ide.cheatsheets.actions.ISampleAction;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractHelloWorld1Action extends AbstractAndroidProjectAction implements ISampleAction {

    private static final String HELLO_WORLD_APPLICATION_PROJECT_NAME = "HelloWorld1-Android";

    private static final String SAMPLE_PROJECT_LOCATION_PATH         = "/code/hello1";

    private static final String SAMPLE_BASE_PACKAGE                  = "com.motwin.android.sample";

    @Override
    public String getProjectName() {
        return HELLO_WORLD_APPLICATION_PROJECT_NAME;
    }

    @Override
    public String getPluginID() {
        return HelloWorldCheatSheetPlugin.PLUGIN_ID;
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
