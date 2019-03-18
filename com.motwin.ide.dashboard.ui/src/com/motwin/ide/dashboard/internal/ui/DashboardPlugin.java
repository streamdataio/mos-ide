package com.motwin.ide.dashboard.internal.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DashboardPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String     PLUGIN_ID = "com.motwin.ide.dashboard.ui"; //$NON-NLS-1$

    // The shared instance
    private static DashboardPlugin plugin;

    /**
     * The constructor
     */
    public DashboardPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        // openDashboardJob();
    }

    // private void openDashboardJob() {
    // Job job;
    // job = new UIJob("Motwin Dashboard IDE Initialization") {
    //
    // @Override
    // public IStatus runInUIThread(final IProgressMonitor aMonitor) {
    // try {
    //
    // IWorkbenchWindow window =
    // PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    // if (window != null) {
    // // prevent loading if already opened by workspace
    // // restore
    // IEditorReference[] references =
    // window.getActivePage().getEditorReferences();
    // for (IEditorReference reference : references) {
    // if
    // (DashboardEditorInputFactory.FACTORY_ID.equals(reference.getFactoryId()))
    // {
    // return Status.OK_STATUS;
    // }
    // }
    //
    // if
    // (getPreferenceStore().getBoolean(DashboardConstants.DEFAULT_OPEN_DASHBOARD_STARTUP))
    // {
    // // don't show if welcome page is visible
    // IIntroManager introManager;
    // introManager = window.getWorkbench().getIntroManager();
    //
    // IIntroPart intro;
    // intro = introManager.getIntro();
    //
    // if (intro != null) {
    // // quick fix until we have our own Welcome page
    // introManager.closeIntro(intro);
    // } else {
    //
    // IHandlerService handlerService;
    // handlerService = (IHandlerService) PlatformUI.getWorkbench().getService(
    // IHandlerService.class);
    // try {
    // handlerService.executeCommand(DashboardHandler.COMMAND_ID, null);
    //
    // } catch (ExecutionException e) {
    // StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
    // "Failed to open the dashboard [id=" + DashboardPlugin.PLUGIN_ID
    // + "]: failed to execute the command [id=" + DashboardHandler.COMMAND_ID
    // + "]", e));
    // } catch (NotDefinedException e) {
    // StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
    // "Failed to open the dashboard [id=" + DashboardPlugin.PLUGIN_ID
    // + "]:no command [id=" + DashboardHandler.COMMAND_ID + "] defined", e));
    // } catch (NotEnabledException e) {
    // StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
    // "Failed to open the dashboard [id=" + DashboardPlugin.PLUGIN_ID
    // + "]: the command [id=" + DashboardHandler.COMMAND_ID
    // + "] is not enabled", e));
    // } catch (NotHandledException e) {
    // StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
    // "Failed to open the dashboard [id=" + DashboardPlugin.PLUGIN_ID
    // + "]: no handler associated to the command [id="
    // + DashboardHandler.COMMAND_ID + "]", e));
    // }
    // }
    // }
    // }
    //
    // } catch (Throwable t) {
    // StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
    // "Failed to open the dashboard [id=" + DashboardPlugin.PLUGIN_ID
    // + "]: an unexpected error occurred", t));
    // }
    //
    // return Status.OK_STATUS;
    // }
    // };
    //
    // job.setSystem(true);
    // job.schedule();
    // }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(final BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static DashboardPlugin getDefault() {
        return plugin;
    }
}
