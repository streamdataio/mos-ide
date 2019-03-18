/**
 * 
 */
package com.motwin.ide.server.ui.wizards.page;

import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Preconditions;
import com.motwin.ide.server.ui.internal.ServerMessages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou
 *
 */
public class SampleJavaProjectPage extends AbstractJavaProjectPage {

	private final String defaultProjectName;

	/**
	 * @param aDefaultProjectName
	 */
	public SampleJavaProjectPage(String aDefaultProjectName) {
		super(ServerMessages.wizardPlatformSamplePageName);
		setTitle(ServerMessages.wizardPlatformSamplePageName);
		setDescription(ServerMessages.wizardPlatformSamplePageDescription);
		setImageDescriptor(Images.motwinMedium);
		defaultProjectName = Preconditions.checkNotNull(aDefaultProjectName);
	}

	@Override
	public void createControl(Composite aParent) {
		super.createControl(aParent);
		projectNameField.setText(defaultProjectName);
		projectNameField.setEnabled(false);
		projectNameField.setEditable(false);
	}

}
