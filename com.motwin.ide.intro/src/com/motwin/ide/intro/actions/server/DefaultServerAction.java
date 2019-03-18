/**
 * 
 */
package com.motwin.ide.intro.actions.server;

import java.util.Properties;

import org.eclipse.ui.intro.IIntroSite;

import com.motwin.ide.intro.actions.IDEIntroAction;

/**
 * @author ctranxuan
 * 
 */
public class DefaultServerAction extends IDEIntroAction {

    public DefaultServerAction() {
        super();
    }

    @Override
    protected String getDefaultPerspectiveID() {
        return "com.springsource.sts.ide.perspective"; //$NON-NLS-N$
    }

    @Override
    protected void runAction(final IIntroSite aSite, final Properties aParams) {
    }

}
