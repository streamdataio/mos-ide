/**
 * 
 */
package com.motwin.ide.html5.ui.exports;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.core.templates.TemplateProcessor;
import com.motwin.ide.core.utils.CoreUtil;
import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.html5.ui.MotwinHTML5UIPlugin;
import com.motwin.ide.html5.ui.exports.pages.ReadmeDialog;
import com.motwin.ide.html5.ui.exports.pages.SourceSelectionWizardPage;
import com.motwin.ide.html5.ui.exports.pages.TargetSelectionWizardPage;

/**
 * @author fbou
 * 
 */
public abstract class AbstractExportWizard extends Wizard implements IExportWizard {

    private final SourceSelectionWizardPage sourceSelectionPage = new SourceSelectionWizardPage();
    private final TargetSelectionWizardPage targetSelectionPage = new TargetSelectionWizardPage();

    protected abstract String getTemplateBundleId();

    protected abstract String getTemplateLocation();

    protected abstract String getTemplateWebContentPath();

    protected abstract Map<String, String> getInputData();

    protected abstract WizardPage getLastPage();

    protected abstract String getReadmeFilePath();

    public AbstractExportWizard() {
        setWindowTitle(HTML5Messages.wizardHTML5ExportWindowTitle);
    }

    @Override
    public void addPages() {
        addPage(sourceSelectionPage);
        addPage(targetSelectionPage);
    }

    @Override
    public void init(final IWorkbench aWorkbench, final IStructuredSelection aStructuredSelection) {
        Preconditions.checkNotNull(aStructuredSelection);

        Object selected = aStructuredSelection.getFirstElement();

        IProject selectedProject = null;
        // lookup for IProject in selection
        if (selected instanceof IProject) {
            selectedProject = (IProject) selected;
        } else if (selected instanceof IAdaptable) {
            IResource r = (IResource) ((IAdaptable) selected).getAdapter(IResource.class);
            if (r != null) {
                selectedProject = r.getProject();
            }
        }
        // set selected project if a project is selected
        if (selectedProject != null) {
            sourceSelectionPage.setInitialProject(selectedProject);
        }
    }

    @Override
    public boolean canFinish() {
        IWizardPage page = getContainer().getCurrentPage();
        return super.canFinish() && page == getLastPage();
    }

    @Override
    public boolean performFinish() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

        try {
            final Map<String, String> inputData = getInputData();

            File exportDirectory;
            exportDirectory = new File(targetSelectionPage.getExportFolder());

            // locate template files
            File templateDirectory = CoreUtil.locateResource(getTemplateBundleId(), getTemplateLocation());
            Preconditions.checkNotNull(templateDirectory, "Project template not found at '%s'.", getTemplateLocation());

            // process the template files
            exportDirectory.mkdirs();
            TemplateProcessor processor = new TemplateProcessor(inputData);
            processor.process(templateDirectory, exportDirectory);

            // copy the www folder from selected project
            File sourceWebContentDirectory, targetWebContentDirectory;
            sourceWebContentDirectory = new File(sourceSelectionPage.getSelectedFolder().getLocationURI());
            targetWebContentDirectory = new File(exportDirectory + File.separator + getTemplateWebContentPath());
            FileUtils.copyDirectory(sourceWebContentDirectory, targetWebContentDirectory);

            ReadmeDialog readmeDialog;
            readmeDialog = new ReadmeDialog(window.getShell(), "Project exported", "Project successfully exported to '"
                + exportDirectory + ".'", "file:" + exportDirectory + File.separator + getReadmeFilePath());
            readmeDialog.open();

            return true;

        } catch (Exception ex) {
            StatusHandler.log(new Status(IStatus.ERROR, MotwinHTML5UIPlugin.PLUGIN_ID, "Unable to export project", ex));
            MessageDialog.openError(window.getShell(), "Unable to export project",
                    Strings.isNullOrEmpty(ex.getMessage()) ? "Unable to export project." : ex.getMessage());
            return false;

        }
    }
}