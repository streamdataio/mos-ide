/**
 * 
 */
package com.motwin.ide.android.ui.internal;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @author fbou
 *
 */
public class MotwinAndroidUIPlugin extends AbstractUIPlugin implements IStartup {

	public static final String PLUGIN_ID = "com.motwin.ide.android.ui";

	private static MotwinAndroidUIPlugin instance;

	public MotwinAndroidUIPlugin() {
		super();
		MotwinAndroidUIPlugin.instance = this;
	}

	public static MotwinAndroidUIPlugin getDefault() {
		return instance;
	}

	@Override
	public void earlyStartup() {

	}

}
