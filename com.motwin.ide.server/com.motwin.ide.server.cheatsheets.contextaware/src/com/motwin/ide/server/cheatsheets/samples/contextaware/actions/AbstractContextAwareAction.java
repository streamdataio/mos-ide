/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.contextaware.actions;

import org.eclipse.core.runtime.IPath;

import com.motwin.ide.cheatsheets.actions.ISampleAction;
import com.motwin.ide.server.cheatsheets.actions.AbstractServerProjectAction;
import com.motwin.ide.server.cheatsheets.samples.contextaware.internal.ContextAwareCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractContextAwareAction extends AbstractServerProjectAction implements ISampleAction {
    protected static final String APPLICATION_XML_FILE = "application.xml";

    @Override
    public String getJavaBasePackage() {
        return "com.motwin.rssreader.context";
    }

    @Override
    public String getSampleCodePath() {
        return "/code";
    }

    @Override
    public String getProjectName() {
        return "ContextAware-Server";
    }

    @Override
    public String getPluginID() {
        return ContextAwareCheatSheetPlugin.PLUGIN_ID;
    }

    public String getApplicationXml() {
        return getSrcSpringFolder() + IPath.SEPARATOR + APPLICATION_XML_FILE;
    }

}
