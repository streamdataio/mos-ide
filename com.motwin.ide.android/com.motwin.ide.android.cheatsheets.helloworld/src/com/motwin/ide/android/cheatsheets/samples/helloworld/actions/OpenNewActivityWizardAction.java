/**
 * 
 */
package com.motwin.ide.android.cheatsheets.samples.helloworld.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.android.cheatsheets.operations.OpenNewActivityWizardOperation;

/**
 * @author ctranxuan
 * 
 */
public final class OpenNewActivityWizardAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new OpenNewActivityWizardOperation(aJavaProject, getPluginID()).run();
    }

}
