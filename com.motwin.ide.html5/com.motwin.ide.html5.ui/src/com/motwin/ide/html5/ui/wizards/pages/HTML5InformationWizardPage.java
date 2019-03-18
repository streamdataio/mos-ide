/**
 * 
 */
package com.motwin.ide.html5.ui.wizards.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Preconditions;
import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou FacetCorePlugin.BUNDLE_FACET_ID;
 */
public class HTML5InformationWizardPage extends ServerInformationWizardPage {

    private final static String             DEFAULT_APP_NAME    = "com.example.serverAppName";
    private final static String             DEFAULT_APP_VERSION = "0.0.1";

    protected final AbstractJavaProjectPage projectPage;

    private Text                            serverAppNameField;
    private Text                            serverAppVersionField;

    /**
     * Default constructor
     * 
     * @param aJavaProjectPage
     */
    public HTML5InformationWizardPage(final AbstractJavaProjectPage aJavaProjectPage) {
        super(HTML5Messages.wizardHTML5ProjectInformationPage);
        setTitle(HTML5Messages.wizardHTML5ProjectInformationPage);
        setDescription(HTML5Messages.wizardHTML5ProjectInformationDescription);
        setImageDescriptor(Images.motwinMedium);
        projectPage = Preconditions.checkNotNull(aJavaProjectPage);
    }

    @Override
    public void createControl(final Composite aParent) {
        Composite container = new Composite(aParent, SWT.NULL);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createServerDescriptionComposite(container);
        createUserInfoDescriptionComposite(container);

        setControl(container);
        setPageComplete(false);
    }

    /**
     * @param aParent
     */
    private void createUserInfoDescriptionComposite(final Composite aParent) {
        Group bundleGroup = new Group(aParent, SWT.NULL);
        bundleGroup.setText("Server Mapping Rules");
        bundleGroup.setLayout(new GridLayout(2, false));
        bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label bundleNameLabel = new Label(bundleGroup, SWT.NULL);
        bundleNameLabel.setText("appName:");

        serverAppNameField = new Text(bundleGroup, SWT.BORDER);
        serverAppNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        serverAppNameField.addModifyListener(checkPageCompleteListener);

        Label bundleVersionLabel = new Label(bundleGroup, SWT.NULL);
        bundleVersionLabel.setText("appVersion:");

        serverAppVersionField = new Text(bundleGroup, SWT.BORDER);
        serverAppVersionField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        serverAppVersionField.addModifyListener(checkPageCompleteListener);

    }

    @Override
    protected void setDefaultBundleInformation() {
        super.setDefaultBundleInformation();
        serverAppNameField.setText(DEFAULT_APP_NAME);
        serverAppVersionField.setText(DEFAULT_APP_VERSION);
    }

    /**
     * @return the serverAppNameField
     */
    public String getServerAppName() {
        return serverAppNameField.getText();
    }

    /**
     * @return the serverAppVersionField
     */
    public String getServerAppVersion() {
        return serverAppVersionField.getText();
    }

    @Override
    protected boolean validate() {
        // check project appName not empty
        if (getServerAppName() == null || getServerAppName().isEmpty()) {
            setErrorMessage("appName: Cannot be empty.");
            return false;
        }

        // check project appVersion not empty
        if (getServerAppVersion() == null || getServerAppVersion().isEmpty()) {
            setErrorMessage("appVersion: Cannot be empty.");
            return false;
        }

        return super.validate();
    }

}
