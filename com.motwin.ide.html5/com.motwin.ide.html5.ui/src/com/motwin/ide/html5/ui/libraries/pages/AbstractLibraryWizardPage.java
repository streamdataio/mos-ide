/**
 * 
 */
package com.motwin.ide.html5.ui.libraries.pages;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.ComboDialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.wst.jsdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPage;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPageExtension;
import org.eclipse.wst.jsdt.ui.wizards.IJsGlobalScopeContainerPageExtension2;
import org.eclipse.wst.jsdt.ui.wizards.NewElementWizardPage;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * @author fbou
 * 
 */
@SuppressWarnings("restriction")
public class AbstractLibraryWizardPage extends NewElementWizardPage implements IJsGlobalScopeContainerPage,
        IJsGlobalScopeContainerPageExtension, IJsGlobalScopeContainerPageExtension2 {

    private final List<String> labels;
    private final List<String> containers;
    private ComboDialogField   versionField;
    private int                versionSelected = -1;

    public AbstractLibraryWizardPage(String aLibName, Map<String, String> aContainerMap) {
        super(aLibName);

        labels = Lists.newArrayList();
        containers = Lists.newArrayList();

        for (Entry<String, String> containerEntry : aContainerMap.entrySet()) {
            labels.add(containerEntry.getKey());
            containers.add(containerEntry.getValue());
        }
    }

    @Override
    public boolean finish() {
        return true;
    }

    @Override
    public IIncludePathEntry getSelection() {
        System.out.println("Unimplemented method:BaseLibraryWizardPage.getSelection");
        return null;
    }

    @Override
    public void setSelection(IIncludePathEntry containerEntry) {
        // nothing to do
    }

    @Override
    public void createControl(Composite parent) {
        Preconditions.checkState(labels != null, "labels are not initialized");

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setFont(parent.getFont());

        versionField = new ComboDialogField(SWT.READ_ONLY);
        versionField.setLabelText("Version");
        versionField.setItems(labels.toArray(new String[labels.size()]));
        versionField.selectItem(versionSelected >= 0 ? versionSelected : 0);
        versionField.setDialogFieldListener(new IDialogFieldListener() {
            @Override
            public void dialogFieldChanged(DialogField field) {
                versionSelected = ((ComboDialogField) field).getSelectionIndex();
                ;
            }
        });

        DialogField field = new DialogField();
        field.setLabelText("Take care that changing this value will not update the javascript file used by your project.");

        LayoutUtil.doDefaultLayout(composite, new DialogField[] { versionField, field }, false, SWT.DEFAULT,
                SWT.DEFAULT);
        Dialog.applyDialogFont(composite);
        setControl(composite);

    }

    @Override
    public void initialize(IJavaScriptProject project, IIncludePathEntry[] currentEntries) {
        Preconditions.checkNotNull(currentEntries);

        for (IIncludePathEntry entry : currentEntries) {
            for (String container : containers) {
                if (container.equals(entry.getPath().toString())) {
                    versionSelected = containers.indexOf(container);
                }
            }
        }
    }

    @Override
    public IIncludePathEntry[] getNewContainers() {
        Preconditions.checkState(containers != null, "containers are not initialized");

        String containerId = containers.get(versionField.getSelectionIndex());
        Preconditions.checkState(containerId != null);
        IIncludePathEntry library = JavaScriptCore.newContainerEntry(new Path(containerId));

        return new IIncludePathEntry[] { library };
    }

}