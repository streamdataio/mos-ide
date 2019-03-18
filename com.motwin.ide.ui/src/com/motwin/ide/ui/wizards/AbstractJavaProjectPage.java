/**
 * 
 */
package com.motwin.ide.ui.wizards;


import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.workingsets.IWorkingSetIDs;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.dialogs.WorkingSetConfigurationBlock;

import com.motwin.ide.ui.Messages;

/**
 * @author fbou
 *
 */
@SuppressWarnings("restriction")
public class AbstractJavaProjectPage extends WizardPage {

	protected Text projectNameField;
	protected Button projectDefaultLocation;
	protected Text projectLocationField;
	protected WorkingSetConfigurationBlock workingSetBlock;

	protected final ModifyListener checkPageCompleteListener;

	/**
	 * @param aPageName
	 */
	protected AbstractJavaProjectPage(String aPageName) {
		super(aPageName);
		checkPageCompleteListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent aE) {
				checkPageComplete();
			}
		};

	}

	@Override
	public void createControl(Composite aParent) {
		Composite container = new Composite(aParent, SWT.NULL);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createProjectNameGroup(container);
		createLocationGroup(container);
		createWorkingSetGroup(container);

		setControl(container);
		setPageComplete(false);

	}

	/**
	 * @param container
	 */
	protected void createProjectNameGroup(Composite aContainer) {
		Composite projectNameGroup = new Composite(aContainer, SWT.NULL);
		projectNameGroup.setLayout(new GridLayout(2, false));
		projectNameGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label projectNameLabel = new Label(projectNameGroup, SWT.NULL);
		projectNameLabel.setText(Messages.wizardPlatformProjectProjectNameLabel);

		projectNameField = new Text(projectNameGroup, SWT.BORDER);
		projectNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		projectNameField.addModifyListener(checkPageCompleteListener);

	}

	/**
	 * @param aParent
	 */
	protected void createLocationGroup(Composite aParent) {
		Group locationGroup = new Group(aParent, SWT.NONE);
		locationGroup.setFont(aParent.getFont());
		locationGroup.setText(Messages.wizardPlatformProjectLocationGroup);
		locationGroup.setLayout(new GridLayout(3, false));
		locationGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		projectDefaultLocation = new Button(locationGroup, SWT.CHECK);
		projectDefaultLocation.setText(Messages.wizardPlatformProjectLocationUseDefault);
		projectDefaultLocation.setSelection(true);
		projectDefaultLocation.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 3, 1));

		Label projectLocationLabel = new Label(locationGroup, SWT.NONE);
		projectLocationLabel.setText("Location:");

		projectLocationField = new Text(locationGroup, SWT.BORDER);
		projectLocationField.setEnabled(false);
		projectLocationField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		projectLocationField.addModifyListener(checkPageCompleteListener);

		final Button projectLocationBrowse = new Button(locationGroup, SWT.NONE);
		projectLocationBrowse.setEnabled(false);
		projectLocationBrowse.setText(Messages.wizardPlatformProjectLocationBrowse);
		projectLocationBrowse.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent aSelectionEvent) {
				showDirectoryDialog();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent aSelectionEvent) {
				widgetSelected(aSelectionEvent);
			}
		});

		projectDefaultLocation.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent aSelectionEvent) {
				projectLocationField.setEnabled(!projectDefaultLocation.getSelection());
				projectLocationBrowse.setEnabled(!projectDefaultLocation.getSelection());
				checkPageComplete();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent aSelectionEvent) {
				widgetSelected(aSelectionEvent);
			}
		});

	}

	/**
	 * @param aParent
	 */
	protected void createWorkingSetGroup(Composite aParent) {
		Group workingSetGroup= new Group(aParent, SWT.NONE);
		workingSetGroup.setFont(aParent.getFont());
		workingSetGroup.setText(Messages.wizardPlatformProjectWorkingSetsGroup);
		workingSetGroup.setLayout(new GridLayout(1, false));
		workingSetGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		String[] workingSetIds = new String[] { IWorkingSetIDs.JAVA, IWorkingSetIDs.RESOURCE };
		workingSetBlock = new WorkingSetConfigurationBlock(workingSetIds, JavaPlugin.getDefault().getDialogSettings());
		workingSetBlock.createContent(workingSetGroup);

	}

	private void showDirectoryDialog() {
		final DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setMessage("Chose a directory for the project content.");
		String directoryName = projectLocationField.getText().trim();
		if (directoryName.length() > 0) {
			final File path = new File(directoryName);
			if (path.exists())
				dialog.setFilterPath(directoryName);
		}
		final String selectedDirectory = dialog.open();
		if (selectedDirectory != null) {
			String oldDirectory= new Path(projectLocationField.getText().trim()).lastSegment();
			projectLocationField.setText(selectedDirectory);
			String lastSegment= new Path(selectedDirectory).lastSegment();
			if (lastSegment != null && (projectNameField.getText().length() == 0 || projectNameField.getText().equals(oldDirectory))) {
				projectNameField.setText(lastSegment);
			}
		}
	}

	/**
	 * Check if the page is completed
	 */
	private void checkPageComplete() {
		setPageComplete(validate());
	}

	/**
	 * @return
	 */
	private boolean validate() {
		// check project name not empty
		if(getProjectName() == null || getProjectName().isEmpty()) {
			setErrorMessage("Project name cannot be empty.");
			return false;
		}

		// check selected location is valid
		if(!projectDefaultLocation.getSelection()) {
			// check project location is not empty
			if(getProjectLocation() == null || getProjectLocation().isEmpty()) {
				setErrorMessage("Project location cannot be empty.");
				return false;
			}

			// check project location is not exist
			File locationFile = new File(getProjectLocation());
			if(locationFile.exists() && locationFile.listFiles().length > 0) {
				setErrorMessage("Project location is not empty.");
				return false;
			}

		} else {
			// check project name not exists
			IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
			if(projectHandle.exists()) {
				setErrorMessage("Project name already exists.");
				return false;
			}

		}

		setErrorMessage(null);
		return true;
	}

	/**
	 * Get the project name
	 * @return
	 */
	public String getProjectName() {
		return projectNameField.getText();
	}

	/**
	 * get the project location
	 * @return
	 */
	public String getProjectLocation() {
		return projectDefaultLocation.getSelection() ? null : projectLocationField.getText();
	}

	/**
	 * Get the working sets
	 * @return
	 */
	public IWorkingSet[] getProjectWorkingSets() {
		return workingSetBlock.getSelectedWorkingSets();
	}

}
