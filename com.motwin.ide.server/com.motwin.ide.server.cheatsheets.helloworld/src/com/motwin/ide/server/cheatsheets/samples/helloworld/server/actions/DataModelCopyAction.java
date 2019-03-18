/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.helloworld.server.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class DataModelCopyAction extends AbstractHelloWorldAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new OperationBuilder()
                .excludeFiles("^\\w+(Interceptor|Processor).java")
                .folderPath(getSrcJavaFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();
        
        operation.run();
        // @formatter:on
    }
}
