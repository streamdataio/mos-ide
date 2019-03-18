/**
 * 
 */
package com.motwin.ide.html5.ui.exports.pages;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractWizardPage;

/**
 * @author fbou
 */
public class SourceSelectionWizardPage extends AbstractWizardPage {

    private Text      resourceSelectionText;
    private IResource selectedResourceFolder;

    /**
     * Default constructor
     * 
     * @param aJavaProjectPage
     */
    public SourceSelectionWizardPage() {
        super(HTML5Messages.wizardHTML5ExportSourcePageTitle);
        setTitle(HTML5Messages.wizardHTML5ExportSourcePageTitle);
        setDescription(HTML5Messages.wizardHTML5ExportSourcePageDescription);
        setImageDescriptor(Images.motwinMedium);
    }

    @Override
    public void createControl(final Composite aParent) {
        final Composite container = new Composite(aParent, SWT.NONE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(3, false));

        Label projectNameLabel = new Label(container, SWT.NONE);
        projectNameLabel.setText("Web Content:");

        resourceSelectionText = new Text(container, SWT.BORDER);
        resourceSelectionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        resourceSelectionText.setEditable(false);
        if (selectedResourceFolder != null) {
            resourceSelectionText.setText(selectedResourceFolder.getFullPath().toString());
        }
        resourceSelectionText.addModifyListener(checkPageCompleteListener);

        Button browseButton = new Button(container, SWT.PUSH);
        browseButton.setText("Browse...");
        browseButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ElementTreeSelectionDialog dialog;

                dialog = new ElementTreeSelectionDialog(aParent.getShell(), new WorkbenchLabelProvider(),
                        new BaseWorkbenchContentProvider());
                dialog.setTitle("Web Content");
                dialog.setMessage("Select the Web Content to export:");
                dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
                dialog.setInitialSelection(selectedResourceFolder);

                int result;
                result = dialog.open();

                if (result == ElementTreeSelectionDialog.OK && dialog.getFirstResult() != null) {
                    if (dialog.getFirstResult() instanceof IFolder) {
                        selectedResourceFolder = (IFolder) dialog.getFirstResult();
                        resourceSelectionText.setText(selectedResourceFolder.getFullPath().toString());
                    }
                }
            }
        });

        setControl(container);
        checkPageComplete();
    }

    public void setInitialProject(IProject aProject) {
        if (aProject != null && aProject.getFolder("www").exists()) {
            selectedResourceFolder = aProject.getFolder("www");
        } else {
            selectedResourceFolder = aProject;
        }

    }

    public IFolder getSelectedFolder() {
        return selectedResourceFolder instanceof IFolder ? (IFolder) selectedResourceFolder : null;
    }

    @Override
    protected boolean validate() {
        if (selectedResourceFolder == null) {
            setErrorMessage("Browse and select the folder containing the web resources.");
            return false;
        }
        if (!(selectedResourceFolder instanceof IFolder)
            || !((IFolder) selectedResourceFolder).exists(new Path("index.html"))) {
            setErrorMessage("The web content folder must contain a index.html file.");
            return false;
        }
        return super.validate();
    }

}
