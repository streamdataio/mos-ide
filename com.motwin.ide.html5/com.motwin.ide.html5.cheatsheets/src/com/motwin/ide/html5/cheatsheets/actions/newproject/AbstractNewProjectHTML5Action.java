/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.actions.newproject;

import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5ProjectAction;
import com.motwin.ide.html5.cheatsheets.internal.HTML5CheatSheetsPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractNewProjectHTML5Action extends AbstractHTML5ProjectAction {

    @Override
    public String getProjectName() {
        return "HelloWorld";
    }

    @Override
    public String getPluginID() {
        return HTML5CheatSheetsPlugin.PLUGIN_ID;
    }

}
