/**
 * 
 */
package com.motwin.ide.android.cheatsheets.realtimepush.actions;

import com.motwin.ide.android.cheatsheets.actions.AbstractAndroidProjectAction;
import com.motwin.ide.android.cheatsheets.realtimepush.internal.RealTimePushCheatSheetPlugin;
import com.motwin.ide.cheatsheets.actions.ISampleAction;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractRealTimePushAction extends AbstractAndroidProjectAction implements ISampleAction {

    @Override
    public String getJavaBasePackage() {
        return "com.motwin.sample.realTimePush";
    }

    @Override
    public String getProjectName() {
        return "RealTimePush-Android";
    }

    @Override
    public String getPluginID() {
        return RealTimePushCheatSheetPlugin.PLUGIN_ID;
    }

    @Override
    public String getSampleCodePath() {
        return "/code/realtimepush";
    }

}
