package com.motwin.ide.html5.docs.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HTML5DocsPlugin implements BundleActivator {

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        HTML5DocsPlugin.context = bundleContext;
    }

    @Override
    public void stop(final BundleContext bundleContext) throws Exception {
        HTML5DocsPlugin.context = null;
    }

}
