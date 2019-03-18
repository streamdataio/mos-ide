package com.motwin.ide.server.connectors.ui.internal.wizard;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.motwin.ide.server.connectors.ui.internal.wizard.pages.ConnectorImportPage;
import com.motwin.ide.server.connectors.ui.internal.wizard.pages.ProjectChooserPage;
import com.motwin.ide.ui.Images;

public class ConnectorImportWizard extends Wizard implements IImportWizard {

    private IWorkbench           workbench;
    private IStructuredSelection selection;
    private ProjectChooserPage   projectChooserPage;
    // private RuntimeServerWizardPage runtimeChooserPage;
    private ConnectorImportPage  connectorListPage;

    public ConnectorImportWizard() {
        setWindowTitle("Motwin Streamdata Connector");
        setDefaultPageImageDescriptor(Images.motwinMedium);
    }

    @Override
    public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
        workbench = checkNotNull(aWorkbench);
        selection = checkNotNull(aSelection);
    }

    @Override
    public void addPages() {
        IProject defaultSelection = null;
        if (selection != null && selection.getFirstElement() instanceof IJavaProject) {
            defaultSelection = ((IJavaProject) selection.getFirstElement()).getProject();
        }

        projectChooserPage = new ProjectChooserPage(defaultSelection);
        connectorListPage = new ConnectorImportPage(projectChooserPage, workbench);

        addPage(projectChooserPage);
        addPage(connectorListPage);

    }

    @Override
    public boolean canFinish() {
        return false;
    }

    @Override
    public boolean performFinish() {
        return false;
    }

}
