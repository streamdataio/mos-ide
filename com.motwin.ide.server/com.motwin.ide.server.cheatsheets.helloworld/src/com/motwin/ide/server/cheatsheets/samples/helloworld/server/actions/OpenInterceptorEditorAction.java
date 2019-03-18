/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.helloworld.server.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation;
import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class OpenInterceptorEditorAction extends AbstractHelloWorldAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        OpenSpringFileOperation openSpringFileOp;
        openSpringFileOp = new OperationBuilder()
                                .activeTabId("com.motwin.ide.server.ui.editor.application.views.ApplicationFormPage")
                                .filePath(getApplicationXml())
                                .pluginID(getPluginID())
                                .project(aJavaProject)
                                .build();
        // @formatter:on

        openSpringFileOp.run();

    }

}
