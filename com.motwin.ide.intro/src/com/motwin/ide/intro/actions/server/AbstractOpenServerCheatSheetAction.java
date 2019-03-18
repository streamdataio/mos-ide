/**
 * 
 */
package com.motwin.ide.intro.actions.server;

import java.util.Properties;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.OpenCheatSheetAction;
import org.eclipse.ui.intro.IIntroSite;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractOpenServerCheatSheetAction extends DefaultServerAction {
    private final String cheatSheetId;

    /**
     * @param aCheatSheetId
     */
    public AbstractOpenServerCheatSheetAction(final String aCheatSheetId) {
        super();
        cheatSheetId = aCheatSheetId;
    }

    @Override
    protected void runAction(final IIntroSite aSite, final Properties aParams) {
        IWorkbenchWindow activeWindow;
        activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        activeWindow.getShell().getDisplay().asyncExec(new Runnable() {

            @Override
            public void run() {
                new OpenCheatSheetAction(cheatSheetId).run();

            }
        });
    }

}
