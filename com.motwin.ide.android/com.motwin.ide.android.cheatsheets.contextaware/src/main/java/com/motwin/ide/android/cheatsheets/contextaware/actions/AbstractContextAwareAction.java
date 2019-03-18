/**
 * 
 */
package com.motwin.ide.android.cheatsheets.contextaware.actions;

import com.motwin.ide.android.cheatsheets.actions.AbstractAndroidProjectAction;
import com.motwin.ide.android.cheatsheets.contextaware.internal.ContextAwareCheatSheetPlugin;
import com.motwin.ide.cheatsheets.actions.ISampleAction;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractContextAwareAction extends AbstractAndroidProjectAction implements ISampleAction {

    @Override
    public String getProjectName() {
        return "ContextAware-Android";
    }

    @Override
    public String getPluginID() {
        return ContextAwareCheatSheetPlugin.PLUGIN_ID;
    }

    @Override
    public String getSampleCodePath() {
        return "/code/contextaware";
    }

    @Override
    public String getJavaBasePackage() {
        return "com.motwin.sample.rssreader";
    }
}
