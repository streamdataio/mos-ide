/**
 * 
 */
package com.motwin.ide.intro.actions.android;

import java.util.Properties;

import org.eclipse.ui.intro.IIntroSite;

import com.motwin.ide.intro.actions.IDEIntroAction;

/**
 * @author ctranxuan
 * 
 */
public class DefaultAndroidAction extends IDEIntroAction {

    public DefaultAndroidAction() {
        super();
    }

    @Override
    protected void runAction(final IIntroSite aSite, final Properties aParams) {

    }

    @Override
    protected String getDefaultPerspectiveID() {
        return "org.eclipse.jdt.ui.JavaPerspective"; //$NON-NLS-N$
    }

}
