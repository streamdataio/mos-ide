/**
 * 
 */
package com.motwin.ide.android.cheatsheets.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.helpers.JavaShowLineActionHelper;

/**
 * @author ctranxuan
 * 
 */
public class ShowStartContextCollectorRegistryAction extends AbstractContextAwareAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("MotwinFacade.java", 434);

    }

}
