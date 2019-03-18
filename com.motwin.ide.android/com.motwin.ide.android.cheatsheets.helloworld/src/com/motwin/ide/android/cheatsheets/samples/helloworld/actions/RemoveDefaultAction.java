/**
 * 
 */
package com.motwin.ide.android.cheatsheets.samples.helloworld.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
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
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import com.motwin.ide.android.cheatsheets.samples.helloworld.internal.HelloWorldCheatSheetPlugin;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public final class RemoveDefaultAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        try {
            IWorkspaceRunnable operation;
            operation = new IWorkspaceRunnable() {

                @Override
                public void run(final IProgressMonitor aMonitor) throws CoreException {
                    IProject project;
                    project = aJavaProject.getProject();

                    deleteJavaFile(project, "DefaultActivity.java", aMonitor);
                    deleteJavaFile(project, "Main.java", aMonitor);
                    deleteJavaFile(project, "MotwinFacade.java", aMonitor);
                    deleteFile(project, "res/layout/main.xml", aMonitor);
                    updateAndroidManifest(project);
                }
            };

            ResourcesPlugin.getWorkspace().run(operation, new NullProgressMonitor());

        } catch (CoreException e) {
            StatusManager
                    .getManager()
                    .handle(new Status(
                            IStatus.ERROR,
                            HelloWorldCheatSheetPlugin.PLUGIN_ID,
                            "A workspace error occurred while deleting the files [DefaultActivity.java, MainActivity.java, MotwinFacade.java, res/layout/main.xml]' from the project '"
                                + aJavaProject.getElementName() + "'", e));
        }

    }

    private void deleteJavaFile(final IProject aProject, final String aFileName, final IProgressMonitor aMonitor)
            throws CoreException {
        Preconditions.checkNotNull(aProject, "aProject cannot be null");
        Preconditions.checkNotNull(aFileName, "aFileName cannot be null");

        String path;
        path = JDTUtil.toPath(getSrcJavaFolder(), getJavaBasePackage()) + IPath.SEPARATOR + aFileName;

        deleteFile(aProject, path, aMonitor);
    }

    private void deleteFile(final IProject aProject, final String aPath, final IProgressMonitor aMonitor)
            throws CoreException {
        Preconditions.checkNotNull(aProject, "aProject cannot be null");
        Preconditions.checkNotNull(aPath, "aPath cannot be null");

        aProject.getFile(aPath).delete(true, aMonitor);
    }

    private void updateAndroidManifest(final IProject aProject) {

        FileWriter writer = null;

        try {
            SAXBuilder builder;
            builder = new SAXBuilder();

            URI manifestURI;
            manifestURI = aProject.getFile("AndroidManifest.xml").getLocationURI();

            File manifestFile;
            manifestFile = EFS.getStore(manifestURI).toLocalFile(0, new NullProgressMonitor());

            Document doc;
            doc = builder.build(manifestFile);

            Element rootNode;
            rootNode = doc.getRootElement();

            Element applicationNode = rootNode.getChild("application");
            applicationNode.removeChild("activity");

            writer = new FileWriter(manifestFile);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, writer);

        } catch (JDOMException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, HelloWorldCheatSheetPlugin.PLUGIN_ID,
                            "A DOM error occurred while removing the default activity from the 'AndroidManifest.xml' of the project '"
                                + aProject.getName() + "'", e));
        } catch (IOException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, HelloWorldCheatSheetPlugin.PLUGIN_ID,
                            "A I/O error occurred while removing the default activity from the 'AndroidManifest.xml' of the project '"
                                + aProject.getName() + "'", e));

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, HelloWorldCheatSheetPlugin.PLUGIN_ID,
                            "A workspace error occurred while removing the default activity from the 'AndroidManifest.xml' of the project '"
                                + aProject.getName() + "': unable to locate the 'AndroidManifest.xml'.", e));
        } finally {
            if (writer != null) {
                Closeables.closeQuietly(writer);
            }
        }

    }
}
