/**
 * 
 */
package com.motwin.ide.server.ui.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

/**
 * @author fbou
 * 
 */
public class MotwinServerUIPlugin extends AbstractUIPlugin implements IStartup {

    public static final String          PLUGIN_ID = "com.motwin.ide.server.ui"; //$NON-NLS-N$  
    private static MotwinServerUIPlugin instance;

    public MotwinServerUIPlugin() {
        super();
        MotwinServerUIPlugin.instance = this;
    }

    public static MotwinServerUIPlugin getDefault() {
        return instance;
    }

    @Override
    public void earlyStartup() {

    }

    @Override
    public void start(final BundleContext aContext) throws Exception {
        super.start(aContext);
        configureMOSRuntime();

    }

    private void configureMOSRuntime() {
        final MOSRuntimeConfigurator mosRuntimeConfigurator;
        mosRuntimeConfigurator = new MOSRuntimeConfigurator();

        if (mosRuntimeConfigurator.shallCreateDefaultMOS()) {
            // crappy but avoid a job creation for nothing
            Job job;
            job = new UIJob("Default MOS Runtime Creation if needed") {
                @Override
                public IStatus runInUIThread(final IProgressMonitor aMonitor) {

                    mosRuntimeConfigurator.createAndShow();
                    return Status.OK_STATUS;
                }
            };

            job.schedule();

        }
    }
}
