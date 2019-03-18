/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;

import com.motwin.ide.cheatsheets.helpers.JavaShowLineActionHelper;

/**
 * @author ctranxuan
 * 
 */
public class ShowInterceptUpAction extends AbstractContextAwareAction implements ICheatSheetAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("CustomInstallationIdInjector.java", 64);

    }

}
