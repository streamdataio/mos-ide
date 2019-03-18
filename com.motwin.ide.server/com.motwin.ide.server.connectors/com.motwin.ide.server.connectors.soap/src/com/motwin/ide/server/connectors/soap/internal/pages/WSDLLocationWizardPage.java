package com.motwin.ide.server.connectors.soap.internal.pages;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class WSDLLocationWizardPage extends WizardPage {
	
	private Text bundleSymbolicNameField;

	public WSDLLocationWizardPage() {
		super("WSDL Location");
		setTitle("WSDL Location");
		setDescription("Choose a WSDL file that will be used to generate Java stubs for your web service client.");
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
		bundleGroup.setText("WSDL Location");
		bundleGroup.setLayout(new GridLayout(2, false));
		bundleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		bundleSymbolicNameField = new Text(bundleGroup, SWT.BORDER);
		bundleSymbolicNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bundleSymbolicNameField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent aEvent) {
				if(StringUtils.isNotBlank(bundleSymbolicNameField.getText())) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}
		});

	}
	
	public String getWSDLLocation() {
		return bundleSymbolicNameField.getText();
	}

}
