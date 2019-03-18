/**
 * 
 */
package com.motwin.ide.html5.ui.exports;

import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;

import com.google.common.collect.Maps;
import com.motwin.ide.html5.templates.MotwinHTML5TemplatePlugin;
import com.motwin.ide.html5.ui.exports.pages.XcodeInformationWizardPage;

/**
 * @author fbou
 * 
 */
public class XcodeExportWizard extends AbstractExportWizard {

    private final XcodeInformationWizardPage iosInformationPage = new XcodeInformationWizardPage();

    @Override
    protected String getTemplateBundleId() {
        return MotwinHTML5TemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/cordovaIOS";
    }

    @Override
    protected String getTemplateWebContentPath() {
        return "www";
    }

    @Override
    protected WizardPage getLastPage() {
        return iosInformationPage;
    }

    @Override
    protected String getReadmeFilePath() {
        return "MotwinReadme.html";
    }

    @Override
    protected Map<String, String> getInputData() {
        Map<String, String> input = Maps.newHashMap();
        input.put("__PROJECT_NAME__", iosInformationPage.getProjectName());
        input.put("__PROJECT_PACKAGE__", iosInformationPage.getProjectPackage());
        input.put("___FULLUSERNAME___", iosInformationPage.getUserName());
        input.put("___DATE___", iosInformationPage.getDate());
        input.put("___YEAR___", iosInformationPage.getYear());
        input.put("___ORGANIZATIONNAME___", iosInformationPage.getOrganizationName());
        return input;
    }

    @Override
    public void addPages() {
        super.addPages();
        addPage(iosInformationPage);
    }

}
