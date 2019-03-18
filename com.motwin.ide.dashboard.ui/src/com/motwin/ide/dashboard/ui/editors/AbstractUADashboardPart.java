/**
 * 
 */
package com.motwin.ide.dashboard.ui.editors;

import java.util.Collection;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.NewWizardAction;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.motwin.ide.dashboard.ui.editors.UALinkDescriptor.Type;

/**
 * "UA" stands for "User Assistance".
 * 
 * @author ctranxuan
 * 
 */
public abstract class AbstractUADashboardPart extends AbstractDashboardPart {

    private final String                     title;
    private final String                     extensionPointID;

    // FIXME have a look to an enum map
    private Multimap<Type, UALinkDescriptor> uaLinkDescriptors;

    protected AbstractUADashboardPart(final String anExensionPointID, final String aTitle) {
        title = aTitle;
        extensionPointID = anExensionPointID;
    }

    @Override
    public Control createPartContent(final Composite aParent) {
        FormToolkit toolkit;
        toolkit = getToolkit();

        boolean isExpanded;
        isExpanded = isExpanded();

        Section section;
        section = toolkit.createSection(aParent, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
        section.setExpanded(isExpanded);
        section.setText(title);
        section.setLayout(new GridLayout());
        section.addExpansionListener(new ExpansionAdapter() {

            @Override
            public void expansionStateChanged(final ExpansionEvent anEvent) {
                boolean state;
                state = anEvent.getState();

                storeExpanded(state);
            }

        });
        GridDataFactory.fillDefaults().grab(true, false).applyTo(section);

        Composite headerComposite;
        headerComposite = toolkit.createComposite(section, SWT.NONE);

        RowLayout rowLayout;
        rowLayout = new RowLayout();
        rowLayout.marginTop = 0;
        rowLayout.marginBottom = 0;
        headerComposite.setLayout(rowLayout);
        headerComposite.setBackground(null);

        ToolBarManager toolBarManager;
        toolBarManager = new ToolBarManager(SWT.FLAT);
        toolBarManager.createControl(headerComposite);
        section.setTextClient(headerComposite);
        toolBarManager.add(new NewWizardAction(PlatformUI.getWorkbench().getActiveWorkbenchWindow()));
        toolBarManager.update(true);

        GridLayout layout;
        layout = new GridLayout(1, false);
        Composite composite;
        composite = toolkit.createComposite(section);
        composite.setLayout(layout);

        readExtensionAndCreateSubParts(composite);
        section.setClient(composite);

        return section;
    }

    protected void createSubPart(final Composite aParent, final String aTitle, final Type aType,
                                 final Integer aColumnNumber) {
        FormToolkit toolkit;
        toolkit = getToolkit();

        String header;
        header = buildSubPartHeader(aTitle);

        FormText formText;
        formText = toolkit.createFormText(aParent, false);
        formText.setText(header, true, false);
        formText.setBackground(toolkit.getColors().getColor(IFormColors.H_GRADIENT_START));
        formText.setColor("header", toolkit.getColors().getColor(IFormColors.TITLE));
        formText.setFont("header", JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));

        Composite composite = toolkit.createComposite(aParent);
        composite.setLayout(new GridLayout(aColumnNumber, false));
        GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);

        createHyperLinks(composite, aType);
    }

    private void createHyperLinks(final Composite aParent, final Type aType) {
        Collection<UALinkDescriptor> descriptors;
        descriptors = uaLinkDescriptors.get(aType);

        for (UALinkDescriptor uaLinkDescriptor : descriptors) {
            createHyperLink(aParent, uaLinkDescriptor);
        }
    }

    public void createHyperLink(final Composite aParent, final UALinkDescriptor aUALinkDescriptor) {
        ImageHyperlink link;
        link = aUALinkDescriptor.createHyperlink(aParent);

        if (link != null) {
            GridDataFactory.fillDefaults().applyTo(link);
        }
    }

    protected String buildSubPartHeader(final String aTitle) {
        // @formatter:off
        String result;
        result = new StringBuilder()
            .append("<form><p><span color=\"header\" font=\"header\">")
            .append(aTitle)
            .append("</span></p></form>")
            .toString();
        
        return result;
     // @formatter:on    
    }

    private void readExtensionAndCreateSubParts(final Composite aComposite) {
        if (uaLinkDescriptors == null) {
            readExtensions();
        }

        createSubParts(aComposite);
    }

    protected void createSubParts(final Composite aComposite) {
        createNewProjectsSubPart(aComposite);
        createSamplesSubPart(aComposite);
        createCheatSheetsSubPart(aComposite);
        createDocsSubPart(aComposite);
    }

    protected void createNewProjectsSubPart(final Composite aParent) {
        createSubPart(aParent, "Create", Type.PROJECT, 1);
    }

    protected void createSamplesSubPart(final Composite aParent) {
        createSubPart(aParent, "Samples", Type.SAMPLE, 2);
    }

    protected void createCheatSheetsSubPart(final Composite aParent) {
        createSubPart(aParent, "Cheatsheets", Type.CHEATSHEET, 2);
    }

    protected void createDocsSubPart(final Composite aParent) {
        createSubPart(aParent, "Documentation", Type.DOC, 2);
    }

    private void readExtensions() {
        uaLinkDescriptors = ArrayListMultimap.create();

        IExtensionRegistry registry;
        registry = Platform.getExtensionRegistry();

        IExtensionPoint extensionPoint;
        extensionPoint = registry.getExtensionPoint(extensionPointID);

        IExtension[] extensions;
        extensions = extensionPoint.getExtensions();

        for (IExtension extension : extensions) {
            readExtension(extension);
        }
    }

    private void readExtension(final IExtension anExtension) {
        IConfigurationElement[] elements;
        elements = anExtension.getConfigurationElements();

        for (IConfigurationElement element : elements) {
            if (isUALinkElement(element)) {
                readUALinkExtension(element);
            }
        }
    }

    private boolean isUALinkElement(final IConfigurationElement anElement) {
        boolean result;
        result = anElement.getName().equals(PartDescriptor.ELEMENT_UALINK) && isThisPartElement(anElement);

        return result;
    }

    private boolean isThisPartElement(final IConfigurationElement anElement) {
        boolean result;
        result = getId().equals(anElement.getAttribute(UALinkDescriptor.ATTRIBUTE_PART));

        return result;
    }

    private void readUALinkExtension(final IConfigurationElement anElement) {
        UALinkDescriptor descriptor;
        descriptor = new UALinkDescriptor(anElement, getToolkit());

        uaLinkDescriptors.put(descriptor.getType(), descriptor);
    }

    public String getTitle() {
        return title;
    }

    protected IPreferenceStore getPreferenceStore() {
        return DashboardPlugin.getDefault().getPreferenceStore();
    }

    private boolean isExpanded() {
        boolean result;
        result = getPreferenceStore().getBoolean(getExpansionPrefId());

        return result;
    }

    private void storeExpanded(final boolean anExpandedState) {
        getPreferenceStore().setValue(getExpansionPrefId(), anExpandedState);
    }

    private String getExpansionPrefId() {
        return getId() + DashboardConstants.DASHBOARD_UA_EXPANSION_SUFFIX;
    }
}
