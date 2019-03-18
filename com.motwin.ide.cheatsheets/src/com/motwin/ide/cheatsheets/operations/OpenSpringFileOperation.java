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
import org.springframework.ide.eclipse.config.ui.editors.SpringConfigEditor;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public class OpenSpringFileOperation {
    public static final String SPRING_CONFIG_EDITOR_ID = "com.springsource.sts.config.ui.editors.SpringConfigEditor";

    private final IProject     project;
    private final String       filePath;
    private final String       pluginID;
    private final String       activeTabID;

    public static class OperationBuilder {
        private IProject project;
        private String   filePath;
        private String   pluginID;
        private String   activeTabID;

        public OperationBuilder project(final IProject aProject) {
            project = aProject;
            return this;
        }

        public OperationBuilder project(final IJavaProject aProject) {
            Preconditions.checkNotNull(aProject, "aProject cannot be null");
            project = aProject.getProject();
            return this;
        }

        public OperationBuilder filePath(final String aFilePath) {
            filePath = aFilePath;
            return this;
        }

        public OperationBuilder pluginID(final String aPluginID) {
            pluginID = aPluginID;
            return this;
        }

        public OperationBuilder activeTabId(final String aTabId) {
            activeTabID = aTabId;
            return this;
        }

        public OpenSpringFileOperation build() {
            Preconditions.checkState(project != null, "project cannot be null");
            Preconditions.checkState(filePath != null, "filePath cannot be null");
            Preconditions.checkState(pluginID != null, "pluginID cannot be null");
            Preconditions.checkState(activeTabID != null, "activeTabID cannot be null");

            return new OpenSpringFileOperation(this);
        }
    }

    private OpenSpringFileOperation(final OperationBuilder aBuilder) {
        project = aBuilder.project;
        filePath = aBuilder.filePath;
        pluginID = aBuilder.pluginID;
        activeTabID = aBuilder.activeTabID;
    }

    public void run() {
        try {
            IFile appFile;
            appFile = project.getFile(filePath);

            if (appFile.exists()) {

                SpringConfigEditor editorPart;
                editorPart = (SpringConfigEditor) JDTUtil.openEditor(SPRING_CONFIG_EDITOR_ID, appFile);

                if (editorPart != null) {
                    editorPart.setActivePage(activeTabID);
                }
            }

        } catch (PartInitException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to open the Spring Editor for the file [" + filePath
                        + "] for the project [" + project.getName() + "]", e));
        }
    }
}
