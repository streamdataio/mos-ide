/**
 * 
 */
package com.motwin.ide.cheatsheets.operations;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractOpenFileOperation {
    private final IProject project;
    private final String   path;
    private final String   pluginID;
    private final String   editorID;

    protected AbstractOpenFileOperation(final IProject aProject, final String aPath, final String aPluginID,
            final String anEditorID) {
        Preconditions.checkNotNull(aProject, "aProject cannot be null");
        Preconditions.checkNotNull(aPath, "aPath cannot be null");
        Preconditions.checkNotNull(aPluginID, "aPluginID cannot be null");
        Preconditions.checkNotNull(anEditorID, "anEditorID cannot be null");

        project = aProject;
        path = aPath;
        pluginID = aPluginID;
        editorID = anEditorID;
    }

    public void run() {
        try {
            IFile file;
            file = project.getFile(path);

            JDTUtil.openEditor(editorID, file);

        } catch (PartInitException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "A workspace editor error occurred while attempting to open the file [" + path
                                + "] of the project [" + project.getName() + "] with the editor [id=" + editorID + "]",
                            e));
        }
    }
}
