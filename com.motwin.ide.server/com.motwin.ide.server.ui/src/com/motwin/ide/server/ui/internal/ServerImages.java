/**
 * 
 */
package com.motwin.ide.server.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fbou
 * 
 */
public class ServerImages {

    private static final Logger log                 = LoggerFactory.getLogger(ServerImages.class);

    public static final Image   CHANNEL_INTERCEPTOR = createImage("application/channel_interceptor.gif");
    public static final Image   CHANNEL_PROCESSOR   = createImage("application/channel_processor.gif");
    public static final Image   MESSAGE_PROCESSOR   = createImage("application/message_processor.gif");
    public static final Image   REQUEST_PROCESSOR   = createImage("application/request_processor.gif");
    public static final Image   SERIALIZABLE        = createImage("application/serializable.gif");

    public static final Image   DATABASE            = createImage("database/database.png");
    public static final Image   TABLE               = createImage("database/table.png");
    public static final Image   TABLE_PERSISTENT    = createImage("database/table_persistent.png");
    public static final Image   SCHEMA              = createImage("database/schema.png");
    public static final Image   COLUMN              = createImage("database/table_column.png");
    public static final Image   COLUMN_KEY          = createImage("database/table_column_key.png");
    public static final Image   SOURCE              = createImage("database/table_source.png");

    private static ImageDescriptor createDescriptor(final String key) {
        try {
            ImageRegistry imageRegistry = getImageRegistry();
            if (imageRegistry != null) {
                ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key);
                if (imageDescriptor == null) {
                    imageDescriptor = doCreateDescriptor(key);
                    imageRegistry.put(key, imageDescriptor);
                }
                return imageDescriptor;
            }
        } catch (Exception ex) {
            log.error(key, ex);
        }
        return null;
    }

    private static Image createImage(final String key) {
        createDescriptor(key);
        ImageRegistry imageRegistry = getImageRegistry();
        return imageRegistry == null ? null : imageRegistry.get(key);
    }

    private static ImageRegistry getImageRegistry() {
        MotwinServerUIPlugin plugin = MotwinServerUIPlugin.getDefault();
        return plugin == null ? null : plugin.getImageRegistry();
    }

    private static ImageDescriptor doCreateDescriptor(final String image) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(MotwinServerUIPlugin.PLUGIN_ID, "icons/" + image); //$NON-NLS-1$
    }

    private static ImageDescriptor getOverlayImageDescriptor(final String basekey, final String overlaykey,
                                                             final int quadrant) {
        String key = basekey + overlaykey;
        try {
            ImageRegistry imageRegistry = getImageRegistry();
            if (imageRegistry != null) {
                ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key);
                if (imageDescriptor == null) {
                    ImageDescriptor base = createDescriptor(basekey);
                    ImageDescriptor overlay = createDescriptor(overlaykey);
                    if (base == null || overlay == null) {
                        log.error("cannot construct overlay image descriptor for " + basekey + " " + overlaykey); //$NON-NLS-1$ //$NON-NLS-2$
                        return null;
                    }
                    imageDescriptor = createOverlayDescriptor(base, overlay, quadrant);
                    imageRegistry.put(key, imageDescriptor);
                }
                return imageDescriptor;
            }
        } catch (Exception ex) {
            log.error(key, ex);
        }
        return null;
    }

    public static Image getOverlayImage(final String base, final String overlay, final int quadrant) {
        getOverlayImageDescriptor(base, overlay, quadrant);
        ImageRegistry imageRegistry = getImageRegistry();
        return imageRegistry == null ? null : imageRegistry.get(base + overlay);
    }

    public static Image createOverlayImage(final String key, final Image base, final ImageDescriptor overlay,
                                           final int quadrant) {
        ImageRegistry registry = getImageRegistry();
        if (registry != null) {
            Image image = registry.get(key);
            if (image == null) {
                ImageDescriptor descriptor = registry.getDescriptor(key);
                if (descriptor == null) {
                    registry.put(key, createOverlayDescriptor(base, overlay, quadrant));
                }
                image = registry.get(key);
            }
            return image;
        }
        return null;
    }

    private static ImageDescriptor createOverlayDescriptor(final ImageDescriptor base, final ImageDescriptor overlay,
                                                           final int quadrant) {
        return new DecorationOverlayIcon(base.createImage(), overlay, quadrant);
    }

    private static ImageDescriptor createOverlayDescriptor(final Image base, final ImageDescriptor overlay,
                                                           final int quadrant) {
        return new DecorationOverlayIcon(base, overlay, quadrant);
    }

}
