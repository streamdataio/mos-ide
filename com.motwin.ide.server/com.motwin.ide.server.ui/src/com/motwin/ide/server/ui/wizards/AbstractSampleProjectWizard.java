/**
 * 
 */
package com.motwin.ide.server.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.virgo.ide.ui.editors.BundleManifestEditor;

import com.motwin.ide.server.ui.internal.ServerMessages;
import com.motwin.ide.server.ui.wizards.page.RuntimeServerWizardPage;
import com.motwin.ide.server.ui.wizards.page.SampleJavaProjectPage;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;
import com.motwin.ide.ui.wizards.AbstractProjectWizard;

/**
 * @author fbou
 * 
 */
public abstract class AbstractSampleProjectWizard extends AbstractProjectWizard {

    private AbstractJavaProjectPage projectPage;
    private RuntimeServerWizardPage serverPage;

    public AbstractSampleProjectWizard() {
        setWindowTitle(ServerMessages.wizardPlatformSampleWindowTitle);
    }

    @Override
    public void addPages() {
        // java page
        projectPage = new SampleJavaProjectPage(getProjectName());
        addPage(projectPage);

        // server page
        serverPage = new RuntimeServerWizardPage();
        addPage(serverPage);

    }

    @Override
    protected String getWelcomeFileLocation() {
        return "src/main/resources/META-INF/MANIFEST.MF";
    }

    @Override
    protected String getWelcomeFileEditor() {
        return BundleManifestEditor.ID_EDITOR;
    }

    @Override
    protected String getWelcomePerspective() {
        return "com.springsource.sts.ide.perspective";
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
        String runtimeName;
        runtimeName = serverPage.getRuntimeName();

        LibraryRangeHelper libraryRangeHelper;
        libraryRangeHelper = new LibraryRangeHelper(runtimeName);

        String sdkRange;
        sdkRange = libraryRangeHelper.getSDKLibRange();

        String sdkTestRange;
        sdkTestRange = libraryRangeHelper.getSDKTestLibRange();

        String springRange;
        springRange = libraryRangeHelper.getSpringLibRange();

        final Map<String, String> inputData;
        inputData = new HashMap<String, String>();

        inputData.put("__RUNTIME_NAME__", runtimeName);
        inputData.put("__MOTWIN_VERSION__", sdkRange);
        inputData.put("__SPRING_VERSION__", springRange);
        inputData.put("__MOTWIN_TEST_VERSION__", sdkTestRange);
        return inputData;
    }

    @Override
    protected void postProcess() {
        final IJavaProject project;
        project = (IJavaProject) getCreatedElement();

        ProjectPostProcessor.postProcess(project);
    }
}
