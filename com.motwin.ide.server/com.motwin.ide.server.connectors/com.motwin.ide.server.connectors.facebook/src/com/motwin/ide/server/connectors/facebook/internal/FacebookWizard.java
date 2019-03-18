package com.motwin.ide.server.connectors.facebook.internal;

import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.motwin.ide.server.connectors.facebook.FacebookConnectorPlugin;
import com.motwin.ide.server.connectors.ui.wizard.AbstractReadmeConnectorWizard;

public class FacebookWizard extends AbstractReadmeConnectorWizard {

    @Override
    protected Collection<BundleCopy> getBundleCopies() {
        return ImmutableList.of(new BundleCopy(FacebookConnectorPlugin.PLUGIN_ID, "libraries/facebook4j-osgi.jar",
                "repository/connectors/facebook4j-osgi.jar"));
    }

    @Override
    protected Collection<BundleImport> getBundleImports() {
        return ImmutableList.of(new BundleImport("org.facebook4j", "2.0.1"));
    }

    @Override
    protected String getReadmeLocationPath() {
        return "documents/readme.html";
    }

    @Override
    protected String getReadmeLocationPluginId() {
        return FacebookConnectorPlugin.PLUGIN_ID;
    }

}
