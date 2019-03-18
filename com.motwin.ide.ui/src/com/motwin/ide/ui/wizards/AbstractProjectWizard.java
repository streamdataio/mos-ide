/**
 * 
 */
package com.motwin.ide.ui.wizards;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jdt.internal.ui.wizards.NewElementWizard;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.part.FileEditorInput;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.core.templates.TemplateProcessor;
import com.motwin.ide.core.utils.CoreUtil;
import com.motwin.ide.ui.MotwinUIPlugin;

/**
 * @author fbou
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractProjectWizard extends NewElementWizard implements INewWizard {

    private IProject project;

    public AbstractProjectWizard() {
        super();
    }

    protected abstract String getTemplateBundleId(); // com.motwin.ide.templates

    protected abstract String getTemplateLocation();

    protected abstract String getWelcomeFileLocation();

    protected abstract String getWelcomeFileEditor();

    protected abstract String getWelcomePerspective();

    protected abstract String getProjectName();

    protected abstract String getProjectLocation();

    protected abstract IWorkingSet[] getProjectWorkingSets();

    protected abstract IWizardPage getLastPage();

    protected abstract Map<String, String> getInputData();

    @Override
    public boolean canFinish() {
        IWizardPage page = getContainer().getCurrentPage();
        return super.canFinish() && page == getLastPage();
    }

    @Override
    public boolean performFinish() {
        try {
            final Map<String, String> inputData = getInputData();

            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IProjectDescription projectDescription = workspace.newProjectDescription(getProjectName());
            File projectDirectory;

            if (getProjectLocation() == null) {
                projectDescription.setLocation(null);
                projectDirectory = new File(workspace.getRoot().getLocationURI().getPath() + File.separator
                    + getProjectName());

            } else {
                projectDescription.setLocation(Path.fromOSString(getProjectLocation()));
                projectDirectory = new File(getProjectLocation());

            }

            // locate template files
            File templateDirectory = CoreUtil.locateResource(getTemplateBundleId(), getTemplateLocation());
            Preconditions.checkNotNull(templateDirectory, "Project template not found at '%s'.", getTemplateLocation());

            // process the template files
            projectDirectory.mkdirs();
            TemplateProcessor processor = new TemplateProcessor(inputData);
            processor.process(templateDirectory, projectDirectory);

            // opening project
            project = workspace.getRoot().getProject(getProjectName());
            project.create(projectDescription, new NullProgressMonitor());
            project.open(new NullProgressMonitor());

            // add to workspaces
            IWorkingSet[] workingSets = getProjectWorkingSets();
            if (workingSets.length > 0) {
                getWorkbench().getWorkingSetManager().addToWorkingSets(project, workingSets);
            }

            // show the project
            selectAndReveal(project.getProject());

            try {
                // try to show String perspective
                IWorkbenchWindow activeWindow = getWorkbench().getActiveWorkbenchWindow();
                activeWindow.getWorkbench().showPerspective(getWelcomePerspective(), activeWindow);
            } catch (WorkbenchException e) {
                MessageDialog.openError(getWorkbench().getActiveWorkbenchWindow().getShell(),
                        "Error opening perspective", e.getMessage());
            }

            if (getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
                // show project in explorer
                IWorkbenchPart activePart = getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
                if (activePart instanceof PackageExplorerPart) {
                    ((PackageExplorerPart) activePart).tryToReveal(project);
                }
                // show welcome file editor
                IFile welcomeFile = (IFile) project.getProject().findMember(getWelcomeFileLocation());
                try {
                    getWorkbench().getActiveWorkbenchWindow().getActivePage()
                            .openEditor(new FileEditorInput(welcomeFile), getWelcomeFileEditor());
                } catch (PartInitException e) {
                    MessageDialog.openError(getWorkbench().getActiveWorkbenchWindow().getShell(),
                            "Error opening editor", e.getMessage());
                }
            }

            /*
             * Bug #9294: the Virgo Manifest Container takes time to gather its
             * classpath entries ; hence sometimes the postprocess method (which
             * right now add javadoc as sources) got an empty classpath
             * container. Calling the method here ensure to give a little bit
             * more time to the classpath container to be fed with its classpath
             * entries along with a job that starts ~ 10s later on... (see
             * sub-classes)
             */
            postProcess();
            return project != null;

        } catch (Exception ex) {
            StatusHandler.log(new Status(IStatus.ERROR, MotwinUIPlugin.PLUGIN_ID, "Unable to create project ["
                + project + "]", ex));
            IWorkbenchWindow workbenchWindow = getWorkbench().getActiveWorkbenchWindow();
            MessageDialog.openError(workbenchWindow.getShell(), "Unable to create project",
                    Strings.isNullOrEmpty(ex.getMessage()) ? "Unable to create project." : ex.getMessage());
            return false;

        }
    }

    protected void postProcess() {
    }

    @Override
    protected void finishPage(final IProgressMonitor aIProgressMonitor) throws InterruptedException, CoreException {
    }

    @Override
    public IJavaElement getCreatedElement() {
        return JavaCore.create(project);
    }

}