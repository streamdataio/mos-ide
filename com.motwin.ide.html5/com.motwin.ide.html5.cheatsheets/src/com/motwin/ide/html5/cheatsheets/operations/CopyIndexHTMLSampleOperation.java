/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.operations;

import org.eclipse.wst.jsdt.core.IJavaScriptProject;

import com.motwin.ide.cheatsheets.operations.CopyFileOperation.OperationBuilder;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;
import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5SampleAction;

/**
 * @author ctranxuan
 * 
 */
public final class CopyIndexHTMLSampleOperation {
    private final AbstractHTML5SampleAction action;
    private final IJavaScriptProject        jsProject;

    public CopyIndexHTMLSampleOperation(final AbstractHTML5SampleAction anAction, final IJavaScriptProject aJsProject) {
        action = anAction;
        jsProject = aJsProject;
    }

    public void run() {
        OperationBuilder builder;
        builder = new OperationBuilder();

        // @formatter:off
        builder.fileName(action.getIndexHTML())
               .rootPath("")
               .folderPath(action.getWWWFolder())
               .sampleCodePath(action.getSampleCodePath())
               .openEditorID(JDTUtil.WTP_HTML_EDITOR_ID)
               .pluginID(action.getPluginID())
               .project(jsProject.getProject())
               .update(true)
               .build()
               .run();
        // @formatter:on
    }
}
