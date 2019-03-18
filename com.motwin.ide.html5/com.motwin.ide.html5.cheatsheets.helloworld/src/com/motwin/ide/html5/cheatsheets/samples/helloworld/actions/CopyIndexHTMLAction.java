/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.helloworld.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.CopyIndexHTMLSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyIndexHTMLAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new CopyIndexHTMLSampleOperation(this, aJsProject).run();
    }

}
