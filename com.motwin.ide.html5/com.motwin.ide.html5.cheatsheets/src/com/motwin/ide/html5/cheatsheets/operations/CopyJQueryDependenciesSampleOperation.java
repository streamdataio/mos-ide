/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.operations;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5SampleAction;

/**
 * @author ctranxuan
 * 
 */
public class CopyJQueryDependenciesSampleOperation {
    private final AbstractHTML5SampleAction action;
    private final IJavaScriptProject        jsProject;

    public CopyJQueryDependenciesSampleOperation(final AbstractHTML5SampleAction aAction,
            final IJavaScriptProject aJsProject) {
        action = aAction;
        jsProject = aJsProject;
    }

    public void run() {
        // @formatter:off
        CopyFilesSampleOperation operation = 
                new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles(".*")
                    .folderPath(action.getWWWFolder())
                    .pluginID(action.getPluginID())
                    .project(jsProject.getProject())
                    .sampleCodePath("code/jquery")
                    .build();
        operation.run();
        // @formatter:on
    }
}
