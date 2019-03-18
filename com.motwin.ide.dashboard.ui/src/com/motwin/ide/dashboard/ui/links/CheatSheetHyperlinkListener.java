/**
 * 
 */
package com.motwin.ide.dashboard.ui.links;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.ui.cheatsheets.OpenCheatSheetAction;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;

import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
// FIXME move into a non internal package!
public final class CheatSheetHyperlinkListener extends HyperlinkAdapter implements IExecutableExtension {
    private String cheatSheetId;

    public CheatSheetHyperlinkListener() {
    }

    /**
     * @param aCheatSheetId
     */
    public CheatSheetHyperlinkListener(final String aCheatSheetId) {
        super();
        cheatSheetId = aCheatSheetId;
    }

    @Override
    public void linkActivated(final HyperlinkEvent anEvent) {
        new OpenCheatSheetAction(cheatSheetId).run();
    }

    @Override
    public void setInitializationData(final IConfigurationElement aConfig, final String aPropertyName,
                                      final Object aData) throws CoreException {
        Preconditions.checkNotNull(aData, "aData cannot be null");
        cheatSheetId = aData.toString();
    }

}
