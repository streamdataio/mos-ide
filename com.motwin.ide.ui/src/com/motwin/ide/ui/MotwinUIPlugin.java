/**
 * 
 */
package com.motwin.ide.ui;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @author fbou
 *
 */
public class MotwinUIPlugin extends AbstractUIPlugin implements IStartup {

	public static final String PLUGIN_ID = "com.motwin.ide.ui";

	private static MotwinUIPlugin instance;

	public MotwinUIPlugin() {
		super();
		MotwinUIPlugin.instance = this;
	}

	public static MotwinUIPlugin getDefault() {
		return instance;
	}

	@Override
	public void earlyStartup() {

	}

}
