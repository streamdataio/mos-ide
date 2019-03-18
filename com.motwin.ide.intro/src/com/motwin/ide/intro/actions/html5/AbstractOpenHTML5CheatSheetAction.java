/**
 * 
 */
package com.motwin.ide.intro.actions.html5;

import java.util.Properties;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.OpenCheatSheetAction;
import org.eclipse.ui.intro.IIntroSite;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractOpenHTML5CheatSheetAction extends DefaultHTML5Action {
    private final String cheatSheetId;

    public AbstractOpenHTML5CheatSheetAction(final String aCheatSheetId) {
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
