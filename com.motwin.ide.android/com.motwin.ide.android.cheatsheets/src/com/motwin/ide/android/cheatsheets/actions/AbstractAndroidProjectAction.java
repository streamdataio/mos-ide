/**
 * 
 */
package com.motwin.ide.android.cheatsheets.actions;

import org.eclipse.core.runtime.IPath;

import com.motwin.ide.cheatsheets.actions.AbstractProjectAction;
import com.motwin.ide.cheatsheets.actions.IJavaProjectAction;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractAndroidProjectAction extends AbstractProjectAction implements IJavaProjectAction {
    private static final String SRC_JAVA_FOLDER   = "src";

    private static final String SRC_RES_FOLDER    = "res";

    private static final String SRC_ASSETS_FOLDER = "assets";

    @Override
    public String getSrcJavaFolder() {
        return SRC_JAVA_FOLDER;
    }

    public String getResFolder() {
        return SRC_RES_FOLDER;
    }

    public String getAssetsFolder() {
        return SRC_ASSETS_FOLDER;
    }

    public String getMappingPropertiesPath() {
        return getAssetsFolder() + IPath.SEPARATOR + "mapping.properties";
    }
}
