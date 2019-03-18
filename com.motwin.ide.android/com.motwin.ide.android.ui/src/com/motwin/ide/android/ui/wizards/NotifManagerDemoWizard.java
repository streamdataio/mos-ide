/**
 * 
 */
package com.motwin.ide.android.ui.wizards;

import com.motwin.ide.android.templates.MotwinAndroidTemplatePlugin;
import com.motwin.ide.android.ui.GoogleAPIValidator;
import com.motwin.ide.android.ui.AndroidTargetsGroup.IAndroidTargetValidator;
import com.motwin.ide.android.ui.wizards.pages.SampleJavaProjectPage;
import com.motwin.ide.android.ui.wizards.pages.ServerInformationWizardPage;

/**
 * @author fbou
 * 
 */
public class NotifManagerDemoWizard extends AbstractSampleProjectWizard {

    @Override
    public void addPages() {
        // java page
        projectPage = new SampleJavaProjectPage(getProjectName()) {
            @Override
            protected IAndroidTargetValidator getAndroidTargetValidator() {
                return new GoogleAPIValidator(8);
            }
        };
        addPage(projectPage);

        // server page
        serverPage = new ServerInformationWizardPage();
        addPage(serverPage);

    }

    @Override
    protected String getProjectName() {
        return "NotificationManagerDemo-Android";
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinAndroidTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/notifmanagerdemo";
    }

}
