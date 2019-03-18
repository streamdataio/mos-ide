/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

/**
 * @author ctranxuan
 * 
 */
public class DashboardEditorInput implements IEditorInput, IPersistableElement {
    public final static DashboardEditorInput INSTANCE = new DashboardEditorInput();

    private DashboardEditorInput() {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object getAdapter(final Class anAdapter) {
        return null;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    @Override
    public String getName() {
        return "Dashboard";
    }

    @Override
    public IPersistableElement getPersistable() {
        return this;
    }

    @Override
    public String getToolTipText() {
        return "Dashboard";
    }

    @Override
    public void saveState(final IMemento aMemento) {
        DashboardEditorInputFactory.save(aMemento);
    }

    @Override
    public String getFactoryId() {
        return DashboardEditorInputFactory.FACTORY_ID;
    }

}
