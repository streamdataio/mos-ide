/**
 * 
 */
package com.motwin.ide.intro.actions.html5;

import java.util.Properties;

import org.eclipse.ui.intro.IIntroSite;

import com.motwin.ide.intro.actions.IDEIntroAction;

/**
 * @author ctranxuan
 * 
 */
public class DefaultHTML5Action extends IDEIntroAction {

    public DefaultHTML5Action() {
        super();
    }

    @Override
    protected void runAction(final IIntroSite aSite, final Properties aParams) {
    }

    @Override
    protected String getDefaultPerspectiveID() {
        return "org.eclipse.wst.web.ui.webDevPerspective";
    }

}
