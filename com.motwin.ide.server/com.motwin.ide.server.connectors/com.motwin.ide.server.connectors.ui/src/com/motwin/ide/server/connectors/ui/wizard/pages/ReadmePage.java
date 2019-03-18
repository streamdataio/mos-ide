package com.motwin.ide.server.connectors.ui.wizard.pages;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.common.io.CharStreams;

public class ReadmePage extends WizardPage {
	
	private String content;
	private Browser browser;
	
	public ReadmePage() {
		super("Motwin Streamdata Connector Readme");
		setTitle("Readme");
		setDescription("Important information about this connector.");
	}
	
	public void setContent(String aContent) {
		content = checkNotNull(aContent);
		if(browser != null) {
			browser.setText(content, true);
		}
	}
	
	public void setContent(URL aContent) {
		setContent(loadContentFromUrl(checkNotNull(aContent)));
	}

	@Override
	public void createControl(Composite aParent) {
		Composite container;
		container = new Composite(aParent, SWT.NONE);
		container.setLayout(new FillLayout());
		
        browser = new Browser(container, SWT.BORDER);
        if(content != null) {
        	browser.setText(content, true);
        }
		
		setControl(container);
		
	}
	
	public static String loadContentFromUrl(URL aUrl) {
		String html;
        InputStream inputStream = null;
		try {
			inputStream = aUrl.openConnection().getInputStream();
			
		    BufferedReader reader;
		    reader = new BufferedReader(new InputStreamReader(inputStream));
			
			html = CharStreams.toString(reader);
			
		} catch (IOException e) {
			html = "Error";
					
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return html;
	}

}
