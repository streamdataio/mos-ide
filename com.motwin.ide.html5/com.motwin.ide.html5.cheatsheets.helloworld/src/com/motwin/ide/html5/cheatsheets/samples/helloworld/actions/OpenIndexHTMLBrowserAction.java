/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.helloworld.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.OpenIndexHTMLSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public final class OpenIndexHTMLBrowserAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new OpenIndexHTMLSampleOperation(this, aJsProject).run();
    }
}
