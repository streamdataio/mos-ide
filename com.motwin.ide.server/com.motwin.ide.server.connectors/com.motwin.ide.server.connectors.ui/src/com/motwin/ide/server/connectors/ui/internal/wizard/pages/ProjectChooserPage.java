package com.motwin.ide.server.connectors.ui.internal.wizard.pages;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.virgo.ide.facet.core.FacetCorePlugin;

import com.motwin.ide.server.connectors.ui.ConnectorUtils;

public class ProjectChooserPage extends WizardPage {
	
	private final IProject defaultSelection;
	
	private Combo serverTargetCombo;

	public ProjectChooserPage(IProject aDefaultSelection) {
		super("Motwin");
		setTitle("Choose Project");
		setDescription("Choose a Motwin Project where to import the Connector.");
		defaultSelection = aDefaultSelection;
	}

	@Override
	public void createControl(Composite aParent) {
		Composite container = new Composite(aParent, SWT.NULL);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createProjectGroupComposite(container);

		setControl(container);
		checkPageComplete();
		
	}

	/**
	 * @param parent
	 */
	protected void createProjectGroupComposite(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText("Project");
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(2, false));
		
		serverTargetCombo = new Combo(group, SWT.BORDER | SWT.READ_ONLY);
		serverTargetCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverTargetCombo.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent aEvent) {
				checkPageComplete();
			}
		});
		
		int index = 0;
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for(IProject project : projects) {
			try {
				if(project.hasNature(JavaCore.NATURE_ID) 
						&& project.hasNature(FacetCorePlugin.BUNDLE_NATURE_ID)
						&& ConnectorUtils.hasVirgoRuntime(project)) {
					serverTargetCombo.add(project.getName());
					if(defaultSelection != null && project.getName().equals(defaultSelection.getName())) {
						serverTargetCombo.select(index);
					}
					index++;
				}
			} catch (CoreException e) {
				// ignore
			}
		}
		
		checkPageComplete();
		
	}

	private void checkPageComplete() {
		IProject choosedProject;
		choosedProject = getSelectedProject();
		
		if(choosedProject == null) {
			setErrorMessage("Choose a valid Motwin Project where to import the Connector before continue.");
			setPageComplete(false);
			
		} else {
			setErrorMessage(null);
			setPageComplete(true);
			
		}
	}

	public IProject getSelectedProject() {
		return serverTargetCombo.getSelectionIndex() >= 0 ? ResourcesPlugin.getWorkspace().getRoot().getProject(serverTargetCombo.getItem(serverTargetCombo.getSelectionIndex()))  : null;
	}

}
