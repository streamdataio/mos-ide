/**
 * 
 */
package com.motwin.ide.android.cheatsheets.actions.newproject;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.android.cheatsheets.internal.AndroidCheatSheetPlugin;
import com.motwin.ide.cheatsheets.operations.OpenFileOperation;

/**
 * @author ctranxuan
 * 
 */
public final class OpenConfServerPropertiesAction extends AbstractNewProjectAndroidAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        String path;
        path = getAssetsFolder() + IPath.SEPARATOR + "confServer.properties";

        new OpenFileOperation(aJavaProject, path, AndroidCheatSheetPlugin.PLUGIN_ID).run();
    }

}
