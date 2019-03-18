/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractHTML5ProjectAction extends Action implements ICheatSheetAction {

    @Override
    public void run(final String[] aParams, final ICheatSheetManager aManager) {
        IWorkspaceRoot root;
        root = ResourcesPlugin.getWorkspace().getRoot();

        String projectName;
        projectName = getProjectName();

        IProject project;
        project = root.getProject(projectName);

        IJavaScriptProject jsProject;
        jsProject = JavaScriptCore.create(project);

        perform(jsProject);
    }

    protected abstract void perform(IJavaScriptProject aJsProject);

    public abstract String getProjectName();

    public abstract String getPluginID();

    public String getWWWFolder() {
        return "www";
    }

    public String getIndexHTML() {
        return "index.html";
    }

    public String getIndexHTMLPath() {
        return getWWWFolder() + IPath.SEPARATOR + getIndexHTML();
    }

    public String getApplicationJS() {
        return "application.js";
    }

    public String getApplicationJSPath() {
        return getWWWFolder() + IPath.SEPARATOR + getApplicationJS();
    }
}
