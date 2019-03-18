package com.motwin.ide.server.ui.editor.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.config.ui.ConfigUiPlugin;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigEditor;
import org.springframework.ide.eclipse.config.ui.editors.Messages;
import org.springframework.ide.eclipse.config.ui.editors.SpringConfigDetailsSectionPart;
import org.springframework.ide.eclipse.config.ui.editors.SpringConfigInputAccessor;
import org.springframework.ide.eclipse.config.ui.hyperlinks.ClassHyperlinkProvider;
import org.springframework.ide.eclipse.config.ui.hyperlinks.XmlBackedHyperlinkProvider;
import org.springframework.ide.eclipse.config.ui.widgets.ButtonAttribute;
import org.springsource.ide.eclipse.commons.core.StatusHandler;

@SuppressWarnings("restriction")
public class MotwinConfigDetailsSectionPart extends SpringConfigDetailsSectionPart {

    protected final FormToolkit             toolkit;
    private final SpringConfigInputAccessor delegate;

    /**
     * @param aEditor
     * @param aInput
     * @param aParent
     * @param aToolkit
     */
    public MotwinConfigDetailsSectionPart(final AbstractConfigEditor aEditor, final IDOMElement aInput,
            final Composite aParent, final FormToolkit aToolkit) {
        super(aEditor, aInput, aParent, aToolkit);
        toolkit = aToolkit;
        delegate = new SpringConfigInputAccessor(aEditor, aInput);
    }

    protected ButtonAttribute createClassAttribute(final Composite client, final String attr,
                                                   final boolean includeInterfaces, final boolean required,
                                                   final String aSuperInterface) {
        ButtonAttribute buttonAttr = new ButtonAttribute(client, toolkit, attr, required) {
            @Override
            public void browse() {
                doOpenTypeDialog(attr, text.getText(), includeInterfaces);
            }

            @Override
            public void modifyAttribute() {
                editAttribute(attr, text.getText());
            }

            @Override
            public void openHyperlink() {
                XmlBackedHyperlinkProvider provider = new ClassHyperlinkProvider(getConfigEditor().getTextViewer(),
                        getInput(), attr);
                if (!provider.open(text.getText())) {
                    openNewClassWizard(attr, text.getText(), aSuperInterface);
                }
            }

            @Override
            public void update() {
                setTextValue(text, getAttributeValue(attr));
            }
        };
        buttonAttr.createAttribute();
        return buttonAttr;
    }

    private void doOpenTypeDialog(final String attr, String filter, final boolean includeInterfaces) {
        filter = filter.replace('$', '.');
        try {
            if (filter == null) {
                filter = ""; //$NON-NLS-1$
            }

            int scope;
            if (includeInterfaces) {
                scope = IJavaElementSearchConstants.CONSIDER_CLASSES_AND_INTERFACES;
            } else {
                scope = IJavaElementSearchConstants.CONSIDER_CLASSES;
            }

            SelectionDialog dialog = JavaUI.createTypeDialog(getConfigEditor().getSite().getShell(), PlatformUI
                    .getWorkbench().getProgressService(), null, scope, false, filter);
            dialog.setTitle(Messages.getString("AbstractNamespaceDetailsPart.TYPE_SELECTION_DIALOG_TITLE")); //$NON-NLS-1$

            if (dialog.open() == Window.OK) {
                IType type = (IType) dialog.getResult()[0];
                String newValue = type.getFullyQualifiedName('$');
                editAttribute(attr, newValue);
            }
        } catch (JavaModelException e) {
            StatusHandler.log(new Status(IStatus.ERROR, ConfigUiPlugin.PLUGIN_ID, Messages
                    .getString("AbstractNamespaceDetailsPart.ERROR_OPENING_DIALOG"), e)); //$NON-NLS-1$
        }
    }

    private void editAttribute(final String attrName, final String newValue) {
        delegate.editAttribute(attrName, newValue);
    }

    private String getAttributeValue(final String attr) {
        return delegate.getAttributeValue(attr);
    }

    private void openNewClassWizard(final String attr, String name, final String aSuperInterface) {
        name = name.replace('$', '.');
        IProject project = getConfigEditor().getResourceFile().getProject();
        MotwinExtendedNewClassCreationWizard wizard = new MotwinExtendedNewClassCreationWizard(project, name,
                aSuperInterface, true);
        wizard.init(PlatformUI.getWorkbench(), null);
        WizardDialog dialog = new WizardDialog(getConfigEditor().getSite().getShell(), wizard);
        dialog.create();
        if (dialog.open() == Window.OK) {
            String newValue = wizard.getQualifiedName();
            editAttribute(attr, newValue);
        }
    }

}
