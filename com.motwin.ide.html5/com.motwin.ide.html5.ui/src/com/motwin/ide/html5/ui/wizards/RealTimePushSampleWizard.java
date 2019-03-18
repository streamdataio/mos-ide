/**
 * 
 */
package com.motwin.ide.html5.ui.wizards;

import com.motwin.ide.html5.templates.MotwinHTML5TemplatePlugin;

/**
 * @author fbou
 * 
 */
public class RealTimePushSampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "RealTimePush-HTML5";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinHTML5TemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/realtimepush";
    }

}
