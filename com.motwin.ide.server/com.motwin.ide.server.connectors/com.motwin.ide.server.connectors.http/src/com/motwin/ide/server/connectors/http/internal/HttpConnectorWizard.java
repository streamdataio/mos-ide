package com.motwin.ide.server.connectors.http.internal;

import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.motwin.ide.server.connectors.http.HttpConnectorPlugin;
import com.motwin.ide.server.connectors.ui.wizard.AbstractReadmeConnectorWizard;

public class HttpConnectorWizard extends AbstractReadmeConnectorWizard {
	
	private final static String NETTY_VERSION = "3.6.6.Final";
	private final static String ASYNC_HTTP_CLIENT_VERSION = "1.7.20";

	@Override
	protected Collection<BundleCopy> getBundleCopies() {
		return ImmutableList.of(
				new BundleCopy(HttpConnectorPlugin.PLUGIN_ID, 
						"libraries/netty-" + NETTY_VERSION + ".jar", 
						"repository/connectors/netty-" + NETTY_VERSION + ".jar"),
				new BundleCopy(HttpConnectorPlugin.PLUGIN_ID, 
						"libraries/async-http-client-" + ASYNC_HTTP_CLIENT_VERSION + ".jar", 
						"repository/connectors/async-http-client-" + ASYNC_HTTP_CLIENT_VERSION + ".jar"));
	}

	@Override
	protected Collection<BundleImport> getBundleImports() {
		return ImmutableList.of(
				new BundleImport("com.ning.async-http-client", ASYNC_HTTP_CLIENT_VERSION));
	}

	@Override
	protected String getReadmeLocationPath() {
		return "documents/readme.html";
	}

	@Override
	protected String getReadmeLocationPluginId() {
		return HttpConnectorPlugin.PLUGIN_ID;
	}

}
