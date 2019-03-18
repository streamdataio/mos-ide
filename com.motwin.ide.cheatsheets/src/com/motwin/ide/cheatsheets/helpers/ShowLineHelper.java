/**
 * 
 */
package com.motwin.ide.cheatsheets.helpers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
// TODO merge code ShowLineHelper and JavaShowLinedHelper (which should inherit
// from the previous one)
public final class ShowLineHelper {
    private final IFile  file;
    private final String editorID;
    private final String pluginID;

    public ShowLineHelper(final IProject aProject, final String aPath, final String aPluginID, final String anEditorID) {
        Preconditions.checkNotNull(aProject, "aProject cannot be null");
        Preconditions.checkNotNull(aPath, "aPath cannot be null");
        Preconditions.checkNotNull(anEditorID, "anEditorID cannot be null");
        Preconditions.checkNotNull(aPluginID, "pluginID cannot be null");

        file = aProject.getFile(aPath);
        pluginID = aPluginID;
        editorID = anEditorID;

    }

    public void showLine(final int aLineNumber) throws BadLocationException, PartInitException {
        IEditorPart editorPart;
        editorPart = JDTUtil.openEditor(editorID, file);

        ITextEditor editor;
        editor = (ITextEditor) editorPart.getAdapter(ITextEditor.class);

        JDTUtil.openEditorAtLine(editor, aLineNumber);
    }

    public void showLineAndLogExceptions(final int aLineNumber) {
        try {
            showLine(aLineNumber);

        } catch (PartInitException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to open the editor [id=" + editorID
                        + "] for the file [" + file + "]", e));
        } catch (BadLocationException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to open the editor [id=" + editorID
                        + "] for the file [" + file + "] at line " + aLineNumber + ": the location is not correct", e));
        }
    }
}
