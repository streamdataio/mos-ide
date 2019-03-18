package com.motwin.ide.android.docs.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class AndroidDocsPlugin implements BundleActivator {
    public static String         PLUGIN_ID = "com.motwin.ide.android.docs";

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        AndroidDocsPlugin.context = bundleContext;
    }

    @Override
    public void stop(final BundleContext bundleContext) throws Exception {
        AndroidDocsPlugin.context = null;
    }

}
