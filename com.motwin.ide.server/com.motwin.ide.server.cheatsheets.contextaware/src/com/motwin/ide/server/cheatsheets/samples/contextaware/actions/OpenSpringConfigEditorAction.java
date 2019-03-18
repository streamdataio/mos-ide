/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;

import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation;
import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public class OpenSpringConfigEditorAction extends AbstractContextAwareAction implements ICheatSheetAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        OpenSpringFileOperation openSpringFileOp;
        openSpringFileOp = new OperationBuilder()
                                .activeTabId("com.springsource.sts.config.ui.editors.namespaces")
                                .filePath(getApplicationXml())
                                .pluginID(getPluginID())
                                .project(aJavaProject)
                                .build();
        
        openSpringFileOp.run();
        // @formatter:on

    }

}
