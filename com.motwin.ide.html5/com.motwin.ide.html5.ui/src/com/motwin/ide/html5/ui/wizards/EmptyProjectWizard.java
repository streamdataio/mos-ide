/**
 * 
 */
package com.motwin.ide.html5.ui.wizards;

import com.motwin.ide.html5.templates.MotwinHTML5TemplatePlugin;
import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.ui.Images;

/**
 * @author fbou
 * 
 */
public class EmptyProjectWizard extends AbstractEmptyProjectWizard {

    public EmptyProjectWizard() {
        setWindowTitle(HTML5Messages.wizardHTML5ProjectLocationPageName);
        setDefaultPageImageDescriptor(Images.motwinMedium);
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinHTML5TemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/emptyProject";
    }

}
