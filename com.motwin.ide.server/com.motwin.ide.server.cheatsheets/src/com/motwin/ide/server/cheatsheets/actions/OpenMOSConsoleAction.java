/**
 * 
 */
package com.motwin.ide.server.cheatsheets.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.motwin.ide.ui.operations.OpenBrowserOperation;

/**
 * @author ctranxuan
 * 
 */
public class OpenMOSConsoleAction extends Action implements ICheatSheetAction {

    @Override
    public void run(final String[] aParams, final ICheatSheetManager aManager) {
        new OpenBrowserOperation("http://localhost:8080/mos").run();
    }

}
