/**
 * 
 */
package com.motwin.ide.android.cheatsheets.samples.helloworld.actions;

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

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class HelloApp1CopyAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        deleteMenu(aJavaProject);
        copyResFiles(aJavaProject);
        copyManifest(aJavaProject);
        copyHelloApp1(aJavaProject);
    }

    private void deleteMenu(final IJavaProject aJavaProject) {
        try {
            IWorkspaceRunnable operation;
            operation = new IWorkspaceRunnable() {

                @Override
                public void run(final IProgressMonitor aMonitor) throws CoreException {
                    IProject project;
                    project = aJavaProject.getProject();

                    project.getFolder(getResFolder() + IPath.SEPARATOR + "menu").delete(true, aMonitor);

                }
            };

            ResourcesPlugin.getWorkspace().run(operation, new NullProgressMonitor());

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, getPluginID(),
                            "A workspace error occurred while deleting the folder [menu] from the project ["
                                + aJavaProject.getElementName() + "]", e));
        }
    }

    /**
     * @param aJavaProject
     */
    private void copyResFiles(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new CopyFilesSampleOperation.OperationBuilder()
                .includeFiles("^(\\w+).xml")
                .folderPath(getResFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();

        operation.run();
        // @formatter:on
    }

    /**
     * @param aJavaProject
     */
    private void copyHelloApp1(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyJavaFileSampleOperation operation;
        operation = new OperationBuilder()
                        .action(this)
                        .project(aJavaProject)
                        .fileName("HelloApp1.java")
                        .update(true)
                        .build();
        // @formatter:on

        operation.run();
    }

    private void copyManifest(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new CopyFilesSampleOperation.OperationBuilder()
                .includeFiles("AndroidManifest.xml")
                .folderPath("")
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();

        operation.run();
        // @formatter:on     

        try {
            IProject project;
            project = aJavaProject.getProject();

            project.getFile("AndroidManifest.xml").refreshLocal(1, new NullProgressMonitor());

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, getPluginID(),
                            "A workspace error occurred while refreshing the file [AndroidManifest.xml] from the project ["
                                + aJavaProject.getElementName() + "]", e));
        }
    }
}