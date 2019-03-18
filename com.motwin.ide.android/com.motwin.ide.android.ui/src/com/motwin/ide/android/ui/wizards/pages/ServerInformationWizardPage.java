/**
 * 
 */
package com.motwin.ide.android.ui.wizards.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.motwin.ide.android.ui.internal.AndroidMessages;
import com.motwin.ide.ui.Images;

/**
 * @author fbou
 */
public class ServerInformationWizardPage extends WizardPage {

	private final static String DEFAULT_URL = "zsocket://tests.motwin.net";
	private final static String DEFAULT_PORT = "1247";

	private final static String REGEX_URL = "^(zsocket|socket|zssl|ssl):\\/\\/([a-z0-9\\._-]+)$";
	private final static String REGEX_PORT = "^[0-9]+$";

	private Text serverUrlField;
	private Text serverPortField;

	protected final ModifyListener checkPageCompleteListener;

	private boolean initialized = false;

	/**
	 * Default constructor
	 */
	public ServerInformationWizardPage() {
		this(AndroidMessages.wizardAndroidProjectServerInformationPage);
		setTitle(AndroidMessages.wizardAndroidProjectServerInformationPage);
		setDescription(AndroidMessages.wizardAndroidProjectServerInformationDescription);
		setImageDescriptor(Images.motwinMedium);
	}

	/**
	 * constructor
	 * @param aPageName
	 * @param aJavaProjectPage
	 */
	protected ServerInformationWizardPage(String aPageName) {
		super(aPageName);
		checkPageCompleteListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent aE) {
				checkPageComplete();
			}
		};
	}

	@Override
	public void createControl(Composite aParent) {
		Composite container = new Composite(aParent, SWT.NULL);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createServerDescriptionComposite(container);

		setControl(container);
		setPageComplete(false);
	}

	/**
	 * @param aParent
	 */
	protected void createServerDescriptionComposite(Composite aParent) {
		Group bundleGroup = new Group(aParent, SWT.NULL);
		bundleGroup.setText("Server Properties");
		bundleGroup.setLayout(new GridLayout(2, false));
		bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleNameLabel = new Label(bundleGroup, SWT.NULL);
		bundleNameLabel.setText("Server URL:");

		serverUrlField = new Text(bundleGroup, SWT.BORDER);
		serverUrlField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		serverUrlField.addModifyListener(checkPageCompleteListener);

		Label bundleVersionLabel = new Label(bundleGroup, SWT.NULL);
		bundleVersionLabel.setText("Server Port:");

		serverPortField = new Text(bundleGroup, SWT.BORDER);
		serverPortField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		serverPortField.addModifyListener(checkPageCompleteListener);

	}

	protected void setDefaultBundleInformation() {
		serverUrlField.setText(DEFAULT_URL);
		serverPortField.setText(DEFAULT_PORT);
	}

	@Override
	public void setVisible(boolean aVisible) {
		super.setVisible(aVisible);
		if(aVisible && !initialized) {
			setDefaultBundleInformation();
			initialized = true;
		}
	}

	/**
	 * @return the serverUrlField
	 */
	public String getServerUrl() {
		return serverUrlField.getText();
	}

	/**
	 * @return the serverPortField
	 */
	public String getServerPort() {
		return serverPortField.getText();
	}

	protected boolean validate() {

		// check server url not empty
		if(getServerUrl() == null || getServerUrl().isEmpty()) {
			setErrorMessage("Server URL: Cannot be empty.");
			return false;
		}

		// check server url format
		if(!getServerUrl().matches(REGEX_URL)) {
			setErrorMessage("Server URL: Format is not valid!");
			return false;
		}

		// check server port not empty
		if(getServerPort() == null || getServerPort().isEmpty()) {
			setErrorMessage("Server Port: Cannot be empty.");
			return false;
		}

		// check server port format
		if(!getServerPort().matches(REGEX_PORT)) {
			setErrorMessage("Server Port: Format is not valid!");
			return false;
		}

		setErrorMessage(null);
		return true;
	}

	private void checkPageComplete() {
		setPageComplete(validate());
	}

}
