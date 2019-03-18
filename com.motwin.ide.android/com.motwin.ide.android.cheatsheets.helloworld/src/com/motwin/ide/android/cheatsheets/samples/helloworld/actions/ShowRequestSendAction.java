/**
 * 
 */
package com.motwin.ide.android.cheatsheets.samples.helloworld.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.helpers.JavaShowLineActionHelper;

/**
 * @author ctranxuan
 * 
 */
public final class ShowRequestSendAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("HelloApp1.java", 108);
    }

}
