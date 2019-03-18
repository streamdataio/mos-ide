/**
 * 
 */
package com.motwin.ide.android.ui.wizards.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Preconditions;
import com.motwin.ide.android.ui.internal.AndroidMessages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou FacetCorePlugin.BUNDLE_FACET_ID;
 */
public class AndroidInformationWizardPage extends ServerInformationWizardPage {

    private final static String             DEFAULT_VERSION_CODE = "1";
    private final static String             DEFAULT_VERSION      = "0.0.1-SNAPSHOT";
    private final static String             DEFAULT_APP_NAME     = "com.example.serverAppName";
    private final static String             DEFAULT_APP_VERSION  = "0.0.1";

    private final static String             REGEX_VERSION_CODE   = "^[0-9]+$";
    private final static String             REGEX_VERSION        = "^([0-9\\.]+)(-[A-Z-]+)$";
    private final static String             REGEX_PACKAGE_NAME   = "^([a-z_]{1}[a-zA-Z0-9_]*(\\.[a-z_]{1}[a-zA-Z0-9_]*)*)$";

    protected final AbstractJavaProjectPage projectPage;

    private Text                            projectVersionField;
    private Text                            projectVersionCodeField;
    private Text                            projectPackageName;

    private Text                            serverAppNameField;
    private Text                            serverAppVersionField;

    /**
     * Default constructor
     * 
     * @param aJavaProjectPage
     */
    public AndroidInformationWizardPage(final AbstractJavaProjectPage aJavaProjectPage) {
        super(AndroidMessages.wizardAndroidProjectInformationPage);
        setTitle(AndroidMessages.wizardAndroidProjectInformationPage);
        setDescription(AndroidMessages.wizardAndroidProjectInformationDescription);
        setImageDescriptor(Images.motwinMedium);
        projectPage = Preconditions.checkNotNull(aJavaProjectPage);
    }

    @Override
    public void createControl(final Composite aParent) {
        Composite container = new Composite(aParent, SWT.NULL);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createProjectDescriptionComposite(container);
        createServerDescriptionComposite(container);
        createUserInfoDescriptionComposite(container);

        setControl(container);
        setPageComplete(false);
    }

    /**
     * @param aParent
     */
    private void createProjectDescriptionComposite(final Composite aParent) {
        Group bundleGroup = new Group(aParent, SWT.NULL);
        bundleGroup.setText("Manifest Properties");
        bundleGroup.setLayout(new GridLayout(2, false));
        bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label projectVersionLabel = new Label(bundleGroup, SWT.NULL);
        projectVersionLabel.setText("Project Version:");

        projectVersionField = new Text(bundleGroup, SWT.BORDER);
        projectVersionField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        projectVersionField.addModifyListener(checkPageCompleteListener);

        Label projectVersionCodeLabel = new Label(bundleGroup, SWT.NULL);
        projectVersionCodeLabel.setText("Project Version Code:");

        projectVersionCodeField = new Text(bundleGroup, SWT.BORDER);
        projectVersionCodeField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        projectVersionCodeField.addModifyListener(checkPageCompleteListener);

        Label projectPackageLabel = new Label(bundleGroup, SWT.NULL);
        projectPackageLabel.setText("Project Package:");

        projectPackageName = new Text(bundleGroup, SWT.BORDER);
        projectPackageName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        projectPackageName.addModifyListener(checkPageCompleteListener);

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
        String projectName = projectPage.getProjectName();
        projectVersionCodeField.setText(DEFAULT_VERSION_CODE);
        projectVersionField.setText(DEFAULT_VERSION);
        projectPackageName.setText("com.example." + projectName.toLowerCase());
        serverAppNameField.setText(DEFAULT_APP_NAME);
        serverAppVersionField.setText(DEFAULT_APP_VERSION);
    }

    /**
     * @return the projectName
     */
    public String getProjectVersionCode() {
        return projectVersionCodeField.getText();
    }

    /**
     * @return the projectVersion
     */
    public String getProjectVersion() {
        return projectVersionField.getText();
    }

    /**
     * @return the projectPackageName
     */
    public String getProjectPackage() {
        return projectPackageName.getText();
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

        // check project version not empty
        if (getProjectVersion() == null || getProjectVersion().isEmpty()) {
            setErrorMessage("Project Version: Cannot be empty.");
            return false;
        }

        // check project version format
        if (!getProjectVersion().matches(REGEX_VERSION)) {
            setErrorMessage("Project Version: Format is not valid! '0-9', '.', '-' and 'A-Z' are allowed.");
            return false;
        }

        // check project version code not empty
        if (getProjectVersionCode() == null || getProjectVersionCode().isEmpty()) {
            setErrorMessage("Project Version Code: Cannot be empty.");
            return false;
        }

        // check project version code format
        if (!getProjectVersionCode().matches(REGEX_VERSION_CODE)) {
            setErrorMessage("Project Version Code: Must be a number.");
            return false;
        }

        // check project package not empty
        if (getProjectPackage() == null || getProjectPackage().isEmpty()) {
            setErrorMessage("Project Package: Cannot be empty.");
            return false;
        }

        // check project package format
        if (!getProjectPackage().matches(REGEX_PACKAGE_NAME)) {
            setErrorMessage("Project Package: Format is not valid!");
            return false;
        }

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
