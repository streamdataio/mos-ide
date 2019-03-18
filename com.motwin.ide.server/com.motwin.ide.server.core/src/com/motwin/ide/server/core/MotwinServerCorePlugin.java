/**
 * 
 */
package com.motwin.ide.server.core;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.motwin.ide.server.core.preferences.PreferenceConstants;

/**
 * @author fbou
 *
 */
public class MotwinServerCorePlugin extends Plugin {

	public final static String PLUGIN_ID = "com.motwin.ide.server.core";
	public final static String MOTWIN_PLATFORM_FACET_ID = "com.motwin.platform";

	private static MotwinServerCorePlugin plugin;

	private final ScopedPreferenceStore preferenceStore;

	private String mootwinHome;

	@SuppressWarnings("deprecation")
	public MotwinServerCorePlugin() {
		super();

		preferenceStore         = new ScopedPreferenceStore(new InstanceScope(), getBundle().getSymbolicName());
		plugin                  = this;

		// initialize and register to mootwinHome changes
		//setMootwinHome(preferenceStore.getString(PreferenceConstants.MOOTWIN_HOME_PATH));
		preferenceStore.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent aEvent) {
				if(PreferenceConstants.MOOTWIN_HOME_PATH.equals(aEvent.getProperty())) {
					//setMootwinHome((String)aEvent.getNewValue());
				}
			}
		});

	}

	/**
	 * @return the plugin
	 */
	public static MotwinServerCorePlugin getDefault() {
		return plugin;
	}

	/**
	 * @return the preference store
	 */
	public IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	/**
	 * @return the mootwinHome
	 */
	public String getMootwinHome() {
		return mootwinHome;
	}

}
