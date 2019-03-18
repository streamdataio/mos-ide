/**
 * 
 */
package com.motwin.ide.ui.operations;

import org.eclipse.swt.program.Program;

/**
 * @author ctranxuan
 * 
 */
public final class OpenBrowserOperation extends AbstractOpenBrowserOperation {

    public OpenBrowserOperation(final String aURL) {
        super(aURL);
    }

    @Override
    public void run() {
        Program.launch(getURL().toString());
    }

}
