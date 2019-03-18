/**
 * 
 */
package com.motwin.ide.cheatsheets.operations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.helpers.CopyHelper;
import com.motwin.ide.cheatsheets.helpers.CopyHelper.CopyHelperBuilder;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;
import com.motwin.ide.core.utils.CoreUtil;

/**
 * Copies a given file and also offers the ability to open the desired editor
 * with it.
 * 
 * @author ctranxuan
 * 
 */
/*
 * FIXME merge CopyJavaFileSampleOperation with this class or at least use this
 * class in CopyJavaFileSampleOperation
 */
public class CopyFileOperation {
    private final String   fileName;
    private final String   folderPath;
    private final String   openEditorID;
    private final IProject project;
    private final String   pluginID;
    private final String   rootPath;
    private final String   sampleCodePath;
    private final boolean  update;

    public final static class OperationBuilder {
        private IProject project;
        private String   pluginID;
        private String   rootPath;
        private String   folderPath;
        private String   sampleCodePath;
        private String   fileName;
        private String   openEditorID;
        private boolean  update;

        public OperationBuilder() {
            update = false;
        }

        public OperationBuilder project(final IProject aProject) {
            project = aProject;
            return this;
        }

        public OperationBuilder pluginID(final String aPluginID) {
            pluginID = aPluginID;
            return this;
        }

        public OperationBuilder folderPath(final String aFolderPath) {
            folderPath = aFolderPath;
            return this;
        }

        public OperationBuilder rootPath(final String aRootPath) {
            rootPath = aRootPath;
            return this;
        }

        public OperationBuilder sampleCodePath(final String aSampleCodePath) {
            sampleCodePath = aSampleCodePath;
            return this;
        }

        public OperationBuilder fileName(final String aFileName) {
            fileName = aFileName;
            return this;
        }

        public OperationBuilder openEditorID(final String anOpenEditorID) {
            openEditorID = anOpenEditorID;
            return this;
        }

        public OperationBuilder update(final boolean anUpdate) {
            update = anUpdate;
            return this;
        }

        public CopyFileOperation build() {
            Preconditions.checkState(fileName != null, "fileName cannot be null");
            Preconditions.checkState(folderPath != null, "folderPath cannot be null");
            Preconditions.checkState(pluginID != null, "pluginID cannot be null");
            Preconditions.checkState(project != null, "project cannot be null");
            Preconditions.checkState(rootPath != null, "rootPath cannot be null");
            Preconditions.checkState(sampleCodePath != null, "sampleCodePath cannot be null");

            return new CopyFileOperation(this);
        }
    }

    private CopyFileOperation(final OperationBuilder aBuilder) {
        fileName = aBuilder.fileName;
        folderPath = aBuilder.folderPath;
        rootPath = aBuilder.rootPath;
        openEditorID = aBuilder.openEditorID;
        pluginID = aBuilder.pluginID;
        project = aBuilder.project;
        sampleCodePath = aBuilder.sampleCodePath;
        update = aBuilder.update;
    }

    public void run() {
        if (update) {
            try {
                IWorkspaceRunnable operation;
                operation = new IWorkspaceRunnable() {

                    @Override
                    public void run(final IProgressMonitor aMonitor) throws CoreException {
                        performOperation(fileName);
                    }
                };

                ResourcesPlugin.getWorkspace().run(operation, new NullProgressMonitor());

            } catch (CoreException e) {
                StatusManager.getManager().handle(
                        new Status(IStatus.ERROR, pluginID,
                                "A workspace error occurred while copying the sample file [name=" + fileName
                                    + "] into project [name=" + project.getName() + "].", e));
            }

        } else {
            performOperation(fileName);

        }

    }

    private void performOperation(final String aFileName) {
        try {
            File sampleFolder;
            sampleFolder = CoreUtil.locateResource(pluginID, sampleCodePath);

            // @formatter:off
            CopyHelper copyHelper;
            copyHelper = 
                    new CopyHelperBuilder()
                        .from(sampleFolder)
                        .to(project, rootPath)
                        .includeFiles(fileName)
                        .build();
            // @formatter:on

            copyHelper.copy();

            if (openEditorID != null) {
                IFile file;
                file = project.getFile(new Path(rootPath + IPath.SEPARATOR + folderPath + IPath.SEPARATOR + fileName));

                JDTUtil.openEditor(openEditorID, file);
            }
        } catch (IOException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "An I/O error occurred while copying the sample file [name="
                        + aFileName + "] into the project [name=" + project.getName() + "].", e));

        } catch (URISyntaxException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "An error occurred while copying the sample file [name="
                        + aFileName + "] into the project [name=" + project.getName()
                        + "]: unable to locate the sample source folder.", e));

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "A workspace error occurred while copying the sample file [name=" + aFileName
                                + "] into the project [name=" + project.getName() + "].", e));
        }
    }
}
