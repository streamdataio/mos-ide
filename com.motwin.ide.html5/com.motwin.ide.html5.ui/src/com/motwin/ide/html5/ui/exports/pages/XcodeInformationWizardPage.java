/**
 * 
 */
package com.motwin.ide.html5.ui.exports.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class XcodeInformationWizardPage extends AbstractWizardPage {

    private final static String DEFAULT_PROJECT_NAME = "CordovaProject";
    private final static String DEFAULT_PACKAGE_NAME = "com.example.project";

    private final static String REGEX_PROJECT_NAME   = "^[a-zA-Z0-9_]*$";
    private final static String REGEX_PACKAGE_NAME   = "^([a-z_]{1}[a-zA-Z0-9_]*(\\.[a-z_]{1}[a-zA-Z0-9_]*)*)$";

    private Text                projectNameField;
    private Text                projectPackageField;
    private Text                userNameField;
    private Text                organizationNameField;
    private Text                dateField;
    private Text                yearField;

    /**
     * Default constructor
     * 
     * @param aJavaProjectPage
     */
    public XcodeInformationWizardPage() {
        super("XCode Project Information");
        setTitle("XCode Project  Information");
        setDescription("Enter XCode Project Information required to generated the iOS project.");
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
        bundleGroup.setText("iOS Properties");
        bundleGroup.setLayout(new GridLayout(2, false));
        bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label projectNameLabel = new Label(bundleGroup, SWT.NULL);
        projectNameLabel.setText("Project Name:");

        projectNameField = new Text(bundleGroup, SWT.BORDER);
        projectNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        projectNameField.addModifyListener(checkPageCompleteListener);

        Label projectPackageLabel = new Label(bundleGroup, SWT.NULL);
        projectPackageLabel.setText("Project Package:");

        projectPackageField = new Text(bundleGroup, SWT.BORDER);
        projectPackageField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        projectPackageField.addModifyListener(checkPageCompleteListener);

        Label userNameLabel = new Label(bundleGroup, SWT.NULL);
        userNameLabel.setText("User Name:");

        userNameField = new Text(bundleGroup, SWT.BORDER);
        userNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        userNameField.addModifyListener(checkPageCompleteListener);

        Label organizationNameLabel = new Label(bundleGroup, SWT.NULL);
        organizationNameLabel.setText("Organization Name:");

        organizationNameField = new Text(bundleGroup, SWT.BORDER);
        organizationNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        organizationNameField.addModifyListener(checkPageCompleteListener);

        Label dateLabel = new Label(bundleGroup, SWT.NULL);
        dateLabel.setText("Date:");

        dateField = new Text(bundleGroup, SWT.BORDER);
        dateField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        dateField.addModifyListener(checkPageCompleteListener);

        Label yearLabel = new Label(bundleGroup, SWT.NULL);
        yearLabel.setText("Year:");

        yearField = new Text(bundleGroup, SWT.BORDER);
        yearField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        yearField.addModifyListener(checkPageCompleteListener);

        // default values
        projectNameField.setText(DEFAULT_PROJECT_NAME);
        projectPackageField.setText(DEFAULT_PACKAGE_NAME);

        Date date = new Date();
        dateField.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date));
        yearField.setText((new SimpleDateFormat("yyyy")).format(date));

        String userName;
        try {
            userName = System.getProperty("user.name");
        } catch (Throwable t) {
            userName = "";
        }
        organizationNameField.setText(userName);
        userNameField.setText(userName);

        checkPageComplete();
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectNameField.getText();
    }

    /**
     * @return the projectPackageField
     */
    public String getProjectPackage() {
        return projectPackageField.getText();
    }

    /**
     * @return the userNameField
     */
    public String getUserName() {
        return userNameField.getText();
    }

    /**
     * @return the organizationNameField
     */
    public String getOrganizationName() {
        return organizationNameField.getText();
    }

    /**
     * @return the dateField
     */
    public String getDate() {
        return dateField.getText();
    }

    /**
     * @return the yearField
     */
    public String getYear() {
        return yearField.getText();
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