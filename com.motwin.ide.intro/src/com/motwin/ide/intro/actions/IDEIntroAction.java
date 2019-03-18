/**
 * 
 */
package com.motwin.ide.intro.actions;

import java.util.Properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.intro.internal.IntroPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class IDEIntroAction implements IIntroAction {
    private static final String OPEN_DASHBOARD_COMMAND_ID = "com.motwin.ide.dashboard.commands.dashboardCommand";

    protected IDEIntroAction() {
    }

    @Override
    public void run(final IIntroSite aSite, final Properties aParams) {
        beforeRunAction();
        runAction(aSite, aParams);
        afterRunAction();
    }

    protected void afterRunAction() {
        // FIXME need a job listener to call this method
    }

    protected void beforeRunAction() {
        closeIntro();
        openDefaultPerspective();
        openDashboard();
    }

    protected abstract void runAction(IIntroSite aSite, Properties aParams);

    protected void closeIntro() {
        IWorkbench workbench;
        workbench = PlatformUI.getWorkbench();

        IIntroManager introManager;
        introManager = workbench.getIntroManager();

        IIntroPart intro;
        intro = introManager.getIntro();

        if (intro != null) {
            introManager.closeIntro(intro);
        }
    }

    protected void openDefaultPerspective() {
        String perspectiveId;
        perspectiveId = getDefaultPerspectiveID();

        try {
            IWorkbench workbench;
            workbench = PlatformUI.getWorkbench();

            IWorkbenchWindow activeWindow;
            activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            IPerspectiveDescriptor perspective;
            perspective = workbench.getPerspectiveRegistry().findPerspectiveWithId(perspectiveId);
            if (perspective != null) {
                workbench.showPerspective(perspectiveId, activeWindow);
            }

        } catch (WorkbenchException e) {
            StatusHandler.log(new Status(Status.ERROR, IntroPlugin.PLUGIN_ID, "failed to open the perspective [id="
                + perspectiveId + "]", e));
        }
    }

    protected abstract String getDefaultPerspectiveID();

    protected void openDashboard() {
        try {
            IHandlerService handlerService;
            handlerService = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);
            handlerService.executeCommand(OPEN_DASHBOARD_COMMAND_ID, null);

        } catch (ExecutionException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: failed to execute the handler associated to the command [id="
                        + OPEN_DASHBOARD_COMMAND_ID + "]", e));

        } catch (NotDefinedException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: no handler associated to the command [id="
                        + OPEN_DASHBOARD_COMMAND_ID + "]", e));

        } catch (NotEnabledException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: the handler associated to the command [id="
                        + OPEN_DASHBOARD_COMMAND_ID + "] is not enabled", e));

        } catch (NotHandledException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: no handler associated to the command [id="
                        + OPEN_DASHBOARD_COMMAND_ID + "]", e));
        }
    }
}
