/**
 * 
 */
package com.motwin.ide.android.cheatsheets.rssreader.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.helpers.JavaShowLineActionHelper;

/**
 * @author ctranxuan
 * 
 */
public final class ShowQueryDataChangedAction extends AbstractRssReaderAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new JavaShowLineActionHelper(this, aJavaProject).showLine("EntryFragment.java", 148);
    }

}
