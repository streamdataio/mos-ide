package com.motwin.ide.server.connectors.soap.internal;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Map;

import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.wsdlto.WSDLToJava;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.virgo.ide.bundlerepository.domain.BundleImport;
import org.eclipse.virgo.ide.manifest.core.BundleManifestUtils;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IRuntime;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.IServerWorkingCopy;
import org.eclipse.wst.server.core.ServerCore;
import org.eclipse.wst.server.core.ServerUtil;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.motwin.ide.core.templates.TemplateProcessor;
import com.motwin.ide.core.utils.CoreUtil;
import com.motwin.ide.server.connectors.soap.SoapConnectorPlugin;
import com.motwin.ide.server.connectors.soap.internal.pages.BundleInformationWizardPage;
import com.motwin.ide.server.connectors.soap.internal.pages.WSDLLocationWizardPage;
import com.motwin.ide.server.connectors.ui.ConnectorUtils;
import com.motwin.ide.server.connectors.ui.wizard.AbstractReadmeConnectorWizard;
import com.motwin.ide.server.ui.wizards.page.DefaultJavaProjectPage;

/**
 * 
 * @author fbou
 */
public class SoapConnectorWizard extends AbstractReadmeConnectorWizard {
	
	private static final String CXF_VERSION = "2.7.8";
	private static final String CXF_RANGE = "[2.7.0,2.8.0)";
	private static final String CXF_TEMPLATE_LOCATION = "cxf-project-template";
	private static final String CXF_TEMPLATE_SRC_DIRECTORY = "/src";
	
	private WSDLLocationWizardPage              wsdlConfigurationPage;
	private DefaultJavaProjectPage      javaProjectPage;
	private BundleInformationWizardPage bundleInformationPage;
	
	private IRuntime                    runtime;
	private Map<String, String>         generatedProjectInputData;
	private String                      generatedProjectName;
	private String                      generatedProjectLocation;
	private String                      wsdlLocation;
	private String                      generatedProjectBundleSymbolicName;
	private String                      generatedProjectBundleVersion;
	private IWorkingSet[]               generatedProjectWorkingSets;
	
	@Override
	public void addPages() {
		wsdlConfigurationPage = new WSDLLocationWizardPage();
		javaProjectPage = new DefaultJavaProjectPage();
		bundleInformationPage = new BundleInformationWizardPage(javaProjectPage);
		
		javaProjectPage.setTitle("Generated Project Information");
		javaProjectPage.setDescription("Enter information about the project that will be generated from the provided WSDL file.");
		
		addPage(wsdlConfigurationPage);
		addPage(javaProjectPage);
		addPage(bundleInformationPage);
		
		super.addPages();
	}
	
	@Override
	protected void doFinishSynchonous() throws Exception {
		runtime = ConnectorUtils.getFirstVirgoRuntime(project);
		checkState(runtime != null, "runtime cannot be null");
		
		generatedProjectInputData = ImmutableMap.<String,String>builder()
        		.put("__BUNDLE_NAME__", bundleInformationPage.getBundleName())
        		.put("__BUNDLE_SYMBOLIC_NAME__", bundleInformationPage.getBundleSymbolicName())
        		.put("__BUNDLE_VERSION__", bundleInformationPage.getBundleVersion())
        		.put("__PROJECT_NAME__", javaProjectPage.getProjectName())
        		.put("__RUNTIME_NAME__", runtime.getName())
        		.build();
		
		generatedProjectName = javaProjectPage.getProjectName();
		generatedProjectLocation = javaProjectPage.getProjectLocation();
		wsdlLocation = wsdlConfigurationPage.getWSDLLocation();
		generatedProjectBundleSymbolicName = bundleInformationPage.getBundleSymbolicName();
		generatedProjectBundleVersion = bundleInformationPage.getBundleVersion();
		generatedProjectWorkingSets = javaProjectPage.getProjectWorkingSets();
		
	}
	
	@Override
	protected void doFinishAsynchronous(IProgressMonitor aProgressMonitor) throws Exception {
		aProgressMonitor.subTask("Creating Project...");
		
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProjectDescription projectDescription = workspace.newProjectDescription(generatedProjectName);
        File projectDirectory;

        if (generatedProjectLocation == null) {
            projectDescription.setLocation(null);
            projectDirectory = new File(workspace.getRoot().getLocationURI().getPath() + File.separator
                + generatedProjectName);

        } else {
            projectDescription.setLocation(Path.fromOSString(generatedProjectLocation));
            projectDirectory = new File(generatedProjectLocation);

        }
        
		aProgressMonitor.worked(10);
		aProgressMonitor.subTask("Generating Java classes from WSDL...");

        // locate template files
        File templateDirectory = CoreUtil.locateResource(SoapConnectorPlugin.PLUGIN_ID, CXF_TEMPLATE_LOCATION);
        Preconditions.checkNotNull(templateDirectory, "Project template not found at '%s'.", CXF_TEMPLATE_LOCATION);

        // process the template files
        projectDirectory.mkdirs();
        TemplateProcessor processor = new TemplateProcessor(generatedProjectInputData);
        processor.process(templateDirectory, projectDirectory);
        
        // Generate CXF services
		WSDLToJava converter;
		converter = new WSDLToJava(new String[] {"-client", "-d", projectDirectory.getAbsolutePath() + CXF_TEMPLATE_SRC_DIRECTORY, wsdlLocation});
		converter.run(new ToolContext()); 
		
		aProgressMonitor.worked(5);
		aProgressMonitor.subTask("Opening generated project...");

        // opening project
		IProject project;
        project = workspace.getRoot().getProject(generatedProjectName);
        project.create(projectDescription, new NullProgressMonitor());
        project.open(new NullProgressMonitor());
        
        // open as a java project
        IJavaProject javaProject;
        javaProject = JavaCore.create(project);
        
		aProgressMonitor.worked(10);
		aProgressMonitor.subTask("Updating project MANIFEST.MF...");
        
        // locate manifest.mf
		BundleManifest manifest;
		manifest = BundleManifestUtils.getBundleManifest(javaProject, false);
        
		// add exported packages to the manifest.mf
        for(IPackageFragment  packageFragment : javaProject.getPackageFragments()) {
        	if(packageFragment.containsJavaResources() && packageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
        		manifest.getExportPackage().addExportedPackage(packageFragment.getElementName());
        	}
        }
        
        // save the new version of manifest.mf
		IFile manifestFile;
		manifestFile = BundleManifestUtils.locateManifest(javaProject, false);
		
		FileWriter writer;
		writer = new FileWriter(new File(manifestFile.getLocationURI()));
		manifest.write(writer);
		
		aProgressMonitor.worked(10);
		aProgressMonitor.subTask("Adding project to WorkingSets...");
		
        // add to workspaces
        if (generatedProjectWorkingSets.length > 0) {
            getWorkbench().getWorkingSetManager().addToWorkingSets(project, generatedProjectWorkingSets);
        }
        
		aProgressMonitor.worked(5);
		aProgressMonitor.subTask("Refreshing generated project...");
        
        project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
        
        aProgressMonitor.worked(5);
        aProgressMonitor.subTask("Adding project to target server...");
		
		IServer[] servers = ServerCore.getServers();
		for(IServer server : servers) {
			if(server.getRuntime() == runtime) {
				IServerWorkingCopy serverCopy;
				serverCopy = server.createWorkingCopy();
				
				serverCopy.modifyModules(ServerUtil.getModules(project), new IModule[] {}, new NullProgressMonitor());
				serverCopy.save(true, new NullProgressMonitor());
			}
		}
		
		aProgressMonitor.worked(5);
		
		super.doFinishAsynchronous(new SubProgressMonitor(aProgressMonitor, 50));
		
	}

	@Override
	protected Collection<BundleCopy> getBundleCopies() {
		return ImmutableList.of(
				new BundleCopy(SoapConnectorPlugin.PLUGIN_ID, 
						"libraries/cxf-bundle-" + CXF_VERSION + ".jar", 
						"repository/connectors/cxf-bundle-" + CXF_VERSION + ".jar"));
	}

	@Override
	protected Collection<BundleImport> getBundleImports() {
		return ImmutableList.of(
				new BundleImport("org.apache.cxf.bundle-minimal", CXF_RANGE),
				new BundleImport(generatedProjectBundleSymbolicName, generatedProjectBundleVersion));
	}
	
	@Override
	protected String getReadmeLocationPluginId() {
		return SoapConnectorPlugin.PLUGIN_ID;
	}
	
	@Override
	protected String getReadmeLocationPath() {
		return "documents/readme.html";
	}
	
}
