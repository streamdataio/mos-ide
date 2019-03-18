/**
 * 
 */
package com.motwin.ide.html5.ui.exports.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractWizardPage;

/**
 * @author fbou;
 */
public class AndroidInformationWizardPage extends AbstractWizardPage {

    private final static String DEFAULT_PROJECT_NAME = "AndroidProject";
    private final static String DEFAULT_PACKAGE_NAME = "com.example.project";
    private final static String DEFAULT_VERSION_CODE = "1";
    private final static String DEFAULT_VERSION      = "0.0.1-SNAPSHOT";

    private final static String REGEX_PROJECT_NAME   = "^[a-zA-Z0-9_]*$";
    private final static String REGEX_VERSION_CODE   = "^[0-9]+$";
    private final static String REGEX_VERSION        = "^([0-9\\.]+)(-[A-Z-]+)$";
    private final static String REGEX_PACKAGE_NAME   = "^([a-z_]{1}[a-zA-Z0-9_]*(\\.[a-z_]{1}[a-zA-Z0-9_]*)*)$";

    private Text                projectNameField;
    private Text                projectVersionField;
    private Text                projectVersionCodeField;
    private Text                projectPackageField;

    /**
     * Default constructor
     * 
     * @param aJavaProjectPage
     */
    public AndroidInformationWizardPage() {
        super("Android Information");
        setTitle("Android Information");
        setDescription("Enter Android Information required to generated the exported project.");
        setImageDescriptor(Images.motwinMedium);
    }

    @Override
    public void createControl(final Composite aParent) {
        Composite container = new Composite(aParent, SWT.NULL);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createProjectDescriptionComposite(container);

        setControl(container);
    }

    /**
     * @param aParent
     */
    private void createProjectDescriptionComposite(final Composite aParent) {
        Group bundleGroup = new Group(aParent, SWT.NULL);
        bundleGroup.setText("Android Properties");
        bundleGroup.setLayout(new GridLayout(2, false));
        bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label projectNameLabel = new Label(bundleGroup, SWT.NULL);
        projectNameLabel.setText("Project Name:");

        projectNameField = new Text(bundleGroup, SWT.BORDER);
        projectNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        projectNameField.addModifyListener(checkPageCompleteListener);

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

        projectPackageField = new Text(bundleGroup, SWT.BORDER);
        projectPackageField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        projectPackageField.addModifyListener(checkPageCompleteListener);

        // default values
        projectNameField.setText(DEFAULT_PROJECT_NAME);
        projectVersionCodeField.setText(DEFAULT_VERSION_CODE);
        projectVersionField.setText(DEFAULT_VERSION);
        projectPackageField.setText(DEFAULT_PACKAGE_NAME);
        checkPageComplete();

    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectNameField.getText();
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
     * @return the projectPackageField
     */
    public String getProjectPackage() {
        return projectPackageField.getText();
    }

    @Override
    protected boolean validate() {

        // check project version not empty
        if (getProjectName() == null || getProjectName().isEmpty()) {
            setErrorMessage("Project Name: Cannot be empty.");
            return false;
        }

        // check project version format
        if (!getProjectName().matches(REGEX_PROJECT_NAME)) {
            setErrorMessage("Project Name: Format is not valid! '0-9', '_' and 'A-Z' are allowed.");
            return false;
        }

        // check project version not empty
        if (getProjectVersion() == null || getProjectVersion().isEmpty()) {
            setErrorMessage("Project Version: Cannot be empty.");
            return false;
        }

        // check project version format
        if (!getProjectVersion().matches(REGEX_VERSION)) {
            setErrorMessage("Project Version: Format is not valid! ex: '1.0-RELEASE'.");
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

        return super.validate();
    }

}