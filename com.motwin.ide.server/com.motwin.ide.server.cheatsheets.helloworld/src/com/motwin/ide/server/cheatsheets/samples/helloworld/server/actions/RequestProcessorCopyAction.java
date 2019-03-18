/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.helloworld.server.actions;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation;
import com.motwin.ide.cheatsheets.operations.CopyJavaFileSampleOperation.OperationBuilder;
import com.motwin.ide.cheatsheets.operations.OpenFileOperation;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */

public final class RequestProcessorCopyAction extends AbstractHelloWorldAction {

    private static final String HELLO_WORLD_REQUEST_PROCESSOR_JAVA_FILE = "HelloWorldRequestProcessor.java";

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyJavaFileSampleOperation operation;
        operation = 
                new OperationBuilder()
                    .action(this)
                    .project(aJavaProject)
                    .fileName(HELLO_WORLD_REQUEST_PROCESSOR_JAVA_FILE)
                    .build();
        // @formatter:on

        operation.run();

        String path;
        path = JDTUtil.toPath(getSrcJavaFolder(), getJavaBasePackage()) + IPath.SEPARATOR
            + HELLO_WORLD_REQUEST_PROCESSOR_JAVA_FILE;

        new OpenFileOperation(aJavaProject, path, getPluginID()).run();
    }
}
