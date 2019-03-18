/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.realtimepush.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.CopyApplicationJSSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyApplicationJSAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new CopyApplicationJSSampleOperation(this, aJsProject).run();
    }
}
