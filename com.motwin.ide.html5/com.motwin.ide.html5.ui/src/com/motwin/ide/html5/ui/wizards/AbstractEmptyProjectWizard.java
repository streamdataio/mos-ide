/**
 * 
 */
package com.motwin.ide.html5.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkingSet;

import com.motwin.ide.html5.ui.wizards.pages.DefaultJavaProjectPage;
import com.motwin.ide.html5.ui.wizards.pages.HTML5InformationWizardPage;
import com.motwin.ide.ui.wizards.AbstractProjectWizard;

/**
 * @author fbou
 * 
 */
public abstract class AbstractEmptyProjectWizard extends AbstractProjectWizard {

    private DefaultJavaProjectPage     projectPage;
    private HTML5InformationWizardPage serverPage;

    public AbstractEmptyProjectWizard() {
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        // java page
        projectPage = new DefaultJavaProjectPage();
        addPage(projectPage);

        // bundle page
        serverPage = new HTML5InformationWizardPage(projectPage);
        addPage(serverPage);

    }

    @Override
    protected String getWelcomeFileLocation() {
        return "www/index.html";
    }

    @Override
    protected String getWelcomeFileEditor() {
        return "org.eclipse.wst.html.core.htmlsource.source";
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
        return serverPage;
    }

    @Override
    protected String getWelcomePerspective() {
        return "org.eclipse.wst.web.ui.webDevPerspective";
    }

    @Override
    protected Map<String, String> getInputData() {
        final Map<String, String> inputData;
        inputData = new HashMap<String, String>();
        inputData.put("__PROJECT_NAME__", projectPage.getProjectName());
        inputData.put(
                "__SERVER_URL__",
                String.format("%s://%s:%s", serverPage.getServerProtocol(), serverPage.getServerUrl(),
                        serverPage.getServerPort()));
        inputData.put("__SERVER_APP_NAME__", serverPage.getServerAppName());
        inputData.put("__SERVER_APP_VERSION__", serverPage.getServerAppVersion());
        return inputData;
    }

}
