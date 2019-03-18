/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.rssreader.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.CopyJQueryDependenciesSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyJQueryDependenciesAction extends AbstractRssReaderAction {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new CopyJQueryDependenciesSampleOperation(this, aJsProject).run();
    }

}
