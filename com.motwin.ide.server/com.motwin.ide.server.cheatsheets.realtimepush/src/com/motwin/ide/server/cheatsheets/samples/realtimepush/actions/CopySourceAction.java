/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.realtimepush.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class CopySourceAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyJavaFileSampleOperation operation;
        operation = 
                new OperationBuilder()
                    .action(this)
                    .project(aJavaProject)
                    .fileName("RealTimePushSource.java")
                    .update(true)
                    .build();
        // @formatter:on

        operation.run();
    }

}
