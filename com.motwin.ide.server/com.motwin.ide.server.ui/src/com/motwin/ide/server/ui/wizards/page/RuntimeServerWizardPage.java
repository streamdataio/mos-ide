/**
 * 
 */
package com.motwin.ide.server.ui.wizards.page;

import java.util.List;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.virgo.ide.facet.core.FacetCorePlugin;
import org.eclipse.virgo.ide.runtime.core.ServerCorePlugin;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntimeComponent;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntimeComponentType;
import org.eclipse.wst.common.project.facet.core.runtime.RuntimeManager;
import org.eclipse.wst.server.ui.ServerUIUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.motwin.ide.server.ui.internal.ServerMessages;
import com.motwin.ide.ui.Images;

/**
 * @author fbou
 */
public class RuntimeServerWizardPage extends WizardPage {

	protected final ModifyListener checkPageCompleteListener;

	private Combo serverTargetCombo;

	private boolean initialized = false;

	/**
	 * Default constructor
	 * @param aJavaProjectPage
	 */
	public RuntimeServerWizardPage() {
		this(ServerMessages.wizardPlatformProjectRuntimeServerPage);
		setTitle(ServerMessages.wizardPlatformProjectRuntimeServerPage);
		setDescription(ServerMessages.wizardPlatformProjectRuntimeServerDescription);
		setImageDescriptor(Images.motwinMedium);
	}

	/**
	 * Custom constructor
	 * @param aPageName
	 * @param aJavaProjectPage
	 */
	protected RuntimeServerWizardPage(String aPageName) {
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

		createServerTargetComposite(container);

		setControl(container);
		setPageComplete(false);
	}

	/**
	 * @param parent
	 */
	protected void createServerTargetComposite(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(ServerMessages.wizardPlatformProjectTargetRuntime);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(2, false));
		serverTargetCombo = new Combo(group, SWT.BORDER | SWT.READ_ONLY);
		serverTargetCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverTargetCombo.addModifyListener(checkPageCompleteListener);
		Button newServerTargetButton = new Button(group, SWT.NONE);
		newServerTargetButton.setText(ServerMessages.wizardPlatformProjectTargetRuntimeNew);
		newServerTargetButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean done = ServerUIUtil.showNewRuntimeWizard(getContainer().getShell(), FacetCorePlugin.BUNDLE_FACET_ID, null);
				if(done) {
					updateRuntimeCombo();
				}
			}
		});
	}

	/**
	 * Udate the server list with the available virgo runtimes
	 */
	private void updateRuntimeCombo() {
		Set<IRuntime> runtimes = RuntimeManager.getRuntimes();
		Set<String> previousRuntimeList = Sets.newHashSet(serverTargetCombo.getItems());
		serverTargetCombo.removeAll();
		for(IRuntime runtime : runtimes) {
			for(IRuntimeComponent component : runtime.getRuntimeComponents()) {
				IRuntimeComponentType type = component.getRuntimeComponentType();
				if(type.getId().equals(ServerCorePlugin.VIRGO_SERVER_ID)) {
					serverTargetCombo.add(runtime.getName());
				}
			}
		}
		List<String> newRuntimeList = Lists.newArrayList(serverTargetCombo.getItems());
		newRuntimeList.removeAll(previousRuntimeList);
		if(newRuntimeList.size() == 0 && serverTargetCombo.getVisibleItemCount() != 0) {
			// select the first server if there are not new server avaliable and if there are more than 0 server in the combo
			serverTargetCombo.select(0);
		} else if(!newRuntimeList.isEmpty()) {
			// select the first new server available
			int index = serverTargetCombo.indexOf(newRuntimeList.get(0));
			if(index >= 0) {
				serverTargetCombo.select(index);
			}
		}
	}

	private void checkPageComplete() {
		setPageComplete(validate());
	}

	@Override
	public void setVisible(boolean aVisible) {
		super.setVisible(aVisible);
		if(aVisible && !initialized) {
			updateRuntimeCombo();
			initialized = true;
		}
	}

	public String getRuntimeName() {
		return serverTargetCombo.getSelectionIndex() >= 0 ? serverTargetCombo.getItem(serverTargetCombo.getSelectionIndex()) : null;
	}

	protected boolean validate() {
		// check runtime name not empty
		if(getRuntimeName() == null) {
			setErrorMessage("Target Server: Select or create a target server runtime to continue.");
			return false;
		}

		setErrorMessage(null);
		return true;
	}

}
