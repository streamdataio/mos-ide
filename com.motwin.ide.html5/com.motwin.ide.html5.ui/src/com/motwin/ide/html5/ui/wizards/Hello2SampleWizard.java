/**
 * 
 */
package com.motwin.ide.html5.ui.wizards;

import com.motwin.ide.html5.templates.MotwinHTML5TemplatePlugin;

/**
 * @author fbou
 * 
 */
public class Hello2SampleWizard extends AbstractSampleProjectWizard {

    @Override
    protected String getProjectName() {
        return "Helloworld-2-HTML5";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinHTML5TemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/hello2";
    }

}
