/**
 * 
 */
package com.motwin.ide.android.ui.internal;

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
public class AndroidImages {

	private static final Logger log = LoggerFactory.getLogger(AndroidImages.class);

	private static ImageDescriptor createDescriptor(String key) {
		try {
			ImageRegistry imageRegistry = getImageRegistry();
			if(imageRegistry != null) {
				ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key);
				if(imageDescriptor == null) {
					imageDescriptor = doCreateDescriptor(key);
					imageRegistry.put(key, imageDescriptor);
				}
				return imageDescriptor;
			}
		} catch(Exception ex) {
			log.error(key, ex);
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static Image createImage(String key) {
		createDescriptor(key);
		ImageRegistry imageRegistry = getImageRegistry();
		return imageRegistry == null ? null : imageRegistry.get(key);
	}

	private static ImageRegistry getImageRegistry() {
		MotwinAndroidUIPlugin plugin = MotwinAndroidUIPlugin.getDefault();
		return plugin == null ? null : plugin.getImageRegistry();
	}

	private static ImageDescriptor doCreateDescriptor(String image) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(MotwinAndroidUIPlugin.PLUGIN_ID, "icons/" + image); //$NON-NLS-1$
	}

	private static ImageDescriptor getOverlayImageDescriptor(String basekey, String overlaykey, int quadrant) {
		String key = basekey + overlaykey;
		try {
			ImageRegistry imageRegistry = getImageRegistry();
			if(imageRegistry != null) {
				ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key);
				if(imageDescriptor == null) {
					ImageDescriptor base = createDescriptor(basekey);
					ImageDescriptor overlay = createDescriptor(overlaykey);
					if(base == null || overlay == null) {
						log.error("cannot construct overlay image descriptor for " + basekey + " " + overlaykey); //$NON-NLS-1$ //$NON-NLS-2$
						return null;
					}
					imageDescriptor = createOverlayDescriptor(base, overlay, quadrant);
					imageRegistry.put(key, imageDescriptor);
				}
				return imageDescriptor;
			}
		} catch(Exception ex) {
			log.error(key, ex);
		}
		return null;
	}

	public static Image getOverlayImage(String base, String overlay, int quadrant) {
		getOverlayImageDescriptor(base, overlay, quadrant);
		ImageRegistry imageRegistry = getImageRegistry();
		return imageRegistry == null ? null : imageRegistry.get(base + overlay);
	}

	public static Image createOverlayImage(String key, Image base, ImageDescriptor overlay, int quadrant) {
		ImageRegistry registry = getImageRegistry();
		if(registry != null) {
			Image image = registry.get(key);
			if(image == null) {
				ImageDescriptor descriptor = registry.getDescriptor(key);
				if(descriptor == null) {
					registry.put(key, createOverlayDescriptor(base, overlay, quadrant));
				}
				image = registry.get(key);
			}
			return image;
		}
		return null;
	}

	private static ImageDescriptor createOverlayDescriptor(ImageDescriptor base, ImageDescriptor overlay, int quadrant) {
		return new DecorationOverlayIcon(base.createImage(), overlay, quadrant);
	}

	private static ImageDescriptor createOverlayDescriptor(Image base, ImageDescriptor overlay, int quadrant) {
		return new DecorationOverlayIcon(base, overlay, quadrant);
	}

}
