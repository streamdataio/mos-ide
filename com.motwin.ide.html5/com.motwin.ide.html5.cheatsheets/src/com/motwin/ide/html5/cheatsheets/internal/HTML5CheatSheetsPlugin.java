package com.motwin.ide.html5.cheatsheets.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class HTML5CheatSheetsPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String            PLUGIN_ID = "com.motwin.ide.html5.cheatsheets"; //$NON-NLS-1$

    // The shared instance
    private static HTML5CheatSheetsPlugin plugin;

    public HTML5CheatSheetsPlugin() {
    }

    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    public static HTML5CheatSheetsPlugin getDefault() {
        return plugin;
    }
}
