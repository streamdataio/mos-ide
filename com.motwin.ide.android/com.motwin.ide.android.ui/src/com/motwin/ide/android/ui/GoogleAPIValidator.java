/**
 * 
 */
package com.motwin.ide.android.ui;

import com.android.sdklib.AndroidVersion;
import com.android.sdklib.IAndroidTarget;
import com.google.common.base.Preconditions;
import com.motwin.ide.android.ui.AndroidTargetsGroup.IAndroidTargetValidator;

public class GoogleAPIValidator implements IAndroidTargetValidator {
    private final int minAPILevel;

    public GoogleAPIValidator(final int aMinAPILevel) {
        minAPILevel = aMinAPILevel;
    }

    @Override
    public boolean validate(final IAndroidTarget aTarget) {
        Preconditions.checkNotNull(aTarget, "aTarget cannot be null");
        String name;
        name = aTarget.getName();

        AndroidVersion version;
        version = aTarget.getVersion();

        boolean result;
        result = "Google APIs".equals(name) && version.getApiLevel() >= minAPILevel;

        return result;
    }

    @Override
    public String getConstraintMessage() {
        return "Google API level 8 or higher";
    }

    @Override
    public String getErrorMessage() {
        return "You must select an Android SDK with " + getConstraintMessage() + ".";
    }

}