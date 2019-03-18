/**
 * 
 */
package com.motwin.ide.android.ui.wizards;

import com.motwin.ide.android.templates.MotwinAndroidTemplatePlugin;

/**
 * @author fbou
 * 
 */
public class RssReaderSampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "RssReader-Android";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinAndroidTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/rssreader";
    }

}
