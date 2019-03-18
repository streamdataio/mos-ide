/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;

import com.motwin.ide.cheatsheets.helpers.ShowLineHelper;
import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation;

/**
 * @author ctranxuan
 * 
 */
public class ShowInterceptUpXMLAction extends AbstractContextAwareAction implements ICheatSheetAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        new ShowLineHelper(aJavaProject.getProject(), getApplicationXml(), 
                getPluginID(), OpenSpringFileOperation.SPRING_CONFIG_EDITOR_ID).showLineAndLogExceptions(46);
//        OpenSpringFileOperation openSpringFileOp;
//        openSpringFileOp = new OperationBuilder()
//                                .activeTabId("com.springsource.sts.config.ui.beanConfigFile.source.EditorContext")
//                                .filePath(getApplicationXml())
//                                .pluginID(getPluginID())
//                                .project(aJavaProject)
//                                .build();
//        
//        openSpringFileOp.run();
        // @formatter:on
    }

}
