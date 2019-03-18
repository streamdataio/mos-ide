/**
 * 
 */
package com.motwin.ide.intro.actions.html5;

import java.util.Properties;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroSite;

import com.motwin.ide.intro.internal.operations.OpenNewWizardOperation;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractHTML5WizardAction extends DefaultHTML5Action {
    private final String wizardId;

    public AbstractHTML5WizardAction(final String aWizardId) {
        super();
        wizardId = aWizardId;
    }

    @Override
    protected void runAction(final IIntroSite aSite, final Properties aParams) {
        IWorkbenchWindow activeWindow;
        activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        activeWindow.getShell().getDisplay().asyncExec(new Runnable() {

            @Override
            public void run() {
                new OpenNewWizardOperation(wizardId).execute();
            }
        });
    }

}
