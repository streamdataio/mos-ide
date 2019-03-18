/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.helloworld.actions;

import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5SampleAction;
import com.motwin.ide.html5.cheatsheets.samples.helloworld.internal.HelloWorldCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractHelloWorld1Action extends AbstractHTML5SampleAction {

    @Override
    public String getProjectName() {
        return "Helloworld-1-HTML5";
    }

    @Override
    public String getPluginID() {
        return HelloWorldCheatSheetPlugin.PLUGIN_ID;
    }

    public String getSampleCodePath() {
        return "code/hello1";
    }
}
