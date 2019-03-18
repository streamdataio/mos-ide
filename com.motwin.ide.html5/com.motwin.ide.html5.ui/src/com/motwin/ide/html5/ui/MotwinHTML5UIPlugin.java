package com.motwin.ide.html5.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MotwinHTML5UIPlugin extends AbstractUIPlugin {

    public static final String         PLUGIN_ID = "com.motwin.ide.html5.ui"; //$NON-NLS-1$

    private static MotwinHTML5UIPlugin plugin;

    /**
     * The constructor
     */
    public MotwinHTML5UIPlugin() {
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static MotwinHTML5UIPlugin getDefault() {
        return plugin;
    }

}
