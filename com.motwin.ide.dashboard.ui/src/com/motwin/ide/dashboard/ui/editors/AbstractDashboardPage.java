/**
 * 
 */
package com.motwin.ide.dashboard.ui.editors;

import java.util.Collection;
import java.util.Comparator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import com.motwin.ide.dashboard.ui.editors.PartDescriptor.PositionPartDescriptorComparator;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractDashboardPage extends FormPage {
    private static final String              ELEMENT_PART = "part";

    private Multimap<String, PartDescriptor> partDescriptors;

    private final String                     extensionPointID;

    protected AbstractDashboardPage(final String aDashboardExtPointID, final FormEditor aEditor, final String aId,
            final String aTitle) {
        // FIXME could reduce the construct arg nber with a DashboardPageInfo
        // object
        super(aEditor, aId, aTitle);
        extensionPointID = aDashboardExtPointID;
    }

    protected Collection<AbstractDashboardPart> contributeParts(final Composite aParent, final String aLocation) {
        if (partDescriptors == null) {
            readExtensions();
        }

        Builder<AbstractDashboardPart> dashboardParts;
        dashboardParts = ImmutableList.builder();

        Collection<PartDescriptor> descriptors;
        descriptors = partDescriptors.get(aLocation);

        if (descriptors != null) {
            for (PartDescriptor partDescriptor : descriptors) {
                AbstractDashboardPart part;
                part = partDescriptor.createPart();

                dashboardParts.add(part);
            }
        }

        return dashboardParts.build();
    }

    private void readExtensions() {
        partDescriptors = TreeMultimap.create(new Comparator<String>() {
            @Override
            public int compare(final String aString1, final String aString2) {
                return aString1.compareTo(aString2);
            }
        }, new PositionPartDescriptorComparator());

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
            if (isPartExtension(element)) {
                readPartExtension(element);
            }
        }
    }

    private void readPartExtension(final IConfigurationElement anElement) {
        PartDescriptor descriptor;
        descriptor = new PartDescriptor(anElement);

        partDescriptors.put(descriptor.getLocation(), descriptor);
    }

    private boolean isPartExtension(final IConfigurationElement anElement) {
        boolean result;
        result = anElement.getName().equals(ELEMENT_PART);

        return result;
    }

}
