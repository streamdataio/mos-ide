/**
 * 
 */
package com.motwin.ide.server.ui.wizards;

import com.motwin.ide.server.templates.MotwinServerTemplatePlugin;

/**
 * @author fbou
 * 
 */
public class RssReaderSampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "RssReader-Server";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinServerTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/rssreader";
    }

}
