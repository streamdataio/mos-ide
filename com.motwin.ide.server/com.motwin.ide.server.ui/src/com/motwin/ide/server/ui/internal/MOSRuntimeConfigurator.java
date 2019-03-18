/**
 * 
 */
package com.motwin.ide.server.ui.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.virgo.ide.runtime.core.IServer;
import org.eclipse.virgo.ide.runtime.core.ServerCorePlugin;
import org.eclipse.wst.server.core.IRuntimeWorkingCopy;
import org.eclipse.wst.server.core.IServerType;
import org.eclipse.wst.server.core.IServerWorkingCopy;
import org.eclipse.wst.server.core.ServerCore;
import org.osgi.service.prefs.BackingStoreException;

import com.google.common.base.Strings;

/**
 * @author ctranxuan
 * 
 */
/*
 * NB: this could be part of another plugin (e.g.
 * com.motwin.ide.server.runtime). As long as a such plugin is not required, we
 * will keep the code here to avoid another "empty" plugin that will take some
 * resources while doing almost anything (and being useful only at the first
 * startup).
 */
class MOSRuntimeConfigurator {

    private static final String DEFAULT_MOS_RUNTIME_LOCATION = "default.mos.runtime.location";        //$NON-NLS-N$
    private static final String DEFAULT_MOS_RUNTIME_ID       = "MOS Runtime";                         //$NON-NLS-N$
    private static final String DEFAULT_MOS_RUNTIME_NAME     = DEFAULT_MOS_RUNTIME_ID;
    private static final String DEFAULT_MOS_SERVER_ID        = "MOS Runtime at localhost";            //$NON-NLS-N$
    private static final String DEFAULT_MOS_SERVER_NAME      = DEFAULT_MOS_RUNTIME_ID;

    private static final String DEFAULT_PERSPECTIVE_ID       = "com.springsource.sts.ide.perspective"; //$NON-NLS-N$

    public boolean shallCreateDefaultMOS() {
        return isDefaultMOSDefined(ConfigurationScope.INSTANCE) && !isDefaultMOSDefined(InstanceScope.INSTANCE);
    }

    private boolean isDefaultMOSDefined(final IScopeContext aContext) {
        String pref;
        pref = getDefaultMOSLocation(aContext);

        return !Strings.isNullOrEmpty(pref);
    }

    private String getDefaultMOSLocation(final IScopeContext aContext) {
        IEclipsePreferences prefs;
        prefs = aContext.getNode(MotwinServerUIPlugin.PLUGIN_ID);

        String pref;
        pref = prefs.get(DEFAULT_MOS_RUNTIME_LOCATION, "");

        return pref;
    }

    private void createMOSRuntime() {

        try {
            IServerType serverType;
            serverType = ServerCore.findServerType(ServerCorePlugin.VIRGO_SERVER_ID);

            if (serverType == null) {
                StatusManager.getManager().handle(
                        new Status(IStatus.WARNING, MotwinServerUIPlugin.PLUGIN_ID, "No Virgo server type ["
                            + ServerCorePlugin.PLUGIN_ID + "] found: no MOS server runtime will be created"));

            } else if (ServerCore.findRuntime(DEFAULT_MOS_RUNTIME_ID) != null) {
                StatusManager.getManager().handle(
                        new Status(IStatus.WARNING, MotwinServerUIPlugin.PLUGIN_ID,
                                "Default MOS server runtime already exists [" + DEFAULT_MOS_RUNTIME_ID
                                    + "]: no new runtime will be created"));

            } else {
                IRuntimeWorkingCopy runtime;
                runtime = serverType.getRuntimeType().createRuntime(DEFAULT_MOS_RUNTIME_ID, new NullProgressMonitor());

                String location;
                location = getDefaultMOSLocation(ConfigurationScope.INSTANCE);
                runtime.setLocation(new Path(location));
                runtime.setName(DEFAULT_MOS_RUNTIME_NAME);

                IStatus status;
                status = runtime.validate(new NullProgressMonitor());

                if (status.isOK()) {
                    runtime.save(true, new NullProgressMonitor());

                    IServerWorkingCopy server;
                    server = serverType.createServer(DEFAULT_MOS_SERVER_ID, null, runtime, new NullProgressMonitor());
                    server.setName(DEFAULT_MOS_SERVER_NAME);
                    server.setAttribute(IServer.PROPERTY_CLEAN_STARTUP, true);
                    server.saveAll(true, new NullProgressMonitor());

                    IEclipsePreferences localPrefs;
                    localPrefs = InstanceScope.INSTANCE.getNode(MotwinServerUIPlugin.PLUGIN_ID);
                    localPrefs.put(DEFAULT_MOS_RUNTIME_LOCATION, location);
                    localPrefs.flush();

                } else {
                    StatusManager.getManager().handle(
                            new Status(IStatus.WARNING, MotwinServerUIPlugin.PLUGIN_ID, "Default MOS server location ["
                                + location + "] is invalid: no MOS server runtime will be created"));

                }
            }
        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, MotwinServerUIPlugin.PLUGIN_ID,
                            "An error occurred while trying to settle a default MOS Server Runtime", e));

        } catch (BackingStoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, MotwinServerUIPlugin.PLUGIN_ID,
                            "Failed to store the location of the default runtime in the InstanceScope", e));
        }

    }

    private void openDefaultPerspective() {
        try {
            IWorkbenchWindow activeWindow;
            activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

            IWorkbench workbench;
            workbench = activeWindow.getWorkbench();

            IPerspectiveDescriptor perspective;
            perspective = workbench.getPerspectiveRegistry().findPerspectiveWithId(DEFAULT_PERSPECTIVE_ID);
            if (perspective != null) {
                workbench.showPerspective(DEFAULT_PERSPECTIVE_ID, activeWindow);
            }

        } catch (WorkbenchException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, MotwinServerUIPlugin.PLUGIN_ID, "Failed to open the perspective [id="
                        + DEFAULT_PERSPECTIVE_ID + "]", e));
        }
    }

    public void createAndShow() {
        createMOSRuntime();
        openDefaultPerspective();

    }
}
