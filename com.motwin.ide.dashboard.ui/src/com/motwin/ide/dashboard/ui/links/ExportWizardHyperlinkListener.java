/**
 * 
 */
package com.motwin.ide.dashboard.ui.links;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;

import com.motwin.ide.dashboard.ui.links.AbstractWizardHyperlinkListener;

/**
 * 
 */
public final class ExportWizardHyperlinkListener extends AbstractWizardHyperlinkListener {

    @Override
    protected IWizardDescriptor locateWizard(String aWizardId) {
        IWorkbench workbench;
        workbench = PlatformUI.getWorkbench();
        return workbench.getExportWizardRegistry().findWizard(aWizardId);
    }

}
