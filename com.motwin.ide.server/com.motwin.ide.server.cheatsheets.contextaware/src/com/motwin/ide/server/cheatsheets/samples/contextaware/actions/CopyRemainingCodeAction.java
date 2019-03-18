/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyRemainingCodeAction extends AbstractContextAwareAction implements ICheatSheetAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
                new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("^(\\w+).java")
                    .folderPath(getSrcJavaFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
            
            operation.run();
            
        CopyFilesSampleOperation copyOp = 
            new CopyFilesSampleOperation.OperationBuilder()
                .includeFiles(APPLICATION_XML_FILE)
                .folderPath(getSrcSpringFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();

        copyOp.run();
        // @formatter:on

    }

}
