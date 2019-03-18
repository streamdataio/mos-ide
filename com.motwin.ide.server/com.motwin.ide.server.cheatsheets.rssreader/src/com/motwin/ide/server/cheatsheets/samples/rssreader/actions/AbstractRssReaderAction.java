/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.rssreader.actions;

import org.eclipse.core.runtime.IPath;

import com.motwin.ide.cheatsheets.actions.ISampleAction;
import com.motwin.ide.server.cheatsheets.actions.AbstractServerProjectAction;
import com.motwin.ide.server.cheatsheets.samples.rssreader.internal.RssReaderCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractRssReaderAction extends AbstractServerProjectAction implements ISampleAction {

    @Override
    public String getJavaBasePackage() {
        return "com.motwin.rssreader.connectors";
    }

    @Override
    public String getSampleCodePath() {
        return "/code";
    }

    @Override
    public String getProjectName() {
        return "RssReader-Server";
    }

    @Override
    public String getPluginID() {
        return RssReaderCheatSheetPlugin.PLUGIN_ID;
    }

    public String getApplicationXml() {
        return getSrcSpringFolder() + IPath.SEPARATOR + "application.xml";
    }

    public String getVirtualDbXml() {
        return getSrcSpringFolder() + IPath.SEPARATOR + "virtualdb.xml";
    }
}
