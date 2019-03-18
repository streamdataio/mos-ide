package com.motwin.ide.server.ui.editor.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.wizards.NewElementWizard;
import org.eclipse.jdt.internal.ui.wizards.NewWizardMessages;
import org.springframework.ide.eclipse.config.ui.wizards.ExtendedNewClassWizardPage;

import com.google.common.collect.Lists;

//Copied from JDT's NewClassCreationWizard and PDE's JavaAttributeWizardPage
@SuppressWarnings("restriction")
public class MotwinExtendedNewClassCreationWizard extends NewElementWizard {
    //

    private ExtendedNewClassWizardPage mainPage;

    private final IProject             project;

    private final String               className;

    private final String               superInterface;

    private final boolean              openEditor;

    public MotwinExtendedNewClassCreationWizard(IProject project, String className, String aSuperInterface,
            boolean openEditorOnFinish) {
        super();
        this.project = project;
        this.className = className;
        this.openEditor = openEditorOnFinish;
        this.superInterface = aSuperInterface;

        setDefaultPageImageDescriptor(JavaPluginImages.DESC_WIZBAN_NEWCLASS);
        setDialogSettings(JavaPlugin.getDefault().getDialogSettings());
        setWindowTitle(NewWizardMessages.NewClassCreationWizard_title);
    }

    @Override
    public void addPages() {
        if (mainPage == null) {
            mainPage = new ExtendedNewClassWizardPage(project, className);
            mainPage.setSuperInterfaces(Lists.newArrayList(superInterface), true);
            mainPage.init();
        }
        addPage(mainPage);
    }

    @Override
    protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {
        mainPage.createType(monitor);
    }

    @Override
    public IJavaElement getCreatedElement() {
        return mainPage.getCreatedType();
    }

    public String getQualifiedName() {
        if (mainPage.getCreatedType() == null) {
            return null;
        }
        return mainPage.getCreatedType().getFullyQualifiedName('$');
    }

    @Override
    public boolean performFinish() {
        warnAboutTypeCommentDeprecation();
        boolean res = super.performFinish();
        if (res) {
            IResource resource = mainPage.getModifiedResource();
            if (resource != null) {
                selectAndReveal(resource);
                if (openEditor) {
                    openResource((IFile) resource);
                }
            }
        }
        return res;
    }

}
