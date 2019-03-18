package com.motwin.ide.server.connectors.core.internal.wizard;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;

import com.google.common.base.Preconditions;
import com.motwin.ide.server.connectors.core.wizard.IConnectorWizard;
import com.motwin.ide.server.connectors.core.wizard.IConnectorWizardDescriptor;

public class ConnectorImpl implements IConnectorWizardDescriptor {
	
	private final IConfigurationElement config;
	
	public ConnectorImpl(IConfigurationElement aConfig) {
		config = Preconditions.checkNotNull(aConfig);
	}

	@Override
	public String getName() {
		return config.getAttribute("name");
	}

	@Override
	public String getVersion() {
		return config.getAttribute("version");
	}

	@Override
	public String getDescription() {
		return config.getAttribute("description");
	}

	@Override
	public URL getIcon() {
		String iconFullPath = String.format("platform:/plugin/%s/%s", 
				config.getContributor().getName(), config.getAttribute("icon"));
		URL iconUrl;
		try {
			iconUrl = new URL(iconFullPath);
		} catch (MalformedURLException e) {
			iconUrl = null;
		}
		return iconUrl;
	}
	
	public IConnectorWizard createWizard() {
		IConnectorWizard wizard;
		try {
			wizard = (IConnectorWizard) config.createExecutableExtension("wizardClass");
		} catch (Exception e) {
			wizard = null;
		}
		return wizard;
	}

}
