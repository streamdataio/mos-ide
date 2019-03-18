/**
 * 
 */
package com.motwin.ide.server.docs;

import com.motwin.ide.docs.AbstractJavaDocs;
import com.motwin.ide.server.docs.internal.ServerDocsPlugin;

/**
 * @author ctranxuan
 * 
 */
public final class ServerJavaDocs extends AbstractJavaDocs {

    @Override
    protected String getPluginID() {
        return ServerDocsPlugin.PLUGIN_ID;
    }
}
