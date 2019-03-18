package com.motwin.ide.server.connectors.core.wizard;

import java.net.URL;

public interface IConnectorWizardDescriptor {
	
	String getName();
	
	String getVersion();
	
	String getDescription();
	
	URL getIcon();
	
	IConnectorWizard createWizard();

}
