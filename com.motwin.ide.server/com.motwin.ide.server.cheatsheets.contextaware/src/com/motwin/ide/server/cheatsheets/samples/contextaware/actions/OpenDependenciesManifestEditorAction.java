/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.virgo.ide.ui.editors.BundleDependenciesPage;

import com.motwin.ide.server.cheatsheets.operations.OpenManifestOperation;

/**
 * @author ctranxuan
 * 
 */
public class OpenDependenciesManifestEditorAction extends AbstractContextAwareAction implements ICheatSheetAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new OpenManifestOperation(aJavaProject, BundleDependenciesPage.PAGE_ID, getPluginID()).run();
    }

}
