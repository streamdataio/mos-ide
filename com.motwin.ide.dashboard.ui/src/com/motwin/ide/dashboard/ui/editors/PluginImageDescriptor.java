/**
 * 
 */
package com.motwin.ide.dashboard.ui.editors;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;

/**
 * @author ctranxuan
 * 
 */
final class PluginImageDescriptor {
    private final IConfigurationElement element;

    static final String                 ATTRIBUTE_PLUGIN_ID = "pluginID";
    static final String                 ATTRIBUTE_PATH      = "path";

    public PluginImageDescriptor(final IConfigurationElement anElement) {
        element = anElement;
    }

    public String getPluginID() {
        String pluginID;
        pluginID = element.getAttribute(PluginImageDescriptor.ATTRIBUTE_PLUGIN_ID);

        return pluginID;
    }

    public String getPath() {
        String path;
        path = element.getAttribute(ATTRIBUTE_PATH);

        return path;
    }

    public String getId() {
        return getPluginID() + IPath.SEPARATOR + getPath();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        String id = getId();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        PluginImageDescriptor other = (PluginImageDescriptor) obj;
        String id = getId();
        String otherId = other.getId();
        if (id == null) {
            if (otherId != null) {
                return false;
            }
        } else if (!id.equals(otherId)) {
            return false;
        }
        return true;
    }

}
