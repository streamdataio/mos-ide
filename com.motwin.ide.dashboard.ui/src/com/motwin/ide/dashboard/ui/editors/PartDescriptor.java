/**
 * 
 */
package com.motwin.ide.dashboard.ui.editors;

import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.common.collect.Lists;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;

final class PartDescriptor extends AbstractDescriptor {

    static final class PositionPartDescriptorComparator implements Comparator<PartDescriptor> {

        @Override
        public int compare(final PartDescriptor aDescriptor1, final PartDescriptor aDescriptor2) {
            int position1;
            position1 = aDescriptor1.getPosition();

            int position2;
            position2 = aDescriptor2.getPosition();

            int compare;
            compare = position1 - position2;

            if (compare == 0) {
                String id1;
                id1 = aDescriptor1.getId();

                String id2;
                id2 = aDescriptor2.getId();

                compare = id1.compareTo(id2);
            }

            return compare;
        }

    }

    private final List<UALinkDescriptor> linkDescriptors;
    static final String ELEMENT_UALINK = "uaLink";
    static final String ATTRIBUTE_POSITION  = "position";
    static final String ATTRIBUTE_LOCATION  = "location";

    public PartDescriptor(final IConfigurationElement anElement) {
        super(anElement);
        linkDescriptors = Lists.newArrayList();
    }

    public void addUALinkDescriptor(final UALinkDescriptor aDescriptor) {
        linkDescriptors.add(aDescriptor);
    }

    public AbstractDashboardPart createPart() {
        AbstractDashboardPart part;
        part = null;

        try {

            Object object;
            object = getElement().createExecutableExtension(AbstractDescriptor.ATTRIBUTE_CLASS);

            if (object instanceof AbstractDashboardPart) {
                part = (AbstractDashboardPart) object;
                part.setId(getId());

            } else {
                StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "Cannot load "
                    + object.getClass().getCanonicalName() + " must implement "
                    + AbstractDashboardPart.class.getCanonicalName()));
            }

        } catch (CoreException e) {
            StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
                    "Cannot read the dashboard extension part descriptor [id=" + getId() + "]", e));
        }

        return part;
    }

    public String getLocation() {
        return getElement().getAttribute(PartDescriptor.ATTRIBUTE_LOCATION);
    }

    public int getPosition() {
        int position;
        position = Integer.MAX_VALUE;

        try {
            IConfigurationElement element;
            element = getElement();

            String positionAttr;
            positionAttr = element.getAttribute(PartDescriptor.ATTRIBUTE_POSITION);

            if (positionAttr != null) {
                position = Integer.parseInt(positionAttr);
            }

        } catch (NumberFormatException e) {
            StatusHandler.log(new Status(IStatus.WARNING, DashboardPlugin.PLUGIN_ID,
                    "Invalid position attribute for the dashboard extension part descriptor [id=" + getId() + "]", e));

        }
        return position;
    }
}