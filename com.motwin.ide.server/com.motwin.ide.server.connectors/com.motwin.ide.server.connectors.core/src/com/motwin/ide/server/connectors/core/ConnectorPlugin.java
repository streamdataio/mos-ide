package com.motwin.ide.server.connectors.core;

import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.motwin.ide.server.connectors.core.internal.wizard.ConnectorImpl;
import com.motwin.ide.server.connectors.core.wizard.IConnectorWizardDescriptor;

/**
 * The activator class controls the plug-in life cycle
 */
public class ConnectorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.motwin.ide.server.connectors.core"; //$NON-NLS-1$

	// The shared instance
	private static ConnectorPlugin plugin;
	
	/**
	 * The constructor
	 */
	public ConnectorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ConnectorPlugin getDefault() {
		return plugin;
	}
	
	public List<IConnectorWizardDescriptor> getConnectors() throws Exception {
		IExtensionPoint extensionPoint;
		extensionPoint = Platform.getExtensionRegistry().getExtensionPoint("com.motwin.ide.server.connectors"); 
		
		IExtension[] extensions;
		extensions = extensionPoint.getExtensions();
		
		Builder<IConnectorWizardDescriptor> connectorsBuilder;
		connectorsBuilder = ImmutableList.builder();
		
		for(IExtension extension : extensions) {
			for(IConfigurationElement config : extension.getConfigurationElements()) {
				connectorsBuilder.add(new ConnectorImpl(config));
			}
		}
		
		return connectorsBuilder.build();
	}

}
