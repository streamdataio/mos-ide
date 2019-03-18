/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.realtimepush.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
import org.eclipse.virgo.ide.manifest.core.BundleManifestUtils;
import org.eclipse.virgo.ide.ui.editors.BundleDependenciesPage;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.virgo.util.osgi.manifest.ImportLibrary;

import com.google.common.io.Closeables;
import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.server.cheatsheets.operations.OpenManifestOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyManifestAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        try {
            IWorkspaceRunnable operation;
            operation = new IWorkspaceRunnable() {

                @Override
                public void run(final IProgressMonitor aMonitor) throws CoreException {
                    copyManifest(aJavaProject);
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

    private void copyManifest(final IJavaProject aJavaProject) {
        FileWriter fileWriter = null;
        try {
            BundleManifest bundleManifest;
            bundleManifest = BundleManifestUtils.getBundleManifest(aJavaProject, false);

            ImportLibrary importLibrary;
            importLibrary = bundleManifest.getImportLibrary();

            String importLibPragma;
            importLibPragma = importLibrary.toParseString();

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

            BundleManifest newBundleManifest;
            newBundleManifest = BundleManifestUtils.getBundleManifest(aJavaProject, false);

            ImportLibrary newImportLibrary;
            newImportLibrary = newBundleManifest.getImportLibrary();
            newImportLibrary.resetFromParseString(importLibPragma);

            File manifestFile;
            manifestFile = BundleManifestUtils.locateManifestFile(aJavaProject, false);

            fileWriter = new FileWriter(manifestFile);
            newBundleManifest.write(fileWriter);

            project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

            new OpenManifestOperation(aJavaProject, BundleDependenciesPage.PAGE_ID, getPluginID()).run();

        } catch (IOException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, getPluginID(), "Problem occurred while opening project manifest.", e));

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, getPluginID(), "Problem occurred while refreshing project ["
                        + aJavaProject.getElementName() + "].", e));

        } finally {

            if (fileWriter != null) {
                Closeables.closeQuietly(fileWriter);
            }
        }

    }
}
