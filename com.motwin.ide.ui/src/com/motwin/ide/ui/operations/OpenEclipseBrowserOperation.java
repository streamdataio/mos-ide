/**
 * 
 */
package com.motwin.ide.ui.operations;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
public final class OpenEclipseBrowserOperation extends AbstractOpenBrowserOperation {

    private final String pluginID;
    private final String browserID;
    private final String name;
    private final String tooltip;

    public OpenEclipseBrowserOperation(final String aBrowserID, final String aName, final String aTooltip,
            final String aURL, final String aPluginID) {
        super(aURL);
        Preconditions.checkNotNull(aBrowserID, "aBrowserID cannot be null");
        Preconditions.checkNotNull(aName, "aName cannot be null");
        Preconditions.checkNotNull(aTooltip, "aTooltip cannot be null");
        Preconditions.checkNotNull(aPluginID, "aPluginID cannot be null");

        browserID = aBrowserID;
        name = aName;
        tooltip = aTooltip;
        pluginID = aPluginID;
    }

    @Override
    public void run() {
        IWorkbenchBrowserSupport browserSupport;
        browserSupport = PlatformUI.getWorkbench().getBrowserSupport();

        try {
            IWebBrowser browser;
            browser = browserSupport.createBrowser(IWorkbenchBrowserSupport.LOCATION_BAR
                | IWorkbenchBrowserSupport.NAVIGATION_BAR, browserID, name, tooltip);

            browser.openURL(getURL());

        } catch (PartInitException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to open the Eclipse Web Browser for the URL [url="
                        + getURL() + "]", e));
        }

    }

}
