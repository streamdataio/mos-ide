/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.realtimepush.actions;

import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5SampleAction;
import com.motwin.ide.html5.cheatsheets.samples.realtimepush.internal.RealTimePushCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractRealTimePushAction extends AbstractHTML5SampleAction {

    @Override
    public String getSampleCodePath() {
        return "code/realtimepush";
    }

    @Override
    public String getProjectName() {
        return "RealTimePush-HTML5";
    }

    @Override
    public String getPluginID() {
        return RealTimePushCheatSheetPlugin.PLUGIN_ID;
    }

}
