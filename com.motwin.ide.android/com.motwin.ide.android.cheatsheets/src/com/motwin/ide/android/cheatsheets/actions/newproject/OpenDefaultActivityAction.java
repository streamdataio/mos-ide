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
public final class OpenDefaultActivityAction extends AbstractNewProjectAndroidAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("DefaultActivity.java", 16);
    }

}
