/**
 * 
 */
package com.motwin.ide.android.ui.wizards;

import com.motwin.ide.android.templates.MotwinAndroidTemplatePlugin;

/**
 * @author fbou
 * 
 */
public class Hello2SampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "HelloWorld2-Android";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinAndroidTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/hello2";
    }

}
