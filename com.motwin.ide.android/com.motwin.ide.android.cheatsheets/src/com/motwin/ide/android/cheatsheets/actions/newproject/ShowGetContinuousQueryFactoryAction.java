/**
 * 
 */
package com.motwin.ide.android.cheatsheets.actions.newproject;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.helpers.JavaShowLineActionHelper;

/**
 * @author ctranxuan
 * 
 */
public final class ShowGetContinuousQueryFactoryAction extends AbstractNewProjectAndroidAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("MotwinFacade.java", 209);
    }

}
