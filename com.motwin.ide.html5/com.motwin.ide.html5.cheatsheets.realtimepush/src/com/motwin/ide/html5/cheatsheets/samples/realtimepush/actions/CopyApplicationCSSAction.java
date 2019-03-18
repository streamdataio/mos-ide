/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.realtimepush.actions;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.cheatsheets.operations.CopyFileOperation;

/**
 * @author ctranxuan
 * 
 */
public class CopyApplicationCSSAction extends AbstractRealTimePushAction {

    @Override
    protected void perform(final IJavaScriptProject aJsProject) {
        // @formatter:off
        CopyFileOperation operation = 
                new CopyFileOperation.OperationBuilder()
                    .fileName("application.css")
                    .rootPath("")
                    .folderPath(getWWWFolder())
                    .pluginID(getPluginID())
                    .project(aJsProject.getProject())
                    .sampleCodePath(getSampleCodePath())
                    .build();
        operation.run();
        // @formatter:on
    }
}
