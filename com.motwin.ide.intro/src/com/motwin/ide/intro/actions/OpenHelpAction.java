/**
 * 
 */
package com.motwin.ide.intro.actions;

import java.util.Properties;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroSite;

/**
 * @author ctranxuan
 * 
 */
public class OpenHelpAction extends IDEIntroAction {

    @Override
    protected void runAction(final IIntroSite aSite, final Properties aParams) {
        PlatformUI.getWorkbench().getHelpSystem()
                .displayHelpResource("/com.motwin.ide.docs/docs/html/topics/maintopic.html");
    }

    @Override
    protected String getDefaultPerspectiveID() {
        return null;
    }

    @Override
    protected void afterRunAction() {
        // do nothing
    }

    @Override
    protected void beforeRunAction() {
        // do nothing
    }

}
