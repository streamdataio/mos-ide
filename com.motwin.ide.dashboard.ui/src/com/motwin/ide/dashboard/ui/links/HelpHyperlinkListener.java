/**
 * 
 */
package com.motwin.ide.dashboard.ui.links;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;

import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
public class HelpHyperlinkListener extends HyperlinkAdapter implements IExecutableExtension {
    private String url;

    public HelpHyperlinkListener() {
    }

    public HelpHyperlinkListener(final String aUrl) {
        super();
        url = aUrl;
    }

    @Override
    public void setInitializationData(final IConfigurationElement aConfig, final String aPropertyName,
                                      final Object aData) throws CoreException {
        Preconditions.checkNotNull(aData, "aData cannot be null");
        url = aData.toString();
    }

    @Override
    public void linkActivated(final HyperlinkEvent anEvent) {
        PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(url);
    }

}
