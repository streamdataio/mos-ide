/**
 * 
 */
package com.motwin.ide.dashboard.ui;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;

/**
 * @author ctranxuan
 * 
 */
public enum DashboardImages {
    INSTANCE;

    public static final ImageDescriptor RSS           = DashboardPlugin.imageDescriptorFromPlugin(
                                                              DashboardPlugin.PLUGIN_ID, "icons/obj16/rss.png");

    public static final ImageDescriptor RSS_CONFIGURE = DashboardPlugin.imageDescriptorFromPlugin(
                                                              DashboardPlugin.PLUGIN_ID,
                                                              "icons/obj16/rss_configure.png");

    private final Map<String, Image>    imagesCache   = new MapMaker().weakValues().makeMap();

    private DashboardImages() {
    }

    public Image getImage(final String aPath) {
        Preconditions.checkNotNull(aPath, "aPath cannot be null");

        return getImage(DashboardPlugin.PLUGIN_ID, aPath);
    }

    public Image getImage(final String aPluginID, final String aPath) {
        Preconditions.checkNotNull(aPluginID, "aPluginID cannot be null");
        Preconditions.checkNotNull(aPath, "aPath cannot be null");

        String imageId;
        imageId = toImageId(aPluginID, aPath);

        Image image;
        image = imagesCache.get(imageId);

        if (image == null) {
            image = newImage(aPluginID, aPath);
        }

        return image;
    }

    private String toImageId(final String aPluginID, final String aPath) {
        return aPluginID + IPath.SEPARATOR + aPath;
    }

    private Image newImage(final String aPluginID, final String aPath) {
        Image image;
        image = null;

        ImageDescriptor imageDescriptor;
        imageDescriptor = DashboardPlugin.imageDescriptorFromPlugin(aPluginID, aPath);

        if (imageDescriptor != null) {
            image = imageDescriptor.createImage();

            String imageId;
            imageId = toImageId(aPluginID, aPath);
            imagesCache.put(imageId, image);
        }

        return image;
    }

}
