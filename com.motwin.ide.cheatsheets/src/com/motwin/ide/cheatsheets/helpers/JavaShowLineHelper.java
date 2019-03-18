/**
 * 
 */
package com.motwin.ide.cheatsheets.helpers;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public final class JavaShowLineHelper {

    private final IJavaProject project;
    private final String       packagee;
    private final String       fileName;
    private final String       srcJavaFolder;

    public static final class Builder {

        private IJavaProject project;
        private String       packagee;
        private String       fileName;
        private String       srcJavaFolder;

        public Builder() {
        }

        public Builder project(final IJavaProject aProject) {
            project = aProject;
            return this;
        }

        public Builder withPackage(final String aPackage) {
            packagee = aPackage;
            return this;
        }

        public Builder fileName(final String aFileName) {
            fileName = aFileName;
            return this;
        }

        public Builder srcJavaFolder(final String aSrcJavaFolder) {
            srcJavaFolder = aSrcJavaFolder;
            return this;
        }

        public JavaShowLineHelper build() {
            Preconditions.checkState(fileName != null, "fileName cannot be null");
            Preconditions.checkState(packagee != null, "package cannot be null");
            Preconditions.checkState(project != null, "project cannot be null");
            Preconditions.checkState(srcJavaFolder != null, "srcJavaFolder cannot be null");

            return new JavaShowLineHelper(this);
        }
    }

    private JavaShowLineHelper(final Builder aBuilder) {
        project = aBuilder.project;
        packagee = aBuilder.packagee;
        fileName = aBuilder.fileName;
        srcJavaFolder = aBuilder.srcJavaFolder;
    }

    public void showLine(final int aLine) throws PartInitException, JavaModelException, BadLocationException {
        IEditorPart editorPart;
        editorPart = JDTUtil.openJavaFileEditor(project, srcJavaFolder, packagee, fileName);

        ITextEditor editor;
        editor = (ITextEditor) editorPart.getAdapter(ITextEditor.class);

        JDTUtil.openEditorAtLine(editor, aLine);
    }
}
