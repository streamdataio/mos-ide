package com.motwin.ide.server.connectors.ui.wizard;

import java.net.MalformedURLException;
import java.net.URL;

import com.motwin.ide.server.connectors.ui.wizard.pages.ReadmePage;

/**
 * 
 * @author fbou
 */
public abstract class AbstractReadmeConnectorWizard extends AbstractConnectorWizard {

	@Override
	public void addPages() {
		try {
			URL url;
			url = new URL("platform:/plugin/" + getReadmeLocationPluginId() + "/" + getReadmeLocationPath());
			
			ReadmePage readmePage;
			readmePage = new ReadmePage();
			readmePage.setContent(url);
			
			addPage(readmePage);
		
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
			
		}
	}

	protected abstract String getReadmeLocationPath();

	protected abstract String getReadmeLocationPluginId();

}
