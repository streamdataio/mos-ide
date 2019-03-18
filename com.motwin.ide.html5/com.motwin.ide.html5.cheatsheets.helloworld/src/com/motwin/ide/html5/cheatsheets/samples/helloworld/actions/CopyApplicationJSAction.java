/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.helloworld.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.CopyApplicationJSSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyApplicationJSAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new CopyApplicationJSSampleOperation(this, aJsProject).run();
    }
}
