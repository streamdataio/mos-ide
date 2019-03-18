package com.motwin.ide.server.connectors.ui.internal.wizard.pages;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;

import com.google.common.collect.Lists;
import com.motwin.ide.server.connectors.core.ConnectorPlugin;
import com.motwin.ide.server.connectors.core.wizard.IConnectorWizard;
import com.motwin.ide.server.connectors.core.wizard.IConnectorWizardDescriptor;
import com.motwin.ide.server.connectors.ui.ConnectorUIPlugin;

public class ConnectorImportPage extends WizardSelectionPage {

	private final ProjectChooserPage   projectChooserPage;
	private final IWorkbench           workbench;
	
	private IConnectorWizardDescriptor selectedConnector = null;
	
	public ConnectorImportPage(ProjectChooserPage aProjectChooserPage, IWorkbench aWorkbench) {
		super("Motwin");
		setTitle("Motwin Server Streamdata");
		setDescription("Select the Connector to configure and import to your project.");
		projectChooserPage = checkNotNull(aProjectChooserPage);
		workbench = checkNotNull(aWorkbench);
	}
	
	@Override
	public void createControl(Composite aParent) {
        Composite container = new Composite(aParent, SWT.NULL);
        container.setLayout(new FillLayout());
        //container.setSize(aParent.getSize().x, aParent.getSize().y);
        
        createConenctorList(container);
        setControl(container);
        
	}
	
	private void createConenctorList(Composite aParent) {
		final ScrolledComposite scrollContainer = new ScrolledComposite(aParent, SWT.BORDER | SWT.V_SCROLL);
		scrollContainer.setLayout(new FillLayout());
		scrollContainer.setExpandHorizontal(true);
		scrollContainer.setExpandVertical(true);
		//scrollContainer.setSize(aParent.getSize().x, aParent.getSize().y);
		
		FillLayout listLayout = new FillLayout();
        listLayout.type = SWT.VERTICAL;
        listLayout.spacing = 1;

		final Composite listContainer = new Composite(scrollContainer, SWT.NONE);
		listContainer.setLayout(listLayout);
		scrollContainer.setContent(listContainer);
		scrollContainer.addControlListener(new ControlAdapter() {
		    public void controlResized(ControlEvent e) {
		        Rectangle r = scrollContainer.getClientArea();
		        scrollContainer.setMinSize(listContainer.computeSize(r.width, SWT.DEFAULT));
		    }
		});
		
		try {
			final List<ConnectorElement> elements;
			elements = Lists.newArrayList();
			
			List<IConnectorWizardDescriptor> connectors;
			connectors = ConnectorPlugin.getDefault().getConnectors();
			
			for(final IConnectorWizardDescriptor connector : connectors) {
				ConnectorElement element;
				element = new ConnectorElement(listContainer, new ConnectorElementListener() {
					@Override
					public void connectorElementSelected(ConnectorElement aElement) {
						selectedConnector = connector;
						for(ConnectorElement otherElement : elements) {
							if(otherElement != aElement && otherElement.isSelected()) {
								otherElement.toggleSelected();
							}
						}
					}
				}, connector);
			
				elements.add(element);
				
			}
			
		} catch (Exception ex) {
			throw new RuntimeException("Unable to load extensions", ex);
			
		}
	}
	
	@Override
	public boolean isPageComplete() {
		return selectedConnector != null;
	}
	
	public IConnectorWizardDescriptor getSelectedConnector() {
		return selectedConnector;
	}
	
	private final class ConnectorElement extends Composite implements IWizardNode {
		
		private final IConnectorWizardDescriptor wizardDescriptor;
		private final ConnectorElementListener listener;
		
		private boolean          selected = false;
		private IConnectorWizard wizardInstance = null;
		
		public ConnectorElement(Composite aParent, ConnectorElementListener aListener, IConnectorWizardDescriptor aDescriptor) {
			super(aParent, SWT.NONE);
			
			wizardDescriptor = checkNotNull(aDescriptor);
			listener = checkNotNull(aListener);
			
	        setForeground(getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND));
	        setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
			
			GridLayout rowLayout = new GridLayout();
			rowLayout.numColumns = 3;
			
	        setLayout(rowLayout);
	        makeClickablePart(this);
	        
	        Image iconResource;
			try {
				iconResource = new Image(getDisplay(), wizardDescriptor.getIcon().openStream());
			
			} catch (IOException e) {
				try {
					iconResource = new Image(getDisplay(), ConnectorUIPlugin.getDefault().getBundle().getEntry("icons/default.png").openStream());
				
				} catch (IOException ex) {
					throw new RuntimeException("Unable to load default icon", ex);
				
				}
			}
			
			GridData iconData = new GridData(64, 64);
			iconData.verticalSpan = 2;
	        
	        Label icon = new Label(this, SWT.WRAP);
	        icon.setLayoutData(iconData);
	        icon.setImage(iconResource);
	        makeClickablePart(icon);
	        
	        Label name = new Label(this, SWT.NONE);
	        FontData nameFontData = name.getFont().getFontData()[0];
	        Font font = new Font(name.getDisplay(), new FontData(nameFontData.getName(), nameFontData.getHeight(), SWT.BOLD));
	        name.setFont(font);
	        name.setText(wizardDescriptor.getName());
	        makeClickablePart(name);
	        
	        Label version = new Label(this, SWT.NONE);
	        version.setText(wizardDescriptor.getVersion());
	        makeClickablePart(version);
	        
			GridData descriptionData = new GridData(520, 40); // TODO: Hardcoded 
			descriptionData.horizontalSpan = 2;
	        
	        Label description = new Label(this, SWT.WRAP);
	        description.setLayoutData(descriptionData);
	        description.setText(wizardDescriptor.getDescription());
	        makeClickablePart(description);
	        
		}
		
		public void toggleSelected() {
			selected = !selected;
			if(selected) {
				setSelectedNode(ConnectorElement.this);
				setForeground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
				setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
			} else {
		        setForeground(getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND));
		        setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
			}
		}
		
		public boolean isSelected() {
			return selected;
		}

		private void makeClickablePart(final Control aClickable) {
			aClickable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent aEvent) {
					toggleSelected();
					listener.connectorElementSelected(ConnectorElement.this);
				}
			});
		}
		
		@Override
		public void dispose() {
			if(wizardInstance != null) {
				wizardInstance.dispose();
				wizardInstance = null;
			}			
			super.dispose();
		}

		@Override
		public Point getExtent() {
			return new Point(-1, -1);
		}

		@Override
		public IWizard getWizard() {
			if(wizardInstance == null) {
				wizardInstance = wizardDescriptor.createWizard();
				if(wizardInstance != null) {
					wizardInstance.init(workbench, new StructuredSelection(projectChooserPage.getSelectedProject()));
				}
			}
			return wizardInstance;
		}

		@Override
		public boolean isContentCreated() {
			return wizardInstance != null;
		}
		
	}
	
	private static interface ConnectorElementListener {
		void connectorElementSelected(ConnectorElement aElement);
	}
	
	

}
