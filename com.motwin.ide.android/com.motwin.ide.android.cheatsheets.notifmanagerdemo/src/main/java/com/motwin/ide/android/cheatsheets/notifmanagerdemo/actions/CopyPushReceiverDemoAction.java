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
public final class CopyPushReceiverDemoAction extends AbstractNotifManagerDemoAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        copyPushReceiverDemoClass(aJavaProject);
        copyPushIcon(aJavaProject);
    }

    private void copyPushReceiverDemoClass(IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("PushReceiverDemo.java")
                    .folderPath(getSrcJavaFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();
    }

    private void copyPushIcon(IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation;
        operation = new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("^\\w*(pushicon).png")
                    .folderPath(getResFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
        // @formatter:on

        operation.run();

    }

}
