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
public final class CopyMotwinFacadeAction extends AbstractNotifManagerDemoAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        copyMotwinFacade(aJavaProject);
        copyNotifManagerProperties(aJavaProject);
    }

    /**
     * 
     */
    private void copyMotwinFacade(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("MotwinFacade.java")
                    .folderPath(getSrcJavaFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();
    }

    /**
     * 
     */
    private void copyNotifManagerProperties(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("gcm.properties")
                    .folderPath(getAssetsFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();
    }
}
