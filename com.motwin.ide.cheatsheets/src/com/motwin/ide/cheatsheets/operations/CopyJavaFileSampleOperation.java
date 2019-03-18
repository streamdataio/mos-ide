/**
 * 
 */
package com.motwin.ide.cheatsheets.operations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

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
import com.motwin.ide.cheatsheets.actions.ISampleAction;
import com.motwin.ide.cheatsheets.helpers.CopyHelper;
import com.motwin.ide.cheatsheets.helpers.CopyHelper.CopyHelperBuilder;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;
import com.motwin.ide.core.utils.CoreUtil;

/**
 * This class copies from the sample a Java file and opens it in the editor. It
 * assumes that the file is in the base package declared by the action.
 * 
 * @author ctranxuan
 * 
 */
public final class CopyJavaFileSampleOperation {

    private final ISampleAction action;
    private final IJavaProject  project;
    private final String        fileName;
    private final boolean       update;
    private final boolean       open;

    public final static class OperationBuilder {
        private ISampleAction action;
        private IJavaProject  project;
        private String        fileName;
        private boolean       update;
        private boolean       open;

        public OperationBuilder() {
            update = false;
            open = true;
        }

        public OperationBuilder action(final ISampleAction anAction) {
            action = anAction;
            return this;
        }

        public OperationBuilder project(final IJavaProject aProject) {
            project = aProject;
            return this;
        }

        public OperationBuilder fileName(final String aFileName) {
            fileName = aFileName;
            return this;
        }

        public OperationBuilder update(final boolean anUpdate) {
            update = anUpdate;
            return this;
        }

        public OperationBuilder open(final boolean anOpen) {
            open = anOpen;
            return this;
        }

        public CopyJavaFileSampleOperation build() {
            Preconditions.checkState(action != null, "action cannot be null");
            Preconditions.checkState(fileName != null, "fileName cannot be null");
            Preconditions.checkState(project != null, "project cannot be null");

            return new CopyJavaFileSampleOperation(this);
        }
    }

    private CopyJavaFileSampleOperation(final OperationBuilder aBuilder) {
        action = aBuilder.action;
        fileName = aBuilder.fileName;
        project = aBuilder.project;
        update = aBuilder.update;
        open = aBuilder.open;
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
                        new Status(IStatus.ERROR, action.getPluginID(),
                                "A workspace error occurred while copying the sample file [name=" + fileName
                                    + "] into project [name=" + project.getElementName() + "].", e));
            }

        } else {
            performOperation(fileName);
        }

    }

    private void performOperation(final String aFileName) {
        try {
            File sampleFolder;
            sampleFolder = CoreUtil.locateResource(action.getPluginID(), action.getSampleCodePath() + IPath.SEPARATOR
                + action.getSrcJavaFolder());

            // @formatter:off
            CopyHelper copyHelper;
            copyHelper = 
                    new CopyHelperBuilder()
                        .from(sampleFolder)
                        .to(project, action.getSrcJavaFolder())
                        .includeFiles(fileName)
                        .build();
            // @formatter:on

            copyHelper.copy();

            if (open) {
                JDTUtil.openJavaFileEditor(project, action.getSrcJavaFolder(), action.getJavaBasePackage(), fileName);
            }
        } catch (IOException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, action.getPluginID(),
                            "An I/O error occurred while copying the sample file [name=" + aFileName
                                + "] into the project [name=" + project.getElementName() + "].", e));

        } catch (URISyntaxException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, action.getPluginID(),
                            "An error occurred while copying the sample file [name=" + aFileName
                                + "] into the project [name=" + project.getElementName()
                                + "]: unable to locate the sample source folder.", e));

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, action.getPluginID(),
                            "A JDT error occurred while copying the sample file [name=" + aFileName
                                + "] into the project [name=" + project.getElementName() + "].", e));
        }
    }
}
