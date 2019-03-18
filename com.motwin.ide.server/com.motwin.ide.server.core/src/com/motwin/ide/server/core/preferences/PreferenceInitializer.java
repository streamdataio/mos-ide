package com.motwin.ide.server.core.preferences;

import java.io.File;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.motwin.ide.server.core.MotwinServerCorePlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private static final String MOOTWIN_HOME_FOLDER_DEFAULT = ".mootwin";

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store;
		String userHome;

		store    = MotwinServerCorePlugin.getDefault().getPreferenceStore();
		userHome = System.getProperty("user.home");

		if(userHome != null && !userHome.isEmpty()) {
			String mootwinHomeDefault;

			mootwinHomeDefault = userHome + File.separator + MOOTWIN_HOME_FOLDER_DEFAULT;
			store.setDefault(PreferenceConstants.MOOTWIN_HOME_PATH,  mootwinHomeDefault);

		}
	}

}
