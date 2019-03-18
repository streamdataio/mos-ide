/**
 * 
 */
package com.motwin.ide.android.cheatsheets.rssreader.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class RemainingCodeCopyAction extends AbstractRssReaderAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new OperationBuilder()
                .includeFiles("^(\\w+).java")
                .excludeFiles("^((\\w*(Entry)\\w*)|(MotwinFacade|RssReaderActivity)).java")
                .folderPath(getSrcJavaFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();
        
        operation.run();
        // @formatter:on
    }

}
