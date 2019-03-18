/**
 * 
 */
package com.motwin.ide.android.cheatsheets.realtimepush.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.helpers.JavaShowLineActionHelper;

/**
 * @author ctranxuan
 * 
 */
public final class ShowQueryControllerInitAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("RealTimePushList.java", 30);
    }

}
