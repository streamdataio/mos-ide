/**
 * 
 */
package com.motwin.ide.server.ui.wizards.page;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.pde.internal.core.util.IdUtil;
import org.eclipse.pde.internal.core.util.VersionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.motwin.ide.server.ui.internal.ServerMessages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou
 */
@SuppressWarnings("restriction")
public class BundleInformationWizardPage extends RuntimeServerWizardPage {

	private final static String DEFAULT_BUNDLE_VERSION = "0.0.1.BUILD-SNAPSHOT";
	
	private final AbstractJavaProjectPage projectPage;

	private Text bundleSymbolicNameField;
	private Text bundleNameField;
	private Text bundleVersionField;

	private boolean initialized = false;

	/**
	 * @param aPageName
	 */
	public BundleInformationWizardPage(AbstractJavaProjectPage aJavaProjectPage) {
		super(ServerMessages.wizardPlatformProjectBundleInformationPage);
		setTitle(ServerMessages.wizardPlatformProjectBundleInformationPage);
		setDescription(ServerMessages.wizardPlatformProjectBundleInformationDescription);
		setImageDescriptor(Images.motwinMedium);
		projectPage = checkNotNull(aJavaProjectPage);
	}

	@Override
	public void createControl(Composite aParent) {
		Composite container = new Composite(aParent, SWT.NULL);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createBundleDescriptionComposite(container);
		createServerTargetComposite(container);

		setControl(container);
		setPageComplete(false);
	}

	/**
	 * @param aParent
	 */
	private void createBundleDescriptionComposite(Composite aParent) {
		Group bundleGroup = new Group(aParent, SWT.NULL);
		bundleGroup.setText(ServerMessages.wizardPlatformProjectBundleProperties);
		bundleGroup.setLayout(new GridLayout(2, false));
		bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleSymbolicNameLabel = new Label(bundleGroup, SWT.NULL);
		bundleSymbolicNameLabel.setText(ServerMessages.wizardPlatformProjectBundleSymbolicName);

		bundleSymbolicNameField = new Text(bundleGroup, SWT.BORDER);
		bundleSymbolicNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bundleSymbolicNameField.addModifyListener(checkPageCompleteListener);

		Label bundleNameLabel = new Label(bundleGroup, SWT.NULL);
		bundleNameLabel.setText(ServerMessages.wizardPlatformProjectBundleName);

		bundleNameField = new Text(bundleGroup, SWT.BORDER);
		bundleNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bundleNameField.addModifyListener(checkPageCompleteListener);

		Label bundleVersionLabel = new Label(bundleGroup, SWT.NULL);
		bundleVersionLabel.setText(ServerMessages.wizardPlatformProjectBundleVersion);

		bundleVersionField = new Text(bundleGroup, SWT.BORDER);
		bundleVersionField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bundleVersionField.addModifyListener(checkPageCompleteListener);

	}

	private void setDefaultBundleInformation() {
		String projectName = projectPage.getProjectName();
		String bundleId = IdUtil.getValidId(projectName);
		bundleSymbolicNameField.setText(bundleId);
		bundleNameField.setText(bundleId);
		bundleVersionField.setText(DEFAULT_BUNDLE_VERSION);
	}

	@Override
	public void setVisible(boolean aVisible) {
		super.setVisible(aVisible);
		if(aVisible && !initialized) {
			setDefaultBundleInformation();
			initialized = true;
		}
	}

	public String getBundleName() {
		return bundleNameField.getText();
	}

	public String getBundleVersion() {
		return bundleVersionField.getText();
	}

	public String getBundleSymbolicName() {
		return bundleSymbolicNameField.getText();
	}

	@Override
	protected boolean validate() {
		// check bundle name not empty
		if(getBundleName() == null || getBundleName().isEmpty()) {
			setErrorMessage("Bundle Name: Bundle name cannot be empty.");
			return false;
		}

		// check bundle version not empty
		if(getBundleVersion() == null || getBundleVersion().isEmpty()) {
			setErrorMessage("Bundle Version: cannot be empty.");
			return false;
		}

		// check bundle symbolic name not empty
		if(getBundleSymbolicName() == null || getBundleSymbolicName().isEmpty()) {
			setErrorMessage("Bundle Symbolic Name: cannot be empty.");
			return false;
		}

		// check runtime name not empty
		if(getRuntimeName() == null) {
			setErrorMessage("Target Server: Select or create a target server runtime to continue.");
			return false;
		}

		// check bundle format
		if(!IdUtil.isValidCompositeID3_0(getBundleSymbolicName())) {
			setErrorMessage("Bundle Symbolic Name: Cannot contain ilegal characters.");
			return false;
		}

		// check version format
		if(VersionUtil.validateVersion(getBundleVersion()).getSeverity() != IStatus.OK) {
			setErrorMessage("Bundle Version: The specified version does not have the correct format (major.minor.micro.qualifier) or contains invalid characters.");
			return false;
		}

		return super.validate();
	}

}
