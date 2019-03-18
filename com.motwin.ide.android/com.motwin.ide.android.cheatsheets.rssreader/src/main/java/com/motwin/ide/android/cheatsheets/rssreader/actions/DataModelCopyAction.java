/**
 * 
 */
package com.motwin.ide.android.cheatsheets.rssreader.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class DataModelCopyAction extends AbstractRssReaderAction {

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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.motwin.ide.android.cheatsheets.rssreader.actions.AbstractRssReaderAction
     * #getJavaBasePackage()
     */
    @Override
    public String getJavaBasePackage() {
        return super.getJavaBasePackage() + ".datamodel";
    }

}
