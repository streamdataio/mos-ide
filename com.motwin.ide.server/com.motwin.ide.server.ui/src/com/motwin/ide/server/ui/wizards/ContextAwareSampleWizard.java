/**
 * 
 */
package com.motwin.ide.server.ui.wizards;

import com.motwin.ide.server.templates.MotwinServerTemplatePlugin;

/**
 * @author ctranxuan
 * 
 */
public class ContextAwareSampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "ContextAware-Server";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinServerTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/contextaware";
    }

}
