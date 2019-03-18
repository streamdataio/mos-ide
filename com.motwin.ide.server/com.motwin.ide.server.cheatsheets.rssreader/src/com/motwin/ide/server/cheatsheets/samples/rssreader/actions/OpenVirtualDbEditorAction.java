/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.rssreader.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation;
import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class OpenVirtualDbEditorAction extends AbstractRssReaderAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        OpenSpringFileOperation openSpringFileOp;
        openSpringFileOp = new OperationBuilder()
                                .activeTabId("com.motwin.ide.server.ui.editor.view.VirtualdbFormPage")
                                .filePath(getVirtualDbXml())
                                .pluginID(getPluginID())
                                .project(aJavaProject)
                                .build();
        // @formatter:on

        openSpringFileOp.run();

    }

}
