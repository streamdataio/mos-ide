/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.handlers.HandlerUtil;

import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.internal.ui.editors.DashboardEditor;
import com.motwin.ide.dashboard.internal.ui.editors.DashboardEditorInput;
import com.motwin.ide.dashboard.internal.ui.editors.DashboardMainPage;

/**
 * @author ctranxuan
 * 
 */
public class DashboardHandler extends AbstractHandler {

    public static final String COMMAND_ID = "com.motwin.ide.dashboard.commands.dashboardCommand";

    public DashboardHandler() {
    }

    @Override
    public Object execute(final ExecutionEvent anEvent) throws ExecutionException {
        IWorkbenchWindow window;
        window = HandlerUtil.getActiveWorkbenchWindowChecked(anEvent);

        IWorkbenchPage page;
        page = window.getActivePage();

        try {
            FormEditor editor;
            editor = (FormEditor) page.openEditor(DashboardEditorInput.INSTANCE, DashboardEditor.EDITOR_ID);

            editor.setActivePage(DashboardMainPage.PAGE_ID);

        } catch (PartInitException e) {
            StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "Can not open dashboard", e));
        }
        return null;
    }
}
