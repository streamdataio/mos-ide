/**
 * 
 */
package com.motwin.ide.dashboard.ui.links;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.wizards.IWizardDescriptor;

import com.google.common.base.Preconditions;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;

/**
 * @author ctranxuan
 * 
 */
// FIXME move into a non internal package!
public abstract class AbstractWizardHyperlinkListener extends HyperlinkAdapter implements IExecutableExtension {
    private String wizardId;

    public AbstractWizardHyperlinkListener() {
    }

    protected abstract IWizardDescriptor locateWizard(String aWizardId);

    /**
     * @param aWizardId
     */
    public AbstractWizardHyperlinkListener(final String aWizardId) {
        super();
        wizardId = aWizardId;
    }

    @Override
    public void linkActivated(final HyperlinkEvent anEvent) {
        try {
            IStructuredSelection selection;
            selection = new StructuredSelection();

            IWizardDescriptor descriptor;
            descriptor = locateWizard(wizardId);

            if (descriptor == null) {
                StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "Cannot open the wizard [id="
                    + wizardId + "]: no wizard found with this id"));

            } else {
                IWorkbench workbench;
                workbench = PlatformUI.getWorkbench();

                final IWorkbenchWizard wizard;
                wizard = descriptor.createWizard();
                wizard.init(workbench, selection);

                Shell shell;
                shell = workbench.getActiveWorkbenchWindow().getShell();

                WizardDialog wizardDialog;
                wizardDialog = new WizardDialog(shell, wizard);
                wizardDialog.setTitle(wizard.getWindowTitle());
                wizardDialog.open();
            }

        } catch (CoreException e) {
            StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "Cannot open the wizard ["
                + wizardId + "]", e));
        }
    }

    @Override
    public void setInitializationData(final IConfigurationElement aConfig, final String aPropertyName,
                                      final Object aData) throws CoreException {
        Preconditions.checkNotNull(aData, "aData cannot be null");
        wizardId = aData.toString();

    }

}
