/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.operations;

import java.net.MalformedURLException;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5SampleAction;
import com.motwin.ide.ui.operations.OpenEclipseBrowserOperation;

/**
 * @author ctranxuan
 * 
 */
public final class OpenIndexHTMLSampleOperation {
    private final AbstractHTML5SampleAction action;
    private final IJavaScriptProject        jsProject;

    public OpenIndexHTMLSampleOperation(final AbstractHTML5SampleAction aAction, final IJavaScriptProject aJsProject) {
        action = aAction;
        jsProject = aJsProject;
    }

    public void run() {
        IProject project;
        project = jsProject.getProject();

        IFile file;
        file = project.getFile(new Path(action.getIndexHTMLPath()));

        try {
            String url;
            url = file.getLocationURI().toURL().toString();

            new OpenEclipseBrowserOperation(UUID.randomUUID().toString(), file.getName(),
                    file.getFullPath().toString(), url, action.getPluginID()).run();

        } catch (MalformedURLException e) {
            StatusManager
                    .getManager()
                    .handle(new Status(
                            IStatus.ERROR,
                            action.getPluginID(),
                            "Failed to open the Eclipse Web Browser for the file [file=" + file + "]: url is malformed",
                            e));
        }

    }

}
