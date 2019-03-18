/**
 * 
 */
package com.motwin.ide.dashboard.ui.editors;

import org.eclipse.core.runtime.IConfigurationElement;

abstract class AbstractDescriptor {
    private final IConfigurationElement element;

    static final String                 ATTRIBUTE_ID    = "id";
    static final String                 ATTRIBUTE_CLASS = "class";

    public AbstractDescriptor(final IConfigurationElement anElement) {
        element = anElement;
    }

    public String getId() {
        String id;
        id = element.getAttribute(AbstractDescriptor.ATTRIBUTE_ID);

        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractDescriptor other = (AbstractDescriptor) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    public IConfigurationElement getElement() {
        return element;
    }
}