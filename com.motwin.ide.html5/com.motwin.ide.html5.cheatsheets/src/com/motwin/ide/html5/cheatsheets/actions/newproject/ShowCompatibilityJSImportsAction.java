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
public final class ShowCompatibilityJSImportsAction extends AbstractNewProjectHTML5Action {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new ShowLineHelper(aJsProject.getProject(), getIndexHTMLPath(), getPluginID(), JDTUtil.WTP_HTML_EDITOR_ID)
                .showLineAndLogExceptions(7);
    }

}
