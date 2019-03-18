/**
 * 
 */
package com.motwin.ide.html5.ui.exports.pages;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractWizardPage;

/**
 * @author fbou
 */
public class TargetSelectionWizardPage extends AbstractWizardPage {

    private Text exportFolderText;

    /**
     * Default constructor
     * 
     * @param aJavaProjectPage
     */
    public TargetSelectionWizardPage() {
        super(HTML5Messages.wizardHTML5ExportTargetPageTitle);
        setTitle(HTML5Messages.wizardHTML5ExportTargetPageTitle);
        setDescription(HTML5Messages.wizardHTML5ExportTargetPageDescription);
        setImageDescriptor(Images.motwinMedium);
    }

    @Override
    public void createControl(final Composite aParent) {
        final Composite container = new Composite(aParent, SWT.NONE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(3, false));

        Label exportFolderLabel = new Label(container, SWT.NONE);
        exportFolderLabel.setText("Export to:");

        exportFolderText = new Text(container, SWT.BORDER);
        exportFolderText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        exportFolderText.addModifyListener(checkPageCompleteListener);

        Button browseExportFolderButton = new Button(container, SWT.PUSH);
        browseExportFolderButton.setText("Browse...");
        browseExportFolderButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                DirectoryDialog dialog;
                String directoryPath;

                dialog = new DirectoryDialog(aParent.getShell());
                directoryPath = dialog.open();

                if (directoryPath != null) {
                    exportFolderText.setText(directoryPath);
                }
            }
        });

        setControl(container);

    }

    public String getExportFolder() {
        return exportFolderText.getText();
    }

    @Override
    protected boolean validate() {
        if (exportFolderText.getText() == null) {
            setErrorMessage("Browse and select the folder where the cordova project will be exported.");
            return false;
        }
        File exportDirectory = new File(exportFolderText.getText());
        if (exportDirectory == null || !exportDirectory.exists()) {
            setErrorMessage("The folder where the cordova project will be exported must exist.");
            return false;
        }
        if (exportDirectory.listFiles().length > 0) {
            setErrorMessage("The folder where the cordova project will be exported must be empty.");
            return false;
        }

        return super.validate();
    }

}
