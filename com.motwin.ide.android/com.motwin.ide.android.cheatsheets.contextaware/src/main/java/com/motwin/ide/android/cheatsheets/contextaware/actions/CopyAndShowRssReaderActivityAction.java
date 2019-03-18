/**
 * 
 */
package com.motwin.ide.android.cheatsheets.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.helpers.JavaShowLineActionHelper;
import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public final class CopyAndShowRssReaderActivityAction extends AbstractContextAwareAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("RssReaderActivity.java")
                    .folderPath(getSrcJavaFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on
        operation.run();

        new JavaShowLineActionHelper(this, aJavaProject).showLine("RssReaderActivity.java", 21);

    }

}
