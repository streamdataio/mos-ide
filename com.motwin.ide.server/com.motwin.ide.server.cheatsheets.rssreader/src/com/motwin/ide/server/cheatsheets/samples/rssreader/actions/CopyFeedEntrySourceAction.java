/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.rssreader.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class CopyFeedEntrySourceAction extends AbstractRssReaderAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyJavaFileSampleOperation operation;
        operation = 
                new OperationBuilder()
                    .action(this)
                    .project(aJavaProject)
                    .fileName("FeedEntrySource.java")
                    .update(true)
                    .build();
        // @formatter:on

        operation.run();
    }

}
