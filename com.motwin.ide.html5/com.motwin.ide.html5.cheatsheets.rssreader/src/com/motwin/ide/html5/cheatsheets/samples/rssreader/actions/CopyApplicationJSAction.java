/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.rssreader.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.CopyApplicationJSSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyApplicationJSAction extends AbstractRssReaderAction {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new CopyApplicationJSSampleOperation(this, aJsProject).run();
    }
}
