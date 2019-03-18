package com.motwin.ide.android.cheatsheets.rssreader.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RssReaderCheatSheetPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String                  PLUGIN_ID = "com.motwin.ide.android.cheatsheets.rssreader"; //$NON-NLS-1$

    // The shared instance
    private static RssReaderCheatSheetPlugin plugin;

    /**
     * The constructor
     */
    public RssReaderCheatSheetPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
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
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
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
    public static RssReaderCheatSheetPlugin getDefault() {
        return plugin;
    }

}
