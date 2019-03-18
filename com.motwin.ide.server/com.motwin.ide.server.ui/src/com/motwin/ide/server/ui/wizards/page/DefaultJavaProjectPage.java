/**
 * 
 */
package com.motwin.ide.server.ui.wizards.page;

import com.motwin.ide.server.ui.internal.ServerMessages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou
 *
 */
public class DefaultJavaProjectPage extends AbstractJavaProjectPage {

	/**
	 * Default constructor
	 */
	public DefaultJavaProjectPage() {
		super(ServerMessages.wizardPlatformProjectPageName);
		setTitle(ServerMessages.wizardPlatformProjectPageName);
		setDescription(ServerMessages.wizardPlatformProjectPageDescription);
		setImageDescriptor(Images.motwinMedium);
	}

}
