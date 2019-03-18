/**
 * 
 */
package com.motwin.ide.android.cheatsheets.samples.helloworld.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.android.cheatsheets.operations.OpenMappingPropertiesOperation;

/**
 * @author ctranxuan
 * 
 */
public final class OpenMappingPropertiesAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new OpenMappingPropertiesOperation(this, aJavaProject).run();
    }

}
