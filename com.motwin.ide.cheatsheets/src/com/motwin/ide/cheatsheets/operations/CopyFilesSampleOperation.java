/**
 * 
 */
package com.motwin.ide.cheatsheets.operations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.helpers.CopyHelper;
import com.motwin.ide.cheatsheets.helpers.CopyHelper.CopyHelperBuilder;
import com.motwin.ide.core.utils.CoreUtil;

/**
 * @author ctranxuan
 * 
 */
public final class CopyFilesSampleOperation {
    private final String   includeFiles;
    private final String   excludeFiles;
    private final IProject project;

    private final String   pluginID;
    private final String   sampleCodePath;
    private final String   folderPath;

    public final static class OperationBuilder {
        private String   includeFiles;
        private String   excludeFiles;
        private IProject project;

        private String   pluginID;
        private String   sampleCodePath;
        private String   folderPath;

        public OperationBuilder() {
        }

        public OperationBuilder includeFiles(final String aRegexp) {
            includeFiles = aRegexp;
            return this;
        }

        public OperationBuilder excludeFiles(final String aRegexp) {
            excludeFiles = aRegexp;
            return this;
        }

        public OperationBuilder project(final IProject aProject) {
            project = aProject;
            return this;
        }

        public OperationBuilder project(final IJavaProject aProject) {
            project = aProject.getProject();
            return this;
        }

        public OperationBuilder pluginID(final String aPluginID) {
            pluginID = aPluginID;
            return this;
        }

        public OperationBuilder sampleCodePath(final String aPath) {
            sampleCodePath = aPath;
            return this;
        }

        public OperationBuilder folderPath(final String aPath) {
            folderPath = aPath;
            return this;
        }

        public CopyFilesSampleOperation build() {
            Preconditions.checkState(project != null, "project cannot be null");
            Preconditions.checkState(pluginID != null, "pluginID cannot be null");
            Preconditions.checkState(sampleCodePath != null, "sampleCodePath cannot be null and must exist");
            Preconditions.checkState(folderPath != null, "folderPath cannot be null");
            Preconditions.checkState(includeFiles != null || excludeFiles != null,
                    "includeFiles and excludeFiles cannot be both null");

            return new CopyFilesSampleOperation(this);
        }
    }

    private CopyFilesSampleOperation(final OperationBuilder aBuilder) {
        project = aBuilder.project;
        pluginID = aBuilder.pluginID;
        excludeFiles = aBuilder.excludeFiles;
        includeFiles = aBuilder.includeFiles;
        sampleCodePath = aBuilder.sampleCodePath;
        folderPath = aBuilder.folderPath;
    }

    public void run() {
        try {
            IWorkspaceRunnable operation;
            operation = new IWorkspaceRunnable() {

                @Override
                public void run(final IProgressMonitor aMonitor) throws CoreException {
                    perform();
                }
            };

            ResourcesPlugin.getWorkspace().run(operation, new NullProgressMonitor());

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "A workspace error occurred while copying the files [includes='" + includeFiles
                                + "', excludes='" + excludeFiles + "'] from '" + sampleCodePath + IPath.SEPARATOR
                                + folderPath + "' into project '" + project.getName() + "'", e));
        }
    }

    private void perform() {
        try {

            File sampleFolder;
            sampleFolder = CoreUtil.locateResource(pluginID, sampleCodePath + IPath.SEPARATOR + folderPath);

            // @formatter:off
            CopyHelperBuilder builder;
            builder = new CopyHelperBuilder()
                            .from(sampleFolder)
                            .to(project, folderPath)
                            ;
            // @formatter:on
            if (includeFiles != null) {
                builder.includeFiles(includeFiles);
            }

            if (excludeFiles != null) {
                builder.excludeFiles(excludeFiles);
            }

            CopyHelper copyHelper;
            copyHelper = builder.build();

            copyHelper.copy();

        } catch (URISyntaxException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "An error occurred while copying the sample files for the javaProject ["
                                + project.getFullPath()
                                + "]: a jdt error occured (probably a missing folder in the workspace).", e));

        } catch (IOException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "An error occurred while copying the sample files for the javaProject ["
                                + project.getFullPath()
                                + "]: a resource sample has not been found or been copied properly.", e));
        }
    }
}
