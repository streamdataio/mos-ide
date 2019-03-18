/**
 * 
 */
package com.motwin.ide.ui.commons;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author ctranxuan
 * 
 */
public class CommonsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    public CommonsPreferencePage() {
    }

    @Override
    public void init(final IWorkbench aWorkbench) {
        noDefaultAndApplyButton();
        setDescription("Expand the tree to edit preferences for a specific feature.");
    }

    @Override
    protected Control createContents(final Composite aParent) {
        Composite composite;
        composite = new Composite(aParent, SWT.NONE);
        composite.setLayout(new FillLayout());

        return composite;
    }

}
