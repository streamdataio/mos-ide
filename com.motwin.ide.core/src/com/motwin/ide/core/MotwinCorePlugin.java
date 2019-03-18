/**
 * 
 */
package com.motwin.ide.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * @author fbou
 * 
 */
public class MotwinCorePlugin extends Plugin {

    public final static String      PLUGIN_ID = "com.motwin.ide.core";

    private static MotwinCorePlugin plugin;

    /**
     * @return the plugin
     */
    public static MotwinCorePlugin getDefault() {
        return plugin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext aContext) throws Exception {
        plugin = this;
        super.start(aContext);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext aContext) throws Exception {
        plugin = null;
        super.stop(aContext);
    }

}
