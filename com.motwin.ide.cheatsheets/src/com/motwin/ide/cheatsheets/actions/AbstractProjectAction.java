/**
 * 
 */
package com.motwin.ide.cheatsheets.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractProjectAction extends Action implements ICheatSheetAction {

    @Override
    public void run(final String[] aParams, final ICheatSheetManager aManager) {
        IWorkspaceRoot root;
        root = ResourcesPlugin.getWorkspace().getRoot();

        String projectName;
        projectName = getProjectName();

        IProject project;
        project = root.getProject(projectName);

        IJavaProject javaProject;
        javaProject = JavaCore.create(project);

        perform(javaProject);
    }

    protected abstract void perform(IJavaProject aJavaProject);

    public abstract String getProjectName();

    public abstract String getPluginID();
}
