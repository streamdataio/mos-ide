/**
 * 
 */
package com.motwin.ide.android.cheatsheets.notifmanagerdemo.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;

/**
 * @author ctranxuan
 * 
 */
public final class CopyManifestAction extends AbstractNotifManagerDemoAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        copyManifest(aJavaProject);
    }

    /**
     * 
     */
    private void copyManifest(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("AndroidManifest.xml")
                    .folderPath("")
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();
    }
}
