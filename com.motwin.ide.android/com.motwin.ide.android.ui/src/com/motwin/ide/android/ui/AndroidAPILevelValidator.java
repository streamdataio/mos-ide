/**
 * 
 */
package com.motwin.ide.android.ui;

import com.android.sdklib.AndroidVersion;
import com.android.sdklib.IAndroidTarget;
import com.google.common.base.Preconditions;
import com.motwin.ide.android.ui.AndroidTargetsGroup.IAndroidTargetValidator;

/**
 * @author ctranxuan
 * 
 */
public class AndroidAPILevelValidator implements IAndroidTargetValidator {

    private final int minAPILevel;

    public AndroidAPILevelValidator(final int aMinLevelAPI) {
        minAPILevel = aMinLevelAPI;
    }

    @Override
    public boolean validate(final IAndroidTarget aTarget) {
        Preconditions.checkNotNull(aTarget, "aTarget cannot be null");

        AndroidVersion version;
        version = aTarget.getVersion();

        boolean result;
        result = (version.getApiLevel() >= minAPILevel);
        return result;
    }

    @Override
    public String getConstraintMessage() {
        return "API level " + minAPILevel + " or higher";
    }

    @Override
    public String getErrorMessage() {
        return "You must select an Android SDK with " + getConstraintMessage() + ".";
    }

}
