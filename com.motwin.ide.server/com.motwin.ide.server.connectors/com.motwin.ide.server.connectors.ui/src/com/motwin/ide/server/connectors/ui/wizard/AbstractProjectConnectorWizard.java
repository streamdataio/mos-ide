package com.motwin.ide.server.connectors.ui.wizard;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * 
 * @author fbou
 */
public abstract class AbstractProjectConnectorWizard extends AbstractConnectorWizard {

    @Override
    protected void doFinishAsynchronous(IProgressMonitor aProgressMonitor) throws Exception {

        super.doFinishAsynchronous(new SubProgressMonitor(aProgressMonitor, 50));

    }

}
