/**
 * 
 */
package com.motwin.ide.android.cheatsheets.actions.newproject;

import com.motwin.ide.android.cheatsheets.actions.AbstractAndroidProjectAction;
import com.motwin.ide.android.cheatsheets.internal.AndroidCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractNewProjectAndroidAction extends AbstractAndroidProjectAction {

    @Override
    public String getProjectName() {
        return "HelloWorld";
    }

    @Override
    public String getPluginID() {
        return AndroidCheatSheetPlugin.PLUGIN_ID;
    }

    @Override
    public String getJavaBasePackage() {
        return "com.example.helloworld";
    }

}
