/**
 * 
 */
package com.motwin.ide.intro.actions.server;

import java.util.Properties;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroSite;

import com.motwin.ide.intro.internal.operations.OpenNewWizardOperation;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractServerWizardAction extends DefaultServerAction {
    private final String wizardId;

    public AbstractServerWizardAction(final String aWizardId) {
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
