/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.helloworld.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.operations.CopyJQueryDependenciesSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyJQueryDependenciesAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        new CopyJQueryDependenciesSampleOperation(this, aJsProject).run();
    }

}
