/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.notifmanagerdemo.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import org.eclipse.virgo.ide.ui.editors.BundleDependenciesPage;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.server.cheatsheets.operations.OpenManifestOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyManifestAndPropertiesAction extends AbstractNotifManagerDemoAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        try {
            IWorkspaceRunnable operation;
            operation = new IWorkspaceRunnable() {

                @Override
                public void run(final IProgressMonitor aMonitor) throws CoreException {
                    copyManifest(aJavaProject);
                    copyProperties(aJavaProject);
                }

            };

            ResourcesPlugin.getWorkspace().run(operation, new NullProgressMonitor());

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, getPluginID(),
                            "A workspace error occurred while copying the MANIFEST.MF into project ["
                                + aJavaProject.getElementName() + "]", e));
        }
    }

    private void copyProperties(final IJavaProject aJavaProject) {
        try {
            // @formatter:off
            CopyFilesSampleOperation copyOp = 
                new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("com.motwin.sample.demo.notifmanager.properties")
                    .folderPath(getSrcTestResourcesFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
    
            copyOp.run();
            // @formatter:on

            IProject project;
            project = aJavaProject.getProject();
            project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, getPluginID(), "Problem occurred while refreshing project ["
                        + aJavaProject.getElementName() + "] after the properties files copy.", e));
        }
    }

    private void copyManifest(final IJavaProject aJavaProject) {
        try {
            // @formatter:off
            CopyFilesSampleOperation copyOp = 
                new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("MANIFEST.MF")
                    .folderPath(getSrcResourcesFolder() + IPath.SEPARATOR + "META-INF")
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
    
            copyOp.run();
            // @formatter:on

            IProject project;
            project = aJavaProject.getProject();
            project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

            new OpenManifestOperation(aJavaProject, BundleDependenciesPage.PAGE_ID, getPluginID()).run();

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, getPluginID(), "Problem occurred while refreshing project ["
                        + aJavaProject.getElementName() + "] after the MANIFEST.MF file copy.", e));
        }
    }
}
