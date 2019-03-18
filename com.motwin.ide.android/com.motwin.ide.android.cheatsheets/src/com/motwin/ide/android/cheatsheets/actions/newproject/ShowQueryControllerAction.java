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
public final class ShowQueryControllerAction extends AbstractNewProjectAndroidAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("Main.java", 30);
    }
}
