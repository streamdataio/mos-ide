/**
 * 
 */
package com.motwin.ide.core.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;

/**
 * @author ctranxuan
 * 
 */
public class CoreUtil {

    private CoreUtil() {
    }

    public static File locateResource(final String aPluginId, final String aPath) throws URISyntaxException,
            IOException {
        File result = null;

        URL url;
        url = Platform.getBundle(aPluginId).getEntry(aPath);

        result = new File(URIUtil.toURI(FileLocator.toFileURL(url)));
        return result;
    }
}
