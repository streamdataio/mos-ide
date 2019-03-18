/**
 * 
 */
package com.motwin.ide.html5.ui.wizards;

import com.motwin.ide.html5.templates.MotwinHTML5TemplatePlugin;

/**
 * @author fbou
 * 
 */
public class RssReaderSampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "RssReader-HTML5";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinHTML5TemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/rssreader";
    }

}
