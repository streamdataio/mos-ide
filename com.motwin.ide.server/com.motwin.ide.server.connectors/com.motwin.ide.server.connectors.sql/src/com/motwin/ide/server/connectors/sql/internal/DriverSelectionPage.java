package com.motwin.ide.server.connectors.sql.internal;

import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.google.common.collect.ImmutableMap;
import com.motwin.ide.server.connectors.sql.SqlConnectorPlugin;
import com.motwin.ide.server.connectors.ui.wizard.AbstractConnectorWizard.BundleCopy;
import com.motwin.ide.server.connectors.ui.wizard.AbstractConnectorWizard.BundleImport;

public class DriverSelectionPage extends WizardPage {
	
	private static final String POSTGRESQL_LABEL = "PostgreSQL JDBC Driver";
	private static final String SQL_SERVER_LABEL = "jTDS JDBC Driver for Microsoft SQL Server";
	
	private static final Map<String, BundleCopy> driverBundles = ImmutableMap.of(
			POSTGRESQL_LABEL, 
				new BundleCopy(SqlConnectorPlugin.PLUGIN_ID, 
						"libraries/postgresql-jdbc-driver.jar", 
						"repository/connectors/postgresql-jdbc-driver.jar"),
			SQL_SERVER_LABEL, 
				new BundleCopy(SqlConnectorPlugin.PLUGIN_ID, 
						"libraries/sqlserver-jdbc-driver.jar", 
						"repository/connectors/jtds-jdbc-driver.jar"));
	
	private static final Map<String, BundleImport> driverImports = ImmutableMap.of(
			POSTGRESQL_LABEL, new BundleImport("org.postgresql", "9"),
			SQL_SERVER_LABEL, new BundleImport("net.sourceforge.jtds", "1.3"));
	
	private static final Map<String, String> driverUrls = ImmutableMap.of(
			POSTGRESQL_LABEL, "jdbc:postgresql://host:port/database",
			SQL_SERVER_LABEL, "jdbc:jtds:sqlserver://host:port/database");
	
	private static final Map<String, String> driverClasses = ImmutableMap.of(
			POSTGRESQL_LABEL, "org.postgresql.Driver",
			SQL_SERVER_LABEL, "net.sourceforge.jtds.jdbc.Driver");
	
	private Combo serverTargetCombo;

	protected DriverSelectionPage() {
		super("Motwin SQL Connector");
		setTitle("Target JDBC driver");
		setDescription("Choose the JDBC driver you need to use.");
	}

	@Override
	public void createControl(Composite aParent) {
		Composite container = new Composite(aParent, SWT.NULL);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createProjectGroupComposite(container);

		setControl(container);
		setPageComplete(false);
		
	}

	/**
	 * @param parent
	 */
	protected void createProjectGroupComposite(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText("JDBC Driver");
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
		
		for(String driverLabel : driverBundles.keySet()) {
			serverTargetCombo.add(driverLabel);
		}
		
		checkPageComplete();
		
	}

	private void checkPageComplete() {
		BundleCopy bundleCopySelected;
		bundleCopySelected = getSelectedBundleCopy();
		
		if(bundleCopySelected == null) {
			setErrorMessage("Choose a JDBC Driver before continue.");
			setPageComplete(false);
			
		} else {
			setErrorMessage(null);
			setPageComplete(true);
			
		}
	}
	
	public BundleCopy getSelectedBundleCopy() {
		return serverTargetCombo.getSelectionIndex() >= 0 ? driverBundles.get(serverTargetCombo.getItem(serverTargetCombo.getSelectionIndex()))  : null;
	}
	
	public BundleImport getSelectedBundleImport() {
		return serverTargetCombo.getSelectionIndex() >= 0 ? driverImports.get(serverTargetCombo.getItem(serverTargetCombo.getSelectionIndex()))  : null;
	}

	public String getSelectedJdbcUrl() {
		return serverTargetCombo.getSelectionIndex() >= 0 ? driverUrls.get(serverTargetCombo.getItem(serverTargetCombo.getSelectionIndex()))  : null;
	}
	
	public String getSelectedJdbcClass() {
		return serverTargetCombo.getSelectionIndex() >= 0 ? driverClasses.get(serverTargetCombo.getItem(serverTargetCombo.getSelectionIndex()))  : null;
	}
	
}
