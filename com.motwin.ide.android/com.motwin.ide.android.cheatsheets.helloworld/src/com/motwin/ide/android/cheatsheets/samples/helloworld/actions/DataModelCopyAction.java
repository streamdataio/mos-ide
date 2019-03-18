/**
 * 
 */
package com.motwin.ide.android.cheatsheets.samples.helloworld.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class DataModelCopyAction extends AbstractHelloWorld1Action {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyJavaFileSampleOperation operation;
        operation = 
                new OperationBuilder()
                    .action(this)
                    .project(aJavaProject)
                    .fileName("Person.java")
                    .build();
        // @formatter:on

        operation.run();

    }

}
