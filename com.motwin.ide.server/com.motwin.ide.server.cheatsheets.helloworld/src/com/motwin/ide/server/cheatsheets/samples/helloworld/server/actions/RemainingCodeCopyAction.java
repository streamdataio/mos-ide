/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.helloworld.server.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyFilesSampleOperation.OperationBuilder;

/**
 * @author ctranxuan
 * 
 */
public class RemainingCodeCopyAction extends AbstractHelloWorldAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemainingCodeCopyAction.class);

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyFilesSampleOperation operation = 
            new OperationBuilder()
                .includeFiles("^\\w+(Interceptor|Processor).java")
                .excludeFiles("(HelloWorldRequestProcessor|HelloWorldMessageProcessor|ExceptionInterceptor).java")
                .folderPath(getSrcJavaFolder())
                .pluginID(getPluginID())
                .project(aJavaProject)
                .sampleCodePath(getSampleCodePath())
                .build();
        
        operation.run();
        
        CopyFilesSampleOperation copySpringFilesOp = 
                new OperationBuilder()
                    .includeFiles("^application.xml")
                    .folderPath(getSrcSpringFolder())
                    .pluginID(getPluginID())
                    .project(aJavaProject)
                    .sampleCodePath(getSampleCodePath())
                    .build();
            
        copySpringFilesOp.run();
        // @formatter:on

        deleteVirtualDbFile(aJavaProject);
    }

    private void deleteVirtualDbFile(final IJavaProject aJavaProject) {
        IProject project;
        project = aJavaProject.getProject();

        String virtualDbPath = "src/main/resources/META-INF/spring/virtualdb.xml";

        try {
            IFile file;
            file = project.getFile(virtualDbPath);
            file.delete(true, null);
        } catch (CoreException e) {
            LOGGER.error("Unable to delete file " + virtualDbPath, e);
        }
    }

}
