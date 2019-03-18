/**
 * 
 */
package com.motwin.ide.dashboard.ui.links;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.base.Preconditions;
import com.motwin.ide.core.utils.CoreUtil;

/**
 * @author ctranxuan
 * 
 */
public class OpenPluginFileAsExternalHyperlinkListener extends HyperlinkAdapter implements IExecutableExtension {
    private String path;
    private String pluginID;

    public OpenPluginFileAsExternalHyperlinkListener() {
        super();
    }

    public OpenPluginFileAsExternalHyperlinkListener(final String aPluginID, final String aPath) {
        pluginID = aPluginID;
        path = aPath;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setInitializationData(final IConfigurationElement aConfig, final String aPropertyName,
                                      final Object aData) throws CoreException {
        Preconditions.checkNotNull(aData, "aData cannot be null");
        Map<String, String> parameters;
        parameters = (Map<String, String>) aData;

        pluginID = parameters.get("pluginID");
        Preconditions.checkState(pluginID != null, "pluginID cannot be null");

        path = parameters.get("path");
        Preconditions.checkState(path != null, "path cannot be null");
    }

    @Override
    public void linkActivated(final HyperlinkEvent anEvent) {
        try {
            File file = CoreUtil.locateResource(pluginID, path);
            IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

            IDE.openEditorOnFileStore(page, fileStore);

        } catch (PartInitException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "A workbench error occurred while attempting to open the file [" + path + "]", e));
        } catch (URISyntaxException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "A workbench error occurred while attempting to open the file [" + path
                                + "]: URI is not valid", e));
        } catch (IOException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "A I/O error occured while attempting to open the file ["
                        + path + "]", e));
        }
    }
}
