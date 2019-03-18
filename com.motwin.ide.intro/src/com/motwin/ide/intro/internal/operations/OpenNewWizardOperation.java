/**
 * 
 */
package com.motwin.ide.intro.internal.operations;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import com.google.common.collect.ImmutableMap;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.intro.internal.IntroPlugin;

/**
 * @author ctranxuan
 * 
 */
public class OpenNewWizardOperation implements IOperation {
    private static final String NEW_WIZARD_COMMAND_ID = "org.eclipse.ui.newWizard";
    private final String        wizardId;

    /**
     * @param aWizardId
     */
    public OpenNewWizardOperation(final String aWizardId) {
        wizardId = aWizardId;
    }

    @Override
    public void execute() {
        try {
            IWorkbench workbench;
            workbench = PlatformUI.getWorkbench();

            IHandlerService handlerService;
            handlerService = (IHandlerService) workbench.getService(IHandlerService.class);

            ICommandService cmdService;
            cmdService = (ICommandService) workbench.getService(ICommandService.class);

            Command command;
            command = cmdService.getCommand(NEW_WIZARD_COMMAND_ID);

            ParameterizedCommand paramCommand;
            paramCommand = ParameterizedCommand.generateCommand(command, ImmutableMap.of("newWizardId", wizardId));

            handlerService.executeCommand(paramCommand, null);

        } catch (ExecutionException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: failed to execute the handler associated to the command [id="
                        + NEW_WIZARD_COMMAND_ID + "]"));

        } catch (NotDefinedException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: no handler associated to the command [id=" + NEW_WIZARD_COMMAND_ID
                        + "]"));

        } catch (NotEnabledException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: the handler associated to the command [id=" + NEW_WIZARD_COMMAND_ID
                        + "] is not enabled"));

        } catch (NotHandledException e) {
            StatusHandler.log(new Status(IStatus.ERROR, IntroPlugin.PLUGIN_ID,
                    "Failed to open the dashboard: no handler associated to the command [id=" + NEW_WIZARD_COMMAND_ID
                        + "]"));
        }

    }

}
