/**
 * 
 */
package com.motwin.ide.dashboard.ui;

import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;

/**
 * @author ctranxuan
 * 
 */
public final class DashboardConstants {
    public static final String DASHBOARD_EXTENSION_POINT_ID       = "com.motwin.ide.dashboard.ui.dashboard";           //$NON-NLS-1$
    public static final String DASHBOARD_PREFERENCE_PAGE_ID       = "com.motwin.ide.dashboard.ui.preferencePage";      //$NON-NLS-N$
    public static final String DEFAULT_OPEN_DASHBOARD_STARTUP     = DashboardPlugin.PLUGIN_ID + ".dashboard.startup";  //$NON-NLS-1$

    public static final String DASHBOARD_UA_EXPANSION_SUFFIX      = ".expansion";                                      //$NON-NLS-N$

    public static final String RSS_AVAILABLE_PRODUCTS_LIST        = DashboardPlugin.PLUGIN_ID + ".rss.products.list";  //$NON-NLS-N$
    public static final String RSS_PRODUCTS_PREFERENCES_PREFIX    = DashboardPlugin.PLUGIN_ID + ".rss.products.pref."; //$NON-NLS-N$
    public static final String RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME = "Feeds";                                           //$NON-NLS-N$
    public static final String RSS_ALL_PRODUCTS                   = "all";                                             //$NON-NLS-N$
    public static final String RSS_ALL_PRODUCTS_PREFERENCE             = RSS_PRODUCTS_PREFERENCES_PREFIX + RSS_ALL_PRODUCTS; //$NON-NLS-N$

    private DashboardConstants() {
    }
}
