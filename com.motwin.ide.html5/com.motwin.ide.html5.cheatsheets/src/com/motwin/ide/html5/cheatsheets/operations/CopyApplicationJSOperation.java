/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.operations;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.wst.jsdt.core.IBuffer;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJavaScriptUnit;
import org.eclipse.wst.jsdt.core.IPackageFragmentRoot;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.dom.AST;
import org.eclipse.wst.jsdt.core.dom.ASTNode;
import org.eclipse.wst.jsdt.core.dom.ASTParser;
import org.eclipse.wst.jsdt.core.dom.ASTVisitor;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;
import org.eclipse.wst.jsdt.core.dom.VariableDeclarationStatement;
import org.eclipse.wst.jsdt.core.dom.rewrite.ASTRewrite;

import com.google.common.base.Preconditions;
import com.motwin.ide.cheatsheets.operations.OpenJSFileOperation;

/**
 * @author ctranxuan
 * 
 */
public final class CopyApplicationJSOperation {
    private static final String      CREATE_CLIENT_CHANNEL_INVOCATION = "motwin.createClientChannel";

    private final String             fileName;
    private final IJavaScriptProject jsProject;
    private final boolean            openJSEditor;
    private final String             pluginID;
    private final String             sampleCodePath;
    private final String             wwwFolderPath;

    public final static class OperationBuilder {
        private String             fileName;
        private IJavaScriptProject jsProject;
        private boolean            openJSEditor;
        private String             pluginID;
        private String             sampleCodePath;
        private String             wwwFolderPath;

        public OperationBuilder() {
            openJSEditor = false;
        }

        public OperationBuilder fileName(final String aFileName) {
            fileName = aFileName;
            return this;
        }

        public OperationBuilder jsProject(final IJavaScriptProject aJsProject) {
            jsProject = aJsProject;
            return this;
        }

        public OperationBuilder openJSEditor(final boolean aDoOpenJSEditor) {
            openJSEditor = aDoOpenJSEditor;
            return this;
        }

        public OperationBuilder pluginID(final String aPluginID) {
            pluginID = aPluginID;
            return this;
        }

        public OperationBuilder sampleCodePath(final String aSampleCodePath) {
            sampleCodePath = aSampleCodePath;
            return this;
        }

        public OperationBuilder wwwFolderPath(final String aWWWFolderPath) {
            wwwFolderPath = aWWWFolderPath;
            return this;
        }

        public CopyApplicationJSOperation build() {
            Preconditions.checkState(fileName != null, "fileName cannot be null");
            Preconditions.checkState(jsProject != null, "jsProject cannot be null");
            Preconditions.checkState(pluginID != null, "pluginID cannot be null");
            Preconditions.checkState(sampleCodePath != null, "sampleCodePath cannot be null");
            Preconditions.checkState(wwwFolderPath != null, "wwwFolderPath cannot be null");

            return new CopyApplicationJSOperation(this);
        }
    }

    class ChannelDeclarationVisitor extends ASTVisitor {
        private String declaration;

        @Override
        public boolean visit(final VariableDeclarationStatement aNode) {
            if (aNode.toString().contains(CREATE_CLIENT_CHANNEL_INVOCATION)) {
                declaration = aNode.toString();
            }
            return false;
        }

        public String getDeclaration() {
            return declaration;
        }
    }

    private CopyApplicationJSOperation(final OperationBuilder aBuilder) {
        fileName = aBuilder.fileName;
        jsProject = aBuilder.jsProject;
        openJSEditor = aBuilder.openJSEditor;
        pluginID = aBuilder.pluginID;
        sampleCodePath = aBuilder.sampleCodePath;
        wwwFolderPath = aBuilder.wwwFolderPath;
    }

    public void run() {
        String channelDeclaration;
        channelDeclaration = extractChannelDeclaration(jsProject);

        com.motwin.ide.cheatsheets.operations.CopyFileOperation.OperationBuilder builder;
        builder = new com.motwin.ide.cheatsheets.operations.CopyFileOperation.OperationBuilder();

        IProject project;
        project = jsProject.getProject();

        // @formatter:off
        builder.fileName(fileName)
               .rootPath("")
               .folderPath(wwwFolderPath)
               .sampleCodePath(sampleCodePath)
               .openEditorID(null)
               .pluginID(pluginID)
               .project(project)
               .update(true)
               .build()
               .run();
        // @formatter:on

        replaceChannelDeclaration(jsProject, channelDeclaration);

        if (openJSEditor) {
            new OpenJSFileOperation(jsProject.getProject(), wwwFolderPath + IPath.SEPARATOR + fileName, pluginID).run();
        }
    }

    private void replaceChannelDeclaration(final IJavaScriptProject aJsProject, final String aChannelDeclaration) {
        try {
            IProject project;
            project = aJsProject.getProject();

            IFolder wwwFolder;
            wwwFolder = project.getFolder(wwwFolderPath);

            IPath wwwFullPath;
            wwwFullPath = wwwFolder.getFullPath();

            IPackageFragmentRoot wwwFragmentRoot;
            wwwFragmentRoot = aJsProject.findPackageFragmentRoot(wwwFullPath);

            IJavaScriptUnit javaScriptUnit;
            javaScriptUnit = wwwFragmentRoot.getPackageFragment("").getJavaScriptUnit(fileName);

            ASTParser parser;
            parser = ASTParser.newParser(AST.JLS3);
            parser.setSource(javaScriptUnit);

            JavaScriptUnit root;
            root = (JavaScriptUnit) parser.createAST(null);

            final AST ast;
            ast = root.getAST();

            final ASTRewrite rewrite;
            rewrite = ASTRewrite.create(ast);

            root.accept(new ASTVisitor() {

                @Override
                public boolean visit(final VariableDeclarationStatement aNode) {
                    if (aNode.toString().contains(CREATE_CLIENT_CHANNEL_INVOCATION)) {
                        ASTNode newNode = rewrite.createStringPlaceholder(aChannelDeclaration, aNode.getNodeType());

                        rewrite.replace(aNode, newNode, null);
                    }
                    return false;
                }

            });

            IBuffer buffer;
            buffer = javaScriptUnit.getBuffer();

            IDocument document;
            document = new Document(buffer.getContents());

            TextEdit edits;
            edits = rewrite.rewriteAST(document, javaScriptUnit.getJavaScriptProject().getOptions(true));
            edits.apply(document);

            buffer.setContents(document.get());
            javaScriptUnit.save(new NullProgressMonitor(), true);

        } catch (JavaScriptModelException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to replace the channel declaration ["
                        + aChannelDeclaration + "] for the file [" + fileName + "] of the project [name="
                        + aJsProject.getElementName() + "]: a JSDT error occurred", e));
        } catch (MalformedTreeException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to replace the channel declaration ["
                        + aChannelDeclaration + "] for the file [" + fileName + "] of the project [name="
                        + aJsProject.getElementName() + "]: the AST is malformed", e));
        } catch (BadLocationException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to update with the channel declaration ["
                        + aChannelDeclaration + "] the file [" + fileName + "] of the project [name="
                        + aJsProject.getElementName() + "]: bad location of the modifications", e));
        }

    }

    private String extractChannelDeclaration(final IJavaScriptProject aJsProject) {
        String result = null;

        try {
            IProject project;
            project = aJsProject.getProject();

            IPath wwwFullPath;
            wwwFullPath = project.getFolder(wwwFolderPath).getFullPath();

            IPackageFragmentRoot wwwFragmentRoot;
            wwwFragmentRoot = aJsProject.findPackageFragmentRoot(wwwFullPath);

            IJavaScriptUnit javaScriptUnit;
            javaScriptUnit = wwwFragmentRoot.getPackageFragment("").getJavaScriptUnit(fileName);

            ASTParser parser;
            parser = ASTParser.newParser(ASTParser.K_STATEMENTS);
            parser.setSource(javaScriptUnit);

            JavaScriptUnit root;
            root = (JavaScriptUnit) parser.createAST(null);

            ChannelDeclarationVisitor visitor;
            visitor = new ChannelDeclarationVisitor();

            root.accept(visitor);

            result = visitor.getDeclaration();

        } catch (JavaScriptModelException e) {
            StatusManager.getManager().handle(
                    new Status(IStatus.ERROR, pluginID, "Failed to extract the channel declaration for the file ["
                        + fileName + "] of the project [name=" + aJsProject.getElementName()
                        + "]: a JSDT error occurred", e));
        }

        return result;
    }
}
