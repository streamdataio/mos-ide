package com.motwin.ide.server.connectors.twitter.internal;

import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.motwin.ide.server.connectors.twitter.TwitterConnectorPlugin;
import com.motwin.ide.server.connectors.ui.wizard.AbstractReadmeConnectorWizard;

public class TwitterWizard extends AbstractReadmeConnectorWizard {

	@Override
	protected Collection<BundleCopy> getBundleCopies() {
		return ImmutableList.of(
				new BundleCopy(TwitterConnectorPlugin.PLUGIN_ID, 
						"libraries/twitter4j-osgi.jar", 
						"repository/connectors/twitter4j-osgi.jar"));
	}

	@Override
	protected Collection<BundleImport> getBundleImports() {
		return ImmutableList.of(
				new BundleImport("org.twitter4j", "3.0"));
	}

	@Override
	protected String getReadmeLocationPath() {
		return "documents/readme.html";
	}

	@Override
	protected String getReadmeLocationPluginId() {
		return TwitterConnectorPlugin.PLUGIN_ID;
	}

}
