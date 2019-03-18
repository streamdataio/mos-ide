/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.actions.newproject;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.cheatsheets.helpers.ShowLineHelper;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public final class ShowClientChannelConnectAction extends AbstractNewProjectHTML5Action {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new ShowLineHelper(aJsProject.getProject(), getApplicationJSPath(), getPluginID(), JDTUtil.WTP_JSDT_EDITOR_ID)
                .showLineAndLogExceptions(12);
    }

}
