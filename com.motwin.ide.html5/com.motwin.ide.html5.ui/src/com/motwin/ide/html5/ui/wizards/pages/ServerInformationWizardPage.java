/**
 * 
 */
package com.motwin.ide.html5.ui.wizards.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.ui.Images;

/**
 * @author fbou
 */
public class ServerInformationWizardPage extends WizardPage {

    private final static String    DEFAULT_PROTOCOL = "http";
    private final static String    DEFAULT_URL      = "tests.motwin.net";
    private final static String    DEFAULT_PORT     = "1249";
    private final static String[]  PROTOCOLS        = new String[] { "http", "https" };

    private final static String    REGEX_URL        = "^([a-z0-9\\._-]+)$";
    private final static String    REGEX_PORT       = "^[0-9]+$";

    private Combo                  serverProtocolField;
    private Text                   serverUrlField;
    private Text                   serverPortField;

    protected final ModifyListener checkPageCompleteListener;

    private boolean                initialized      = false;

    /**
     * Default constructor
     */
    public ServerInformationWizardPage() {
        this(HTML5Messages.wizardHTML5ProjectServerInformationPage);
        setTitle(HTML5Messages.wizardHTML5ProjectServerInformationPage);
        setDescription(HTML5Messages.wizardHTML5ProjectServerInformationDescription);
        setImageDescriptor(Images.motwinMedium);
    }

    /**
     * constructor
     * 
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

        Label protocolLabel = new Label(bundleGroup, SWT.NULL);
        protocolLabel.setText("Protocol:");

        serverProtocolField = new Combo(bundleGroup, SWT.READ_ONLY);
        serverProtocolField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        serverProtocolField.setItems(PROTOCOLS);
        serverProtocolField.addModifyListener(checkPageCompleteListener);

        Label bundleNameLabel = new Label(bundleGroup, SWT.NULL);
        bundleNameLabel.setText("Server Address:");

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
        serverProtocolField.setText(DEFAULT_PROTOCOL);
    }

    @Override
    public void setVisible(boolean aVisible) {
        super.setVisible(aVisible);
        if (aVisible && !initialized) {
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

    /**
     * @return the serverProtocolField
     */
    public String getServerProtocol() {
        return serverProtocolField.getText();
    }

    protected boolean validate() {

        // check server url not empty
        if (getServerUrl() == null || getServerUrl().isEmpty()) {
            setErrorMessage("Server URL: Cannot be empty.");
            return false;
        }

        // check server url format
        if (!getServerUrl().matches(REGEX_URL)) {
            setErrorMessage("Server URL: Format is not valid!");
            return false;
        }

        // check server port not empty
        if (getServerPort() == null || getServerPort().isEmpty()) {
            setErrorMessage("Server Port: Cannot be empty.");
            return false;
        }

        // check server port format
        if (!getServerPort().matches(REGEX_PORT)) {
            setErrorMessage("Server Port: Format is not valid!");
            return false;
        }

        // check server protocol not empty
        if (getServerProtocol() == null || getServerProtocol().isEmpty()) {
            setErrorMessage("Server Protocol: Cannot be empty.");
            return false;
        }

        setErrorMessage(null);
        return true;
    }

    private void checkPageComplete() {
        setPageComplete(validate());
    }

}
