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
public final class MessageProcessorCopyAction extends AbstractHelloWorldAction {

    private static final String HELLO_WORLD_MESSAGE_PROCESSOR_JAVA_FILE = "HelloWorldMessageProcessor.java";

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        // @formatter:off
        CopyJavaFileSampleOperation copyOp;
        copyOp = 
                new OperationBuilder()
                    .action(this)
                    .project(aJavaProject)
                    .fileName(HELLO_WORLD_MESSAGE_PROCESSOR_JAVA_FILE)
                    .build();
        // @formatter:on

        copyOp.run();

        String path;
        path = JDTUtil.toPath(getSrcJavaFolder(), getJavaBasePackage()) + IPath.SEPARATOR
            + HELLO_WORLD_MESSAGE_PROCESSOR_JAVA_FILE;

        new OpenFileOperation(aJavaProject, path, getPluginID()).run();
    }
}
