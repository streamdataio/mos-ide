/**
 * 
 */
package com.motwin.ide.android.cheatsheets.operations;

import org.eclipse.jdt.core.IJavaProject;

import com.motwin.ide.android.cheatsheets.actions.AbstractAndroidProjectAction;
import com.motwin.ide.cheatsheets.operations.OpenFileOperation;

/**
 * @author ctranxuan
 * 
 */
public class OpenMappingPropertiesOperation extends OpenFileOperation {

    public OpenMappingPropertiesOperation(final AbstractAndroidProjectAction anAction, final IJavaProject aProject) {
        super(aProject.getProject(), anAction.getMappingPropertiesPath(), anAction.getPluginID());
    }
}
