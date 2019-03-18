/**
 * 
 */
package com.motwin.ide.android.ui;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.android.ide.eclipse.adt.internal.sdk.Sdk;
import com.android.sdklib.AndroidVersion;
import com.android.sdklib.IAndroidTarget;
import com.google.common.collect.Lists;

/**
 * @author ctranxuan
 * 
 */
@SuppressWarnings("restriction")
public class AndroidTargetsGroup {

    public static interface IMessageListener {
        void newErrorMessage(String aMessage);

        void clearErrorMessage();
    }

    public static interface IAndroidTargetValidator {

        boolean validate(IAndroidTarget aTarget);

        String getConstraintMessage();

        String getErrorMessage();
    }

    private final Combo                   targetsCombo;
    private final IAndroidTargetValidator androidTargetValidator;

    private final IMessageListener        messageListener;

    /**
     * @param aParent
     * @param aStyle
     */
    public AndroidTargetsGroup(final Composite aParent, final int aStyle, final IAndroidTargetValidator aValidator,
            final IMessageListener aListener) {
        Group group;
        group = new Group(aParent, aStyle);

        androidTargetValidator = aValidator;
        messageListener = aListener;

        group.setText("Android SDK");
        group.setLayout(new GridLayout(2, false));
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label label;
        label = new Label(group, SWT.NULL);
        // label.setText("Android SDK (API level " + minAPILevel +
        // " or higher):");
        label.setText(androidTargetValidator.getConstraintMessage() + ":");

        targetsCombo = new Combo(group, SWT.BORDER | SWT.READ_ONLY);
        targetsCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fill();
        bind();
    }

    private void fill() {
        Collection<IAndroidTarget> androidTargets;
        androidTargets = getAndroidTargets();

        for (IAndroidTarget androidTarget : androidTargets) {
            String label;
            label = toLabel(androidTarget);

            targetsCombo.add(label);
            targetsCombo.setData(label, androidTarget);
        }

        if (!androidTargets.isEmpty()) {
            targetsCombo.select(0);
        }
    }

    private void bind() {
        targetsCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent anEvent) {
                IAndroidTarget androidTarget;
                androidTarget = getAndroidTarget();

                if (androidTarget == null) {
                    messageListener.newErrorMessage(androidTargetValidator.getErrorMessage());

                } else {
                    messageListener.clearErrorMessage();

                }
            }

        });
    }

    private String toLabel(final IAndroidTarget anAndroidTarget) {
        AndroidVersion version;
        version = anAndroidTarget.getVersion();

        return anAndroidTarget.getFullName() + " - " + version;
    }

    private Collection<IAndroidTarget> getAndroidTargets() {
        Collection<IAndroidTarget> result;
        result = Lists.newArrayList();

        if (Sdk.getCurrent() != null) {
            IAndroidTarget[] androidTargets;
            androidTargets = Sdk.getCurrent().getTargets();

            for (IAndroidTarget androidTarget : androidTargets) {
                if (androidTargetValidator.validate(androidTarget)) {
                    result.add(androidTarget);
                }
            }
        }

        return result;
    }

    public void addSelectionListener(final SelectionListener aSelectionListener) {
        targetsCombo.addSelectionListener(aSelectionListener);
    }

    public String getAndroidTargetHashOrDefault() {
        String result;
        result = null;

        IAndroidTarget androidTarget;
        androidTarget = getAndroidTarget();

        if (androidTarget == null) {
            result = "android-4";

        } else {
            result = androidTarget.hashString();

        }

        return result;
    }

    public String getTargetConstraintMessage() {
        return androidTargetValidator.getConstraintMessage();
    }

    public IAndroidTarget getAndroidTarget() {
        return (IAndroidTarget) targetsCombo.getData(targetsCombo.getText());
    }

}
