package com.motwin.ide.server.connectors.sql.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.eclipse.jface.wizard.IWizardPage;

import com.google.common.collect.ImmutableList;
import com.motwin.ide.server.connectors.sql.SqlConnectorPlugin;
import com.motwin.ide.server.connectors.ui.wizard.AbstractConnectorWizard;
import com.motwin.ide.server.connectors.ui.wizard.pages.ReadmePage;

public class SqlConnectorWizard extends AbstractConnectorWizard {
	
	private final static String BONECP_VERSION = "0.8";
	
	private DriverSelectionPage driverSelectionPage;
	private ReadmePage readmePage;
	
	private BundleCopy driverBundleCopy;
	private BundleImport driverBundleImport;
	
	@Override
	public void addPages() {
		driverSelectionPage = new DriverSelectionPage();
		addPage(driverSelectionPage);
		
		readmePage = new ReadmePage();
		readmePage.setPageComplete(false);
		addPage(readmePage);
		
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage aPage) {
		if(aPage == driverSelectionPage) {
			try {
				URL url;
				url = new URL("platform:/plugin/" + SqlConnectorPlugin.PLUGIN_ID + "/documents/readme.html");
				
				String jdbcUrl;
				jdbcUrl = driverSelectionPage.getSelectedJdbcUrl();
				
				String jdbcClass;
				jdbcClass = driverSelectionPage.getSelectedJdbcClass();
				
				String content;
				content = ReadmePage.loadContentFromUrl(url);
				content = content.replaceFirst("%DRIVER_CLASS_DEFAULT%", jdbcClass);
				content = content.replaceFirst("%DRIVER_URL_DEFAULT%", jdbcUrl);
				readmePage.setContent(content);
				readmePage.setPageComplete(true);
			
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
				
			}
		}
		return super.getNextPage(aPage);
	}
	
	@Override
	protected void doFinishSynchonous() throws Exception {
		driverBundleCopy = checkNotNull(driverSelectionPage.getSelectedBundleCopy());
		driverBundleImport = checkNotNull(driverSelectionPage.getSelectedBundleImport());
		super.doFinishSynchonous();
	}

	@Override
	protected Collection<BundleCopy> getBundleCopies() {
		return ImmutableList.of(
				driverBundleCopy,
				new BundleCopy(SqlConnectorPlugin.PLUGIN_ID, 
						"libraries/bonecp.jar", 
						"repository/connectors/bonecp.jar"),
				new BundleCopy(SqlConnectorPlugin.PLUGIN_ID, 
						"libraries/bonecp-spring.jar", 
						"repository/connectors/bonecp-spring.jar"));
	}

	@Override
	protected Collection<BundleImport> getBundleImports() {
		return ImmutableList.of(
				driverBundleImport,
				new BundleImport("com.jolbox.bonecp", BONECP_VERSION),
				new BundleImport("com.jolbox.bonecp-spring", BONECP_VERSION));
	}

}
