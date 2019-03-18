package com.motwin.ide.dashboard.ui.editors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Clients that contribute sections to the dashboard need to extend this class.
 */
public abstract class AbstractDashboardPart extends AbstractFormPart {

    private Control control;

    private String  id;

    public final void createControl(final Composite parent) {
        this.control = createPartContent(parent);
    }

    public abstract Control createPartContent(Composite parent);

    public final Control getControl() {
        return control;
    }

    public String getId() {
        return id;
    }

    protected FormToolkit getToolkit() {
        return getManagedForm().getToolkit();
    }

    public void setId(final String id) {
        this.id = id;
    }

}
