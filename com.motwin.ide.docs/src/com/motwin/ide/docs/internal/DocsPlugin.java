package com.motwin.ide.docs.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class DocsPlugin implements BundleActivator {

    // public static final String PLUGIN_ID = "com.motwin.ide.docs";

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        DocsPlugin.context = bundleContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext bundleContext) throws Exception {
        DocsPlugin.context = null;
    }

}
