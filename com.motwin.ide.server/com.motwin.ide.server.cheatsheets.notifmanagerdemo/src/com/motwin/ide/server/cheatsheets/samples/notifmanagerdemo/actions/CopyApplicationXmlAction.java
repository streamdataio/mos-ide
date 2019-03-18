/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.notifmanagerdemo.actions;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation;
import com.motwin.ide.cheatsheets.operations.OpenSpringFileOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public final class CopyApplicationXmlAction extends AbstractNotifManagerDemoAction {

    private static final String APPLICATION_XML_FILE = "application.xml";

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
                new CopyFilesSampleOperation.OperationBuilder()
                    .includeFiles("^\\w+(Interceptor|Processor).java")
                    .folderPath(getSrcJavaFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
            
            operation.run();
            
        CopyFilesSampleOperation copyOp = 
            new CopyFilesSampleOperation.OperationBuilder()
                .includeFiles(APPLICATION_XML_FILE)
                .folderPath(getSrcSpringFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();

        copyOp.run();
        // @formatter:on

        // @formatter:off
        OpenSpringFileOperation openSpringFileOp;
        openSpringFileOp = new OperationBuilder()
                                .activeTabId("com.springsource.sts.config.ui.beanConfigFile.source.EditorContext")
                                .filePath(getApplicationXml())
                                .pluginID(getPluginID())
                                .project(aJavaProject)
                                .build();
        
        openSpringFileOp.run();
        // @formatter:on
    }

}
