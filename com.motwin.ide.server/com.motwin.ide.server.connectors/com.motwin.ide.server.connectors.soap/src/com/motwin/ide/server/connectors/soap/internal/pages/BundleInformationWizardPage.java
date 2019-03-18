/**
 * 
 */
package com.motwin.ide.server.connectors.soap.internal.pages;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.pde.internal.core.util.IdUtil;
import org.eclipse.pde.internal.core.util.VersionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou
 */
@SuppressWarnings("restriction")
public class BundleInformationWizardPage extends WizardPage {

	private final static String DEFAULT_BUNDLE_VERSION = "0.0.1.BUILD-SNAPSHOT";
	
	private final ModifyListener checkPageCompleteListener;
	private final AbstractJavaProjectPage projectPage;

	private Text bundleSymbolicNameField;
	private Text bundleNameField;
	private Text bundleVersionField;

	private boolean initialized = false;

	/**
	 * @param aPageName
	 */
	public BundleInformationWizardPage(AbstractJavaProjectPage aJavaProjectPage) {
		super("Generated Bundle Information");
		setTitle("Generated Bundle Information");
		setDescription("Enter information about the bundle that will be generated from the provided WSDL file.");
		setImageDescriptor(Images.motwinMedium);
		projectPage = checkNotNull(aJavaProjectPage);
		checkPageCompleteListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent aEvent) {
				checkPageComplete();
			}
		};
	}

	@Override
	public void createControl(Composite aParent) {
		Composite container = new Composite(aParent, SWT.NULL);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createBundleDescriptionComposite(container);

		setControl(container);
		setPageComplete(false);
		
	}

	/**
	 * @param aParent
	 */
	private void createBundleDescriptionComposite(Composite aParent) {
		Group bundleGroup = new Group(aParent, SWT.NULL);
		bundleGroup.setText("Bundle Information");
		bundleGroup.setLayout(new GridLayout(2, false));
		bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleSymbolicNameLabel = new Label(bundleGroup, SWT.NULL);
		bundleSymbolicNameLabel.setText("Bundle Symbolic Name");

		bundleSymbolicNameField = new Text(bundleGroup, SWT.BORDER);
		bundleSymbolicNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bundleSymbolicNameField.addModifyListener(checkPageCompleteListener);

		Label bundleNameLabel = new Label(bundleGroup, SWT.NULL);
		bundleNameLabel.setText("Bundle Name");

		bundleNameField = new Text(bundleGroup, SWT.BORDER);
		bundleNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bundleNameField.addModifyListener(checkPageCompleteListener);

		Label bundleVersionLabel = new Label(bundleGroup, SWT.NULL);
		bundleVersionLabel.setText("Bundle Version");

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
		checkPageComplete();
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

	protected void checkPageComplete() {
		// check bundle name not empty
		if(getBundleName() == null || getBundleName().isEmpty()) {
			setErrorMessage("Bundle Name: Bundle name cannot be empty.");
			setPageComplete(false);
			return;
		}

		// check bundle version not empty
		if(getBundleVersion() == null || getBundleVersion().isEmpty()) {
			setErrorMessage("Bundle Version: cannot be empty.");
			setPageComplete(false);
			return;
		}

		// check bundle symbolic name not empty
		if(getBundleSymbolicName() == null || getBundleSymbolicName().isEmpty()) {
			setErrorMessage("Bundle Symbolic Name: cannot be empty.");
			setPageComplete(false);
			return;
		}

		// check bundle format
		if(!IdUtil.isValidCompositeID3_0(getBundleSymbolicName())) {
			setErrorMessage("Bundle Symbolic Name: Cannot contain ilegal characters.");
			setPageComplete(false);
			return;
		}

		// check version format
		if(VersionUtil.validateVersion(getBundleVersion()).getSeverity() != IStatus.OK) {
			setErrorMessage("Bundle Version: The specified version does not have the correct format (major.minor.micro.qualifier) or contains invalid characters.");
			setPageComplete(false);
			return;
		}
		
		setErrorMessage(null);
		setPageComplete(true);
		
	}

}
