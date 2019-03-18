/**
 * 
 */
package com.motwin.ide.android.cheatsheets.realtimepush.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public final class RealTimePushListCopyAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        copyResFiles(aJavaProject);
        copyManifest(aJavaProject);
        copyEntryFragmentFiles(aJavaProject);
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
                .includeFiles("^(\\w+).xml")
                .folderPath(getResFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();

        operation.run();
        // @formatter:on
    }

    /**
     * @param aJavaProject
     */
    private void copyEntryFragmentFiles(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("RealTimePushList.java")
                    .folderPath(getSrcJavaFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();
    }
}
