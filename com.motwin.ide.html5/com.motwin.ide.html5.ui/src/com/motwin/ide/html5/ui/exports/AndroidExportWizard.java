/**
 * 
 */
package com.motwin.ide.html5.ui.exports;

import java.io.File;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;

import com.google.common.collect.Maps;
import com.motwin.ide.html5.templates.MotwinHTML5TemplatePlugin;
import com.motwin.ide.html5.ui.exports.pages.AndroidInformationWizardPage;

/**
 * @author fbou
 * 
 */
public class AndroidExportWizard extends AbstractExportWizard {

    private final AndroidInformationWizardPage androidInformationPage = new AndroidInformationWizardPage();

    @Override
    protected String getTemplateBundleId() {
        return MotwinHTML5TemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/cordovaAndroid";
    }

    @Override
    protected String getTemplateWebContentPath() {
        return "assets/www";
    }

    @Override
    protected WizardPage getLastPage() {
        return androidInformationPage;
    }

    @Override
    protected String getReadmeFilePath() {
        return "MotwinReadme.html";
    }

    @Override
    protected Map<String, String> getInputData() {
        Map<String, String> input = Maps.newHashMap();
        input.put("__PROJECT_NAME__", androidInformationPage.getProjectName());
        input.put("__PROJECT_PACKAGE__", androidInformationPage.getProjectPackage());
        input.put("__PROJECT_PACKAGE_FOLDER__", androidInformationPage.getProjectPackage().replace(".", File.separator));
        input.put("__PROJECT_VERSION__", androidInformationPage.getProjectVersion());
        input.put("__PROJECT_VERSION_CODE__", androidInformationPage.getProjectVersionCode());
        return input;
    }

    @Override
    public void addPages() {
        super.addPages();
        addPage(androidInformationPage);
    }

}
