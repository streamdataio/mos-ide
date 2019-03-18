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
public final class CopyNotifManagerDemoActivityAction extends AbstractNotifManagerDemoAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        copyActivity(aJavaProject);
        copyLayout(aJavaProject);
        copyResources(aJavaProject);
    }

    private void copyActivity(IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("NotifManagerDemoActivity.java")
                    .folderPath(getSrcJavaFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();
    }

    private void copyLayout(IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("^\\w*(main).xml")
                    .folderPath(getResFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();

    }

    private void copyResources(IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("^\\w*(motwin_logo).png")
                    .folderPath(getResFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();

        operation = new CopyFilesSampleOperation.OperationBuilder().includeFiles("^\\w*(strings).xml")
                .folderPath(getResFolder()).pluginID(getPluginID()).project(aJavaProject)
                .sampleCodePath(getSampleCodePath()).build();
        // @formatter:on

        operation.run();

    }

}
