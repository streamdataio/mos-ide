/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.rssreader.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.OpenIndexHTMLSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public final class OpenIndexHTMLBrowserAction extends AbstractRssReaderAction {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new OpenIndexHTMLSampleOperation(this, aJsProject).run();
    }
}
