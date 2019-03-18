/**
 * 
 */
package com.motwin.ide.android.ui.wizards;

import com.motwin.ide.android.templates.MotwinAndroidTemplatePlugin;

/**
 * @author ctranxuan
 * 
 */
public class ContextAwareSampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "ContextAware-Android";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinAndroidTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/contextaware";
    }

}
