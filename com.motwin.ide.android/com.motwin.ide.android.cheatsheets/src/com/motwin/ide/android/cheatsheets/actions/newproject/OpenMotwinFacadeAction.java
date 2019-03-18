/**
 * 
 */
package com.motwin.ide.android.cheatsheets.actions.newproject;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.android.cheatsheets.internal.AndroidCheatSheetPlugin;
import com.motwin.ide.cheatsheets.operations.OpenFileOperation;
import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public final class OpenMotwinFacadeAction extends AbstractNewProjectAndroidAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        String path;
        path = JDTUtil.toPath(getSrcJavaFolder(), getJavaBasePackage()) + IPath.SEPARATOR + "MotwinFacade.java";

        new OpenFileOperation(aJavaProject, path, AndroidCheatSheetPlugin.PLUGIN_ID).run();
    }
}
