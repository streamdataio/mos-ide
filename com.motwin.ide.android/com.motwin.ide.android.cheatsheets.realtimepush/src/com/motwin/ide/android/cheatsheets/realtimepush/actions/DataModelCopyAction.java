/**
 * 
 */
package com.motwin.ide.android.cheatsheets.realtimepush.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class DataModelCopyAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyJavaFileSampleOperation operation;
        operation = 
                new OperationBuilder()
                    .action(this)
                    .project(aJavaProject)
                    .fileName("M.java")
                    .build();
        // @formatter:on

        operation.run();

    }

    @Override
    public String getJavaBasePackage() {
        return super.getJavaBasePackage() + ".datamodel";
    }

}
