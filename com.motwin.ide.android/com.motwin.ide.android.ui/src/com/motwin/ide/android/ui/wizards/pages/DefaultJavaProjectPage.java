/**
 * 
 */
package com.motwin.ide.android.ui.wizards.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import com.motwin.ide.android.ui.AndroidAPILevelValidator;
import com.motwin.ide.android.ui.AndroidTargetsGroup;
import com.motwin.ide.android.ui.AndroidTargetsGroup.IAndroidTargetValidator;
import com.motwin.ide.android.ui.AndroidTargetsGroup.IMessageListener;
import com.motwin.ide.android.ui.internal.AndroidMessages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou
 * 
 */
public class DefaultJavaProjectPage extends AbstractJavaProjectPage implements IMessageListener {
    private AndroidTargetsGroup androidTargetsGroup;

    /**
     * Default constructor
     */
    public DefaultJavaProjectPage() {
        this(AndroidMessages.wizardAndroidProjectPageName, AndroidMessages.wizardAndroidProjectPageName,
                AndroidMessages.wizardAndroidProjectPageDescription);
    }

    public DefaultJavaProjectPage(final String aPageName, final String aTitle, final String aDescription) {
        super(aPageName);
        setTitle(aTitle);
        setDescription(aDescription);
        setImageDescriptor(Images.motwinMedium);
    }

    @Override
    public void createControl(final Composite aParent) {
        super.createControl(aParent);
        checkPageComplete();
    }

    @Override
    protected void createProjectNameGroup(final Composite aContainer) {
        super.createProjectNameGroup(aContainer);

        createAndroidTargetsDescriptionComposite(aContainer);
    }

    private void createAndroidTargetsDescriptionComposite(final Composite aParent) {
        androidTargetsGroup = new AndroidTargetsGroup(aParent, SWT.NULL, getAndroidTargetValidator(), this);
        androidTargetsGroup.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetDefaultSelected(final SelectionEvent anEvent) {
                checkPageComplete();
            }

            @Override
            public void widgetSelected(final SelectionEvent anEvent) {
                checkPageComplete();
            }
        });
    }

    protected IAndroidTargetValidator getAndroidTargetValidator() {
        return new AndroidAPILevelValidator(4);
    }

    @Override
    public void newErrorMessage(final String aMessage) {
        setErrorMessage(aMessage);
    }

    @Override
    public void clearErrorMessage() {
        setErrorMessage(null);

    }

    public String getAndroidTargetHash() {
        return androidTargetsGroup.getAndroidTargetHashOrDefault();
    }

    protected void checkPageComplete() {
        setPageComplete(validate());
    }

    protected boolean validate() {
        boolean result = true;

        if (androidTargetsGroup.getAndroidTarget() == null) {
            setErrorMessage(androidTargetsGroup.getTargetConstraintMessage());
            result = false;
        }

        return result;
    }
}
