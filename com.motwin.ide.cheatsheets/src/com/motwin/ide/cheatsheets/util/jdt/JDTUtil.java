/**
 * 
 */
package com.motwin.ide.cheatsheets.util.jdt;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.virgo.ide.manifest.core.BundleManifestUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
public final class JDTUtil {
    public static final String WTP_HTML_EDITOR_ID        = "org.eclipse.wst.html.core.htmlsource.source";
    public static final String WTP_WEB_BROWSER_EDITOR_ID = "org.eclipse.ui.browser.editor";
    public static final String WTP_JSDT_EDITOR_ID        = "org.eclipse.wst.jsdt.ui.CompilationUnitEditor";
    public static final String VIRGO_MANIFEST_EDITOR_ID  = "org.eclipse.virgo.ide.ui.bundlemanifest";

    public static IPackageFragmentRoot locateFragmentRoot(final IJavaProject aJavaProject, final String aSrcFolderName)
            throws JavaModelException {
        Preconditions.checkNotNull(aJavaProject, "aJavaProject cannot be null");
        Preconditions.checkNotNull(aSrcFolderName, "aSrcFolderName cannot be null");

        IPackageFragmentRoot[] packageFragmentRoots;
        packageFragmentRoots = aJavaProject.getAllPackageFragmentRoots();

        IPackageFragmentRoot srcRoot;
        srcRoot = null;

        String projectSrcFolderName;
        projectSrcFolderName = aJavaProject.getPath().toString() + IPath.SEPARATOR + aSrcFolderName;

        for (int i = 0; srcRoot == null && i < packageFragmentRoots.length; i++) {

            IPackageFragmentRoot packageFragmentRoot;
            packageFragmentRoot = packageFragmentRoots[i];

            if (packageFragmentRoot.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT
                && !packageFragmentRoot.isArchive()
                && projectSrcFolderName.equals(packageFragmentRoot.getPath().toString())) {

                srcRoot = packageFragmentRoot;
            }
        }

        return srcRoot;
    }

    public static IEditorPart openJavaFileEditor(final IJavaProject aJavaProject, final String aSrcFolder,
                                                 final String aPackageName, final String aFileName)
            throws JavaModelException, PartInitException {
        IPackageFragmentRoot locateFragmentRoot;
        locateFragmentRoot = JDTUtil.locateFragmentRoot(aJavaProject, aSrcFolder);

        ICompilationUnit compilationUnit;
        compilationUnit = locateFragmentRoot.getPackageFragment(aPackageName).getCompilationUnit(aFileName);

        IEditorPart javaEditor;
        javaEditor = JavaUI.openInEditor(compilationUnit, true, true);

        return javaEditor;
    }

    public static void openEditorAtLine(final ITextEditor anEditor, final int aLineNumber) throws BadLocationException {
        IDocumentProvider documentProvider;
        documentProvider = anEditor.getDocumentProvider();

        IDocument document;
        document = documentProvider.getDocument(anEditor.getEditorInput());

        int start = document.getLineOffset(aLineNumber - 1);
        anEditor.selectAndReveal(start, 0);

        IWorkbenchPage page = anEditor.getSite().getPage();
        page.activate(anEditor);
    }

    public static IEditorPart openManifestEditor(final IJavaProject aJavaProject) throws PartInitException {
        Preconditions.checkNotNull(aJavaProject, "aJavaProject cannot be null");

        IFile manifest;
        manifest = BundleManifestUtils.locateManifest(aJavaProject, false);

        IEditorPart editorPart;
        editorPart = openEditor(VIRGO_MANIFEST_EDITOR_ID, manifest);

        return editorPart;
    }

    public static IEditorPart openDefaultEditor(final IFile aFile) throws PartInitException {
        Preconditions.checkNotNull(aFile, "aFile cannot be null");

        IWorkbench workbench;
        workbench = PlatformUI.getWorkbench();

        IEditorDescriptor editorDescriptor;
        editorDescriptor = workbench.getEditorRegistry().getDefaultEditor(aFile.getName());

        IWorkbenchPage page;
        page = workbench.getActiveWorkbenchWindow().getActivePage();

        IEditorPart editorPart;
        editorPart = page.openEditor(new FileEditorInput(aFile), editorDescriptor.getId());

        return editorPart;
    }

    public static IEditorPart openEditor(final String anEditorId, final IFile aFile) throws PartInitException {
        Preconditions.checkNotNull(aFile, "aFile cannot be null");
        Preconditions.checkNotNull(anEditorId, "anEditorId cannot be null");

        IEditorPart editorPart;
        editorPart = null;

        IWorkbench workbench;
        workbench = PlatformUI.getWorkbench();

        IEditorDescriptor editorDescriptor;
        editorDescriptor = workbench.getEditorRegistry().findEditor(anEditorId);

        if (editorDescriptor != null) {
            IWorkbenchPage page;
            page = workbench.getActiveWorkbenchWindow().getActivePage();

            editorPart = page.openEditor(new FileEditorInput(aFile), editorDescriptor.getId());

        }

        return editorPart;
    }

    public static String toPath(final String aSrcFolder, final String aBasePackage) {
        Preconditions.checkNotNull(aSrcFolder, "aSrcFolder can be empty but not null");
        Preconditions.checkNotNull(aBasePackage, "aBasePackage can be empty but not null");

        String path;
        path = Joiner.on(IPath.SEPARATOR).join(aSrcFolder, aBasePackage.replace('.', IPath.SEPARATOR));

        return path;
    }
}
