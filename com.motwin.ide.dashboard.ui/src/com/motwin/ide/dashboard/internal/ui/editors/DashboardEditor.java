/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;

/**
 * @author ctranxuan
 * 
 */
public class DashboardEditor extends SharedHeaderFormEditor {
    public static String EDITOR_ID = "com.motwin.ide.dashboard.ui.editors.DashboardEditor";

    @Override
    protected void addPages() {
        try {
            addPage(new DashboardMainPage(this));
        } catch (PartInitException e) {
            StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
                    "failed to add the pages into the DashboardEditor", e));
        }
    }

    @Override
    protected void createHeaderContents(final IManagedForm aHeaderForm) {
        ScrolledForm form;
        form = aHeaderForm.getForm();
        form.setText("Motwin Dashboard");

        Form topForm;
        topForm = form.getForm();
        aHeaderForm.getToolkit().decorateFormHeading(topForm);

        final IToolBarManager toolBarManager;
        toolBarManager = topForm.getToolBarManager();
        toolBarManager.add(new Action("Motwin", createImageDescriptor()) {

            @Override
            public void run() {
                TasksUiUtil.openUrl("http://www.motwin.com");
            }
        });
        toolBarManager.update(true);
    }

    @Override
    public void doSave(final IProgressMonitor aMonitor) {
        // nothing to do
    }

    @Override
    public void doSaveAs() {
        // nothing to do

    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    private ImageDescriptor createImageDescriptor() {
        ImageDescriptor imageDescriptor = null;

        try {
            URL imageURL;
            imageURL = new URL(DashboardPlugin.getDefault().getBundle().getEntry("/icons/"), "motwin_logo.png");

            imageDescriptor = ImageDescriptor.createFromURL(imageURL);

        } catch (MalformedURLException e) {
            StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "Unable to create the image", e));
        }

        return imageDescriptor;
    }
}
