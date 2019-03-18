/**
 * 
 */
package com.motwin.ide.server.cheatsheets.samples.contextaware.actions;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.virgo.ide.ui.editors.BundleOverviewPage;

import com.motwin.ide.server.cheatsheets.operations.OpenManifestOperation;

/**
 * @author ctranxuan
 * 
 */
public class OpenOverviewManifestEditorAction extends AbstractContextAwareAction implements ICheatSheetAction {

    @Override
    protected void perform(final IJavaProject aJavaProject) {
        new OpenManifestOperation(aJavaProject, BundleOverviewPage.PAGE_ID, getPluginID()).run();
    }

}
