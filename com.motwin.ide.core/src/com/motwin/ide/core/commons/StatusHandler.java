/**
 * 
 */
package com.motwin.ide.core.commons;

import org.eclipse.core.runtime.IStatus;

import com.motwin.ide.core.MotwinCorePlugin;

/**
 * @author ctranxuan
 * 
 */
public class StatusHandler {

    public static void log(final IStatus status) {
        MotwinCorePlugin.getDefault().getLog().log(status);
    }
}
