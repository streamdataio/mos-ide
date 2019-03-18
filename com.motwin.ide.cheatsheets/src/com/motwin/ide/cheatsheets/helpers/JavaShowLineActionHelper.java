/**
 * 
 */
package com.motwin.ide.cheatsheets.helpers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.text.BadLocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.motwin.ide.cheatsheets.actions.IJavaProjectAction;

/**
 * @author ctranxuan
 * 
 */
public final class JavaShowLineActionHelper {
    private static final Logger      LOGGER = LoggerFactory.getLogger(JavaShowLineActionHelper.class);

    private final IJavaProjectAction action;
    private final IJavaProject       project;

    public JavaShowLineActionHelper(final IJavaProjectAction anAction, final IJavaProject aJavaProject) {
        action = anAction;
        project = aJavaProject;
    }

    /**
     * Open the java editor of the given file at the given line number.
     * 
     * <br/>
     * <br/>
     * <b>NB:</b> any error is caught and logged. It means that if the editor
     * fails to open the file for any reasons, this method just logs the cause.
     * 
     * @param aFileName
     *            the file name of the java file to open
     * @param aLineNumber
     *            the line number at which the editor should open
     * 
     * @see JavaShowLineHelper#showLine(int) if this method does not fit your
     *      purpose
     */
    public void showLine(final String aFileName, final int aLineNumber) {
        try {
            // @formatter:off
            new JavaShowLineHelper.Builder()
                .project(project)
                .fileName(aFileName)
                .srcJavaFolder(action.getSrcJavaFolder())
                .withPackage(action.getJavaBasePackage())
                .build()
                .showLine(aLineNumber);
            // @formatter:on

        } catch (CoreException e) {
            // just log
            LOGGER.error("failed to open the editor of '" + aFileName + "' at the line " + aLineNumber
                + ": a workspace error occured", e);
        } catch (BadLocationException e) {
            // ignore
            LOGGER.error("failed to open the editor of " + aFileName + "' at the line " + aLineNumber
                + ": the location is not correct", e);
        }
    }
}
