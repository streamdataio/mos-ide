/**
 * 
 */
package com.motwin.ide.server.ui.wizards;

import com.motwin.ide.server.templates.MotwinServerTemplatePlugin;

/**
 * @author fbou
 *
 */
public class UISSampleWizard extends AbstractSampleProjectWizard {

	@Override
	protected String getProjectName() {
		return "uis-application";
	}

	@Override
	protected String getTemplateBundleId() {
		return MotwinServerTemplatePlugin.PLUGIN_ID;
	}

	@Override
	protected String getTemplateLocation() {
		return "templates/uis";
	}

}
