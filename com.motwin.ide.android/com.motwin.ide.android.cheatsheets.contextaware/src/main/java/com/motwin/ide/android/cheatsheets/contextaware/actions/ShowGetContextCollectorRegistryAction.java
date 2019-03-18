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
public final class ShowGetContextCollectorRegistryAction extends AbstractContextAwareAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("MotwinFacade.java", 395);

    }

}
