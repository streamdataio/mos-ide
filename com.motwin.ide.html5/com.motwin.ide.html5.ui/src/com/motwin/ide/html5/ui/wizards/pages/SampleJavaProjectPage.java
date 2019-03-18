/**
 * 
 */
package com.motwin.ide.html5.ui.wizards.pages;

import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Preconditions;
import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou
 * 
 */
public class SampleJavaProjectPage extends AbstractJavaProjectPage {

    private final String defaultProjectName;

    /**
     * @param aDefaultProjectName
     */
    public SampleJavaProjectPage(String aDefaultProjectName) {
        super(HTML5Messages.wizardHTML5SamplePageName);
        setTitle(HTML5Messages.wizardHTML5SamplePageName);
        setDescription(HTML5Messages.wizardHTML5SamplePageDescription);
        setImageDescriptor(Images.motwinMedium);
        defaultProjectName = Preconditions.checkNotNull(aDefaultProjectName);
    }

    @Override
    public void createControl(Composite aParent) {
        super.createControl(aParent);
        projectNameField.setText(defaultProjectName);
        projectNameField.setEnabled(false);
        projectNameField.setEditable(false);
    }

}
