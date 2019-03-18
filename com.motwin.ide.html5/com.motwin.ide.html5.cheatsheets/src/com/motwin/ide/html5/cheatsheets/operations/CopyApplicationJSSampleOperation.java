/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.operations;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5SampleAction;
import com.motwin.ide.html5.cheatsheets.operations.CopyApplicationJSOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public class CopyApplicationJSSampleOperation {
    private final AbstractHTML5SampleAction action;
    private final IJavaScriptProject        jsProject;

    public CopyApplicationJSSampleOperation(final AbstractHTML5SampleAction aAction, final IJavaScriptProject aJsProject) {
        action = aAction;
        jsProject = aJsProject;
    }

    public void run() {
        // @formatter:off
        new OperationBuilder()
                    .fileName(action.getApplicationJS())
                    .jsProject(jsProject)
                    .openJSEditor(true)
                    .pluginID(action.getPluginID())
                    .sampleCodePath(action.getSampleCodePath())
                    .wwwFolderPath(action.getWWWFolder())
                    .build()
                    .run();
        // @formatter:on
    }
}
