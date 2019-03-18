/**
 * 
 */
package com.motwin.ide.android.cheatsheets.operations;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.IWizardDescriptor;

/**
 * @author ctranxuan
 * 
 */
public class OpenNewActivityWizardOperation {
    private final IJavaProject project;
    private final String       pluginID;

    public OpenNewActivityWizardOperation(final IJavaProject aProject, final String aPluginID) {
        project = aProject;
        pluginID = aPluginID;
    }

    public void run() {
        try {

            IWorkbench workbench;
            workbench = PlatformUI.getWorkbench();

            IStructuredSelection selection;
            selection = new StructuredSelection(project);

            IWizardDescriptor descriptor;
            descriptor = workbench.getNewWizardRegistry().findWizard(
                    "com.android.ide.eclipse.editors.wizards.NewActivityWizard");

            if (descriptor != null) {
                final IWorkbenchWizard wizard = descriptor.createWizard();
                wizard.init(workbench, selection);

                Shell shell;
                shell = workbench.getActiveWorkbenchWindow().getShell();

                WizardDialog wizardDialog;
                wizardDialog = new WizardDialog(shell, wizard) {

                    @Override
                    protected Control createButtonBar(final Composite aParent) {
                        /*
                         * Big hack of the death that makes die!!! Due to a
                         * weird initialization of the pages in the
                         * NewActivityWizard!
                         */
                        wizard.getNextPage(wizard.getStartingPage());
                        return super.createButtonBar(aParent);
                    }
                };

                wizardDialog.setTitle(wizard.getWindowTitle());
                wizardDialog.open();

            }

        } catch (CoreException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID,
                            "A workspace error occurred while attempting to open the NewActivityWizard of the project ["
                                + project.getElementName() + "]", e));
        }

    }
}
