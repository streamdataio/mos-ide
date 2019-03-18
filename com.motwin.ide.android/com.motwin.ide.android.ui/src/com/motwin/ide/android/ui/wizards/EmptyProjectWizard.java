/**
 * 
 */
package com.motwin.ide.android.ui.wizards;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkingSet;

import com.android.ide.eclipse.adt.internal.editors.manifest.ManifestEditor;
import com.motwin.ide.android.templates.MotwinAndroidTemplatePlugin;
import com.motwin.ide.android.ui.internal.AndroidMessages;
import com.motwin.ide.android.ui.wizards.pages.AndroidInformationWizardPage;
import com.motwin.ide.android.ui.wizards.pages.DefaultJavaProjectPage;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractProjectWizard;

/**
 * @author fbou
 * 
 */
@SuppressWarnings("restriction")
public class EmptyProjectWizard extends AbstractProjectWizard {

    private DefaultJavaProjectPage       projectPage;
    private AndroidInformationWizardPage androidInformationPage;

    public EmptyProjectWizard() {
        setWindowTitle(AndroidMessages.wizardAndroidProjectLocationPageName);
        setDefaultPageImageDescriptor(Images.motwinMedium);
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        // java page
        projectPage = new DefaultJavaProjectPage();
        addPage(projectPage);

        // bundle page
        androidInformationPage = new AndroidInformationWizardPage(projectPage);
        addPage(androidInformationPage);
    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinAndroidTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/emptyProject";
    }

    @Override
    protected String getWelcomeFileLocation() {
        return "AndroidManifest.xml";
    }

    @Override
    protected String getWelcomeFileEditor() {
        return ManifestEditor.ID;
    }

    @Override
    protected String getWelcomePerspective() {
        return "org.eclipse.jdt.ui.JavaPerspective";
    }

    @Override
    protected String getProjectName() {
        return projectPage.getProjectName();
    }

    @Override
    protected String getProjectLocation() {
        return projectPage.getProjectLocation();
    }

    @Override
    protected IWorkingSet[] getProjectWorkingSets() {
        return projectPage.getProjectWorkingSets();
    }

    @Override
    protected IWizardPage getLastPage() {
        return androidInformationPage;
    }

    @Override
    protected Map<String, String> getInputData() {
        final Map<String, String> inputData;
        inputData = new HashMap<String, String>();
        inputData.put("__DB_NAME__", projectPage.getProjectName().replaceAll("\\W+", "") + "DB");
        inputData.put("__PROJECT_NAME__", projectPage.getProjectName());
        inputData.put("__MANIFEST_VERSION_CODE__", androidInformationPage.getProjectVersionCode());
        inputData.put("__MANIFEST_VERSION__", androidInformationPage.getProjectVersion());
        inputData.put("__PACKAGE_NAME__", androidInformationPage.getProjectPackage());
        inputData.put("__PACKAGE_DIRECTORY__", androidInformationPage.getProjectPackage().replace(".", File.separator));
        inputData.put("__SERVER_URL__", androidInformationPage.getServerUrl());
        inputData.put("__SERVER_PORT__", androidInformationPage.getServerPort());
        inputData.put("__SERVER_APP_NAME__", androidInformationPage.getServerAppName());
        inputData.put("__SERVER_APP_VERSION__", androidInformationPage.getServerAppVersion());
        inputData.put("__ANDROID_TARGET__", projectPage.getAndroidTargetHash());
        return inputData;
    }
}
