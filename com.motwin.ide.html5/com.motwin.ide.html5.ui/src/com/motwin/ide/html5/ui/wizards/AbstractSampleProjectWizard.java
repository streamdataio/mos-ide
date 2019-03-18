/**
 * 
 */
package com.motwin.ide.html5.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkingSet;

import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.html5.ui.wizards.pages.SampleJavaProjectPage;
import com.motwin.ide.html5.ui.wizards.pages.ServerInformationWizardPage;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;
import com.motwin.ide.ui.wizards.AbstractProjectWizard;

/**
 * @author fbou
 * 
 */
public abstract class AbstractSampleProjectWizard extends AbstractProjectWizard {

    private AbstractJavaProjectPage     projectPage;
    private ServerInformationWizardPage serverPage;

    public AbstractSampleProjectWizard() {
        setWindowTitle(HTML5Messages.wizardHTML5SampleWindowTitle);
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
        return "www/index.html";
    }

    @Override
    protected String getWelcomeFileEditor() {
        return "org.eclipse.ui.browser.editor";
    }

    @Override
    protected String getWelcomePerspective() {
        return "org.eclipse.wst.web.ui.webDevPerspective";
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
        inputData.put(
                "__SERVER_URL__",
                String.format("%s://%s:%s", serverPage.getServerProtocol(), serverPage.getServerUrl(),
                        serverPage.getServerPort()));
        return inputData;
    }

}
