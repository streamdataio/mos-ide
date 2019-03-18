/**
 * 
 */
package com.motwin.ide.android.ui.wizards;

import com.motwin.ide.android.templates.MotwinAndroidTemplatePlugin;
import com.motwin.ide.android.ui.AndroidAPILevelValidator;
import com.motwin.ide.android.ui.AndroidTargetsGroup.IAndroidTargetValidator;
import com.motwin.ide.android.ui.wizards.pages.SampleJavaProjectPage;
import com.motwin.ide.android.ui.wizards.pages.ServerInformationWizardPage;

/**
 * @author fbou
 * 
 */
public class RealTimePushSampleWizard extends AbstractSampleProjectWizard {

    @Override
    public void addPages() {
        // java page
        projectPage = new SampleJavaProjectPage(getProjectName()) {
            @Override
            protected IAndroidTargetValidator getAndroidTargetValidator() {
                return new AndroidAPILevelValidator(8);
            }
        };
        addPage(projectPage);

        // server page
        serverPage = new ServerInformationWizardPage();
        addPage(serverPage);

    }

    @Override
    protected String getProjectName() {
        return "RealTimePush-Android";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinAndroidTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/realtimepush";
    }

}
