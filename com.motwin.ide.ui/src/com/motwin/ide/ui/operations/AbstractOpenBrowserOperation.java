/**
 * 
 */
package com.motwin.ide.ui.operations;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractOpenBrowserOperation {
    private final URL url;

    protected AbstractOpenBrowserOperation(final String aURL) {
        Preconditions.checkNotNull(aURL, "aUrl cannot be null");

        try {
            url = new URL(aURL);

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("aURL [%s] is not a valid URL", aURL));

        }
    }

    public abstract void run();

    /**
     * @return the url
     */
    public URL getURL() {
        return url;
    }
}
