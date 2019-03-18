/**
 * 
 */
package com.motwin.ide.server.cheatsheets.operations;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.statushandlers.StatusManager;

import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public class OpenManifestOperation {
    private final IJavaProject project;
    private final String       pageID;
    private final String       pluginID;

    public OpenManifestOperation(final IJavaProject aProject, final String aPageID, final String aPluginID) {
        project = aProject;
        pageID = aPageID;
        pluginID = aPluginID;
    }

    public void run() {
        try {
            FormEditor manifestEditor;
            manifestEditor = (FormEditor) JDTUtil.openManifestEditor(project);

            manifestEditor.setActivePage(pageID);

        } catch (PartInitException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "Problem occurred while opening the MANIFEST.MF of the project ["
                                + project.getElementName() + "].", e));
        }
    }
}
