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

import com.motwin.ide.server.templates.MotwinServerTemplatePlugin;
import com.motwin.ide.server.ui.internal.ServerMessages;
import com.motwin.ide.server.ui.wizards.page.BundleInformationWizardPage;
import com.motwin.ide.server.ui.wizards.page.DefaultJavaProjectPage;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractProjectWizard;

/**
 * @author fbou
 * 
 */
public class EmptyProjectWizard extends AbstractProjectWizard {
    private DefaultJavaProjectPage      projectPage;
    private BundleInformationWizardPage bundleInformationPage;

    public EmptyProjectWizard() {
        setWindowTitle(ServerMessages.wizardPlatformProjectLocationPageName);
        setDefaultPageImageDescriptor(Images.motwinMedium);
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        // java page
        projectPage = new DefaultJavaProjectPage();
        addPage(projectPage);

        // bundle page
        bundleInformationPage = new BundleInformationWizardPage(projectPage);
        addPage(bundleInformationPage);

    }

    @Override
    protected String getTemplateBundleId() {
        return MotwinServerTemplatePlugin.PLUGIN_ID;
    }

    @Override
    protected String getTemplateLocation() {
        return "templates/emptyProject";
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
        return bundleInformationPage;
    }

    @Override
    protected Map<String, String> getInputData() {
        String runtimeName;
        runtimeName = bundleInformationPage.getRuntimeName();

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
        inputData.put("__PROJECT_NAME__", projectPage.getProjectName());
        inputData.put("__BUNDLE_NAME__", bundleInformationPage.getBundleName());
        inputData.put("__BUNDLE_VERSION__", bundleInformationPage.getBundleVersion());
        inputData.put("__BUNDLE_SYMBOLIC_NAME__", bundleInformationPage.getBundleSymbolicName());
        inputData.put("__MOTWIN_VERSION__", sdkRange);
        inputData.put("__MOTWIN_TEST_VERSION__", sdkTestRange);
        inputData.put("__SPRING_VERSION__", springRange);
        inputData.put("__RUNTIME_NAME__", bundleInformationPage.getRuntimeName());

        return inputData;
    }

    @Override
    protected void postProcess() {
        final IJavaProject project;
        project = (IJavaProject) getCreatedElement();

        ProjectPostProcessor.postProcess(project);
    }

}
