/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.helloworld.server.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.virgo.ide.manifest.core.BundleManifestUtils;
import org.eclipse.virgo.ide.ui.editors.BundleDependenciesPage;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.virgo.util.osgi.manifest.ImportPackage;
import org.eclipse.virgo.util.osgi.manifest.ImportedPackage;

import com.google.common.io.Closeables;
import com.motwin.ide.server.cheatsheets.operations.OpenManifestOperation;

/**
 * @author ctranxuan
 * 
 */
public class AddLogToManifestAction extends AbstractHelloWorldAction implements ICheatSheetAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        BundleManifest bundleManifest;
        bundleManifest = BundleManifestUtils.getBundleManifest(aJavaProject, false);

        ImportPackage importPackagePragma;
        importPackagePragma = bundleManifest.getImportPackage();

        List<ImportedPackage> importedPackages;
        importedPackages = importPackagePragma.getImportedPackages();

        boolean importNotFound;
        importNotFound = true;

        for (Iterator<ImportedPackage> iterator = importedPackages.iterator(); iterator.hasNext() && importNotFound;) {
            ImportedPackage importedPackage;
            importedPackage = iterator.next();

            if ("org.slf4j".equals(importedPackage.getPackageName()) && "1.6".equals(importedPackage.getVersion())) {
                importNotFound = false;
            }

        }

        if (!importNotFound || importedPackages.isEmpty()) {
            importPackagePragma.addImportedPackage("org.slf4j;version=\"1.6\"");
        }

        File manifestFile;
        manifestFile = BundleManifestUtils.locateManifestFile(aJavaProject, false);

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(manifestFile);
            bundleManifest.write(fileWriter);

            IProject project;
            project = aJavaProject.getProject();
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
