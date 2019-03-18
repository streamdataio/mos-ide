/**
 * 
 */
package com.motwin.ide.product;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * @author ctranxuan
 * 
 */
public class ProductPlugin extends Plugin {
    // The plug-in ID
    public static final String   PLUGIN_ID = "com.motwin.ide.product";

    // The shared instance
    private static ProductPlugin plugin;

    /**
     * The constructor
     */
    public ProductPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static ProductPlugin getDefault() {
        return plugin;
    }

}
