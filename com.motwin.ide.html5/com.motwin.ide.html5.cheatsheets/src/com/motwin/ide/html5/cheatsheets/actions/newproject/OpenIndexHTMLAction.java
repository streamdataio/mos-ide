/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.actions.newproject;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.cheatsheets.operations.OpenHTMLFileInEditorOperation;

/**
 * @author ctranxuan
 * 
 */
public final class OpenIndexHTMLAction extends AbstractNewProjectHTML5Action {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        String path;
        path = getIndexHTMLPath();

        new OpenHTMLFileInEditorOperation(aJsProject.getProject(), path, getPluginID()).run();
    }

}
