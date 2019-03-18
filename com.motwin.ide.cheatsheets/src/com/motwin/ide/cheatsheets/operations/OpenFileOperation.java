/**
 * 
 */
package com.motwin.ide.cheatsheets.operations;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public class OpenFileOperation {
    private final IProject project;
    private final String   path;
    private final String   pluginID;

    public OpenFileOperation(final IProject aProject, final String aFilePath, final String aPluginID) {
        Preconditions.checkNotNull(aProject, "aProject cannot be null");
        Preconditions.checkNotNull(aFilePath, "aFilePath cannot be null");
        Preconditions.checkNotNull(aPluginID, "aPluginID cannot be null");

        project = aProject;
        path = aFilePath;
        pluginID = aPluginID;
    }

    public OpenFileOperation(final IJavaProject aProject, final String aFilePath, final String aPluginID) {
        this(aProject.getProject(), aFilePath, aPluginID);
    }

    public void run() {
        try {
            IFile file;
            file = project.getFile(path);

            JDTUtil.openDefaultEditor(file);
        } catch (PartInitException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "A workspace editor error occurred while attempting to open the file [" + path
                                + "] of the project [" + project.getName() + "]", e));
        }
    }
}
