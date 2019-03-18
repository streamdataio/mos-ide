/**
 * 
 */
package com.motwin.ide.dashboard.ui.links;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;

/**
 * 
 */
public final class NewWizardHyperlinkListener extends AbstractWizardHyperlinkListener {

    @Override
    protected IWizardDescriptor locateWizard(String aWizardId) {
        IWorkbench workbench;
        workbench = PlatformUI.getWorkbench();
        return workbench.getNewWizardRegistry().findWizard(aWizardId);
    }

}
