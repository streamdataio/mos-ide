/**
 * 
 */
package com.motwin.ide.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

/**
 * @author fbou
 * 
 */
public abstract class AbstractWizardPage extends WizardPage {

    protected final ModifyListener checkPageCompleteListener;

    /**
     * @param aPageName
     */
    protected AbstractWizardPage(String aPageName) {
        super(aPageName);
        setPageComplete(false);
        checkPageCompleteListener = new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent aE) {
                checkPageComplete();
            }
        };
    }

    /**
     * Check if the page is completed
     */
    protected final void checkPageComplete() {
        setPageComplete(validate());
    }

    /**
     * @return
     */
    protected boolean validate() {
        setErrorMessage(null);
        return true;
    }

}
