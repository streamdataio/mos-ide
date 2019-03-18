/**
 * 
 */
package com.motwin.ide.android.ui.wizards.pages;

import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Preconditions;
import com.motwin.ide.android.ui.internal.AndroidMessages;

/**
 * @author fbou
 * 
 */
public class SampleJavaProjectPage extends DefaultJavaProjectPage {

    private final String defaultProjectName;

    /**
     * @param aDefaultProjectName
     */
    public SampleJavaProjectPage(final String aDefaultProjectName) {
        super(AndroidMessages.wizardAndroidSamplePageName, AndroidMessages.wizardAndroidSamplePageName,
                AndroidMessages.wizardAndroidSamplePageDescription);
        defaultProjectName = Preconditions.checkNotNull(aDefaultProjectName);
    }

    @Override
    public void createControl(final Composite aParent) {
        super.createControl(aParent);
        projectNameField.setText(defaultProjectName);
        projectNameField.setEnabled(false);
        projectNameField.setEditable(false);
        checkPageComplete();
    }
}
