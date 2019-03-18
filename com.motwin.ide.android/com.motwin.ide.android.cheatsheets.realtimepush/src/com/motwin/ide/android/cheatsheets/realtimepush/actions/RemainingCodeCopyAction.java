/**
 * 
 */
package com.motwin.ide.android.cheatsheets.realtimepush.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class RemainingCodeCopyAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        copyResFiles(aJavaProject);
        copyManifest(aJavaProject);
        copyJavaFiles(aJavaProject);
    }

    /**
     * @param aJavaProject
     */
    private void copyJavaFiles(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new OperationBuilder()
                .includeFiles("^(\\w+).java")
                .excludeFiles("^(MotwinFacade|RealTimePushActivity).java")
                .folderPath(getSrcJavaFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();
        
        operation.run();
        // @formatter:on
    }

    private void copyManifest(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new CopyFilesSampleOperation.OperationBuilder()
                .includeFiles("AndroidManifest.xml")
                .folderPath("")
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();

        operation.run();
        // @formatter:on        
    }

    private void copyResFiles(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new CopyFilesSampleOperation.OperationBuilder()
                .includeFiles("^(\\w+)(.xml|.png)")
                .folderPath(getResFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();

        operation.run();
        // @formatter:on
    }

}
