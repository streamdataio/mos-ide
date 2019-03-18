/**
 * 
 */
package com.motwin.ide.android.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkingSet;

import com.android.ide.eclipse.adt.internal.editors.manifest.ManifestEditor;
import com.motwin.ide.android.ui.internal.AndroidMessages;
import com.motwin.ide.android.ui.wizards.pages.SampleJavaProjectPage;
import com.motwin.ide.android.ui.wizards.pages.ServerInformationWizardPage;
import com.motwin.ide.ui.wizards.AbstractProjectWizard;

/**
 * @author fbou
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractSampleProjectWizard extends AbstractProjectWizard {

    protected SampleJavaProjectPage       projectPage;
    protected ServerInformationWizardPage serverPage;

    public AbstractSampleProjectWizard() {
        setWindowTitle(AndroidMessages.wizardAndroidSampleImportWindowTitle);
    }

    @Override
    public void addPages() {
        // java page
        projectPage = new SampleJavaProjectPage(getProjectName());
        addPage(projectPage);

        // server page
        serverPage = new ServerInformationWizardPage();
        addPage(serverPage);

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
    protected String getProjectLocation() {
        return projectPage.getProjectLocation();
    }

    @Override
    protected IWorkingSet[] getProjectWorkingSets() {
        return projectPage.getProjectWorkingSets();
    }

    @Override
    protected IWizardPage getLastPage() {
        return serverPage;
    }

    @Override
    protected Map<String, String> getInputData() {
        final Map<String, String> inputData;
        inputData = new HashMap<String, String>();
        inputData.put("__SERVER_URL__", serverPage.getServerUrl());
        inputData.put("__SERVER_PORT__", serverPage.getServerPort());
        inputData.put("__ANDROID_TARGET__", projectPage.getAndroidTargetHash());
        return inputData;
    }

}
