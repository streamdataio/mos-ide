/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author ctranxuan
 * 
 */
public enum FeedCategory {


    // @formatter:off
    ALL("all", "All"),
    ANDROID_SDK("motwin_android_sdk", "Android SDK"), 
    HTML5_SDK("motwin_html5_sdk", "HTML5 SDK"), 
    IDE("motwin_ide", "Motwin IDE"), 
    IPHONE_SDK("motwin_iphone_sdk", "iPhone SDK"), 
    CONNECTORS("motwin_motwin_connectors", "Motwin Connectors"), 
    PLATFORM("motwin_motwin_platform", "Motwin Platform"), 
    SERVER_SDK("motwin_server_sdk", "Server SDK");    // @formatter:on

    private String                           label;
    private String                           gsID;

    private static Map<String, FeedCategory> categories = Maps.newHashMap();

    static {
        for (FeedCategory category : values()) {
            categories.put(category.gsID, category);
        }
    }

    /**
     * @param aGsID
     * @param aLabel
     */
    private FeedCategory(final String aGsID, final String aLabel) {
        label = aLabel;
        gsID = aGsID;
    }

    public static FeedCategory get(final String aGsID) {
        return categories.get(aGsID);
    }

    public static boolean contains(final String aGsID) {
        return categories.containsKey(aGsID);
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the gsID
     */
    public String getGsID() {
        return gsID;
    }
}
