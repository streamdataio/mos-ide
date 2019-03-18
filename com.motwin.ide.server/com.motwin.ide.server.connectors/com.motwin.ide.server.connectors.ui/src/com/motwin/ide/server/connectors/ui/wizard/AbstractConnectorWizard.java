package com.motwin.ide.server.connectors.ui.wizard;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.virgo.ide.manifest.core.BundleManifestUtils;
import org.eclipse.virgo.ide.runtime.core.ServerCorePlugin;
import org.eclipse.virgo.ide.runtime.core.ServerUtils;
import org.eclipse.virgo.ide.runtime.core.provisioning.RepositoryUtils;
import org.eclipse.virgo.ide.runtime.internal.core.runtimes.VirgoRuntimeProvider;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.virgo.util.osgi.manifest.ImportBundle;
import org.eclipse.virgo.util.osgi.manifest.ImportedBundle;
import org.eclipse.wst.server.core.IRuntime;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.motwin.ide.server.connectors.core.wizard.IConnectorWizard;
import com.motwin.ide.ui.Images;

/**
 * 
 * @author fbou
 */
public abstract class AbstractConnectorWizard extends Wizard implements IConnectorWizard {

    protected IWorkbench workbench;
    protected IProject   project;

    public AbstractConnectorWizard() {
        setDefaultPageImageDescriptor(Images.motwinMedium);
        setNeedsProgressMonitor(true);
    }

    @Override
    public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
        checkArgument(aSelection.getFirstElement() instanceof IProject, "need a Project");
        project = (IProject) aSelection.getFirstElement();
        workbench = checkNotNull(aWorkbench);
    }

    @Override
    public boolean performFinish() {
        try {
            doFinishSynchonous();

            getContainer().run(true, false, new IRunnableWithProgress() {
                @Override
                public void run(IProgressMonitor aProgressMonitor) throws InvocationTargetException,
                        InterruptedException {
                    try {
                        aProgressMonitor.beginTask("Connector Import", 100);
                        doFinishAsynchronous(aProgressMonitor);

                    } catch (Exception ex) {
                        throw new InvocationTargetException(ex, "Unable to import connector");
                    }
                }
            });

        } catch (Throwable e) {
            MessageDialog.openError(getShell(), "Error importing Motwin Connector", e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    protected void doFinishSynchonous() throws Exception {
        // nothing

    }

    protected void doFinishAsynchronous(IProgressMonitor aProgressMonitor) throws Exception {
        aProgressMonitor.subTask("Checking project...");

        IJavaProject javaProject;
        javaProject = JavaCore.create(project);

        BundleManifest manifest;
        manifest = BundleManifestUtils.getBundleManifest(javaProject, false);

        ImportBundle currentImportBundle;
        currentImportBundle = manifest.getImportBundle();

        aProgressMonitor.worked(10);
        aProgressMonitor.subTask("Editing MANIFEST.MF...");

        Collection<BundleImport> importedBundles;
        importedBundles = getBundleImports();
        for (BundleImport importedBundle : importedBundles) {
            // remove bundle if already present
            for (ImportedBundle currentImportedBundle : Lists.newArrayList(currentImportBundle.getImportedBundles())) {
                if (currentImportedBundle.getBundleSymbolicName().equals(importedBundle.bundleName)) {
                    currentImportBundle.getImportedBundles().remove(currentImportedBundle);
                }
            }
            // add bundle
            currentImportBundle.addImportedBundle(importedBundle.getFormatted());
        }

        IFile manifestFile;
        manifestFile = BundleManifestUtils.locateManifest(javaProject, false);

        FileWriter writer;
        writer = new FileWriter(new File(manifestFile.getLocationURI()));
        manifest.write(writer);

        aProgressMonitor.worked(10);
        aProgressMonitor.subTask("Locating target Virgo runtime...");

        IRuntime[] runtimes;
        runtimes = ServerUtils.getTargettedRuntimes(project.getProject());

        IRuntime runtime = null;
        for (IRuntime candidateRuntime : runtimes) {
            if (candidateRuntime.getRuntimeType().getId().equals(VirgoRuntimeProvider.SERVER_VIRGO_BASE)) {
                runtime = candidateRuntime;
                break;
            }
        }

        checkState(runtime != null, "this project is not associated to a runtime");

        aProgressMonitor.worked(40);
        aProgressMonitor.subTask("Coping required bundles to target Virgo runtime...");

        RepositoryUtils.getRepositoryContents(runtime);

        String runtimePath;
        runtimePath = runtime.getLocation().toFile().getPath();

        Collection<BundleCopy> copiedBundles;
        copiedBundles = getBundleCopies();
        for (BundleCopy copiedBundle : copiedBundles) {
            File targetFile;
            targetFile = new File(copiedBundle.getTarget(runtimePath));

            if (!targetFile.exists()) {
                try {
                    targetFile.getParentFile().mkdirs();
                    targetFile.createNewFile();
                    ByteStreams.copy(copiedBundle.getSource().openStream(), new FileOutputStream(targetFile));

                } catch (Exception t) {
                    targetFile.delete();
                    throw t;

                }
            }
        }

        aProgressMonitor.worked(10);
        aProgressMonitor.subTask("Refreshing bundle repository...");

        ServerCorePlugin.getArtefactRepositoryManager().refreshBundleRepository(runtime);

        aProgressMonitor.worked(10);
        aProgressMonitor.subTask("Refreshing project...");

        project.refreshLocal(IResource.DEPTH_INFINITE, aProgressMonitor);
    }

    public IWorkbench getWorkbench() {
        return workbench;
    }

    /**
     * @return a collection of {@link BundleCopy}.
     */
    protected abstract Collection<BundleCopy> getBundleCopies();

    /**
     * @return a collection of {@link BundleImport}.
     */
    protected abstract Collection<BundleImport> getBundleImports();

    /**
	 * 
	 */
    public static class BundleCopy {

        private final String sourcePlugin;
        private final String sourceFile;
        private final String targetFile;

        public BundleCopy(String aSourcePlugin, String aSourceFile, String aTargetFile) {
            sourcePlugin = checkNotNull(aSourcePlugin);
            sourceFile = checkNotNull(aSourceFile);
            targetFile = checkNotNull(aTargetFile);
        }

        public URL getSource() {
            return Platform.getBundle(sourcePlugin).getEntry(checkNotNull(sourceFile));
        }

        public URI getTarget(String aBaseDirectory) throws URISyntaxException {
            return new URI("file:" + aBaseDirectory + "/" + targetFile);
        }

    }

    /**
	 * 
	 */
    public static class BundleImport {

        private final String bundleName;
        private final String bundleVersion;

        public BundleImport(String aBundleName, String aBundleVersion) {
            bundleName = checkNotNull(aBundleName);
            bundleVersion = checkNotNull(aBundleVersion);
        }

        public String getFormatted() {
            return String.format("%s;version=\"%s\"", bundleName, bundleVersion);
        }

    }

}
