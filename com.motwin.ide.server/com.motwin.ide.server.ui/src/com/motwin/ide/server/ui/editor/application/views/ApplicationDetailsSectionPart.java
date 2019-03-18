package com.motwin.ide.server.ui.editor.application.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.ClassContentAssistCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistCalculator;
import org.springframework.ide.eclipse.config.core.contentassist.XmlBackedContentProposalProvider;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigEditor;
import org.springframework.ide.eclipse.config.ui.widgets.ButtonAttribute;
import org.springframework.ide.eclipse.config.ui.widgets.ComboAttribute;
import org.springframework.ide.eclipse.config.ui.widgets.TextAttribute;
import org.springframework.ide.eclipse.config.ui.widgets.TextAttributeProposalAdapter;

import com.motwin.ide.server.ui.editor.application.ApplicationNamespaceUtils;
import com.motwin.ide.server.ui.editor.contentassist.GenericClassContentAssistCalculator;
import com.motwin.ide.server.ui.editor.contentassist.GenericMethodContentAssistCalculator;
import com.motwin.ide.server.ui.editor.contentassist.GenericRefContentAssistCalculator;
import com.motwin.ide.server.ui.editor.views.MotwinConfigDetailsSectionPart;

@SuppressWarnings("restriction")
public class ApplicationDetailsSectionPart extends MotwinConfigDetailsSectionPart {

    public ApplicationDetailsSectionPart(AbstractConfigEditor editor, IDOMElement input, Composite parent,
            FormToolkit toolkit) {
        super(editor, input, parent, toolkit);
    }

    @Override
    protected boolean addCustomAttribute(Composite client, String attr, boolean required) {
        String element = getInput().getLocalName();
        if (ApplicationNamespaceUtils.PROCESSOR_TAG.equals(element)
            && ("ref".equals(attr) || "idref".equals(attr) || "bean".equals(attr))) {
            TextAttribute attrControl = createBeanAttribute(client, attr, required);
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new GenericRefContentAssistCalculator(ApplicationNamespaceUtils.CHANNEL_PROCESSOR_CLASS_NAME);
                }
            }));
            return true;
        }
        if (ApplicationNamespaceUtils.INTERCEPTOR_TAG.equals(element)
            && ("ref".equals(attr) || "idref".equals(attr) || "bean".equals(attr))) {
            TextAttribute attrControl = createBeanAttribute(client, attr, required);
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new GenericRefContentAssistCalculator(
                            ApplicationNamespaceUtils.CHANNEL_INTERCEPTOR_CLASS_NAME);
                }
            }));
            return true;
        }
        if (ApplicationNamespaceUtils.PROCESSOR_TAG.equals(element) && "class".equals(attr)) {
            ButtonAttribute attrControl = createClassAttribute(client, attr, false, required,
                    ApplicationNamespaceUtils.CHANNEL_PROCESSOR_CLASS_NAME);
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new GenericClassContentAssistCalculator(
                            ApplicationNamespaceUtils.CHANNEL_PROCESSOR_CLASS_NAME);
                }
            }));
            return true;
        }
        if (ApplicationNamespaceUtils.INTERCEPTOR_TAG.equals(element) && "class".equals(attr)) {
            ButtonAttribute attrControl = createClassAttribute(client, attr, false, required,
                    ApplicationNamespaceUtils.CHANNEL_INTERCEPTOR_CLASS_NAME);

            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new GenericClassContentAssistCalculator(
                            ApplicationNamespaceUtils.CHANNEL_INTERCEPTOR_CLASS_NAME);
                }
            }));
            return true;
        }
        if (ApplicationNamespaceUtils.SERIALIZABLE_TAG.equals(element) && "class".equals(attr)) {
            ButtonAttribute attrControl = createClassAttribute(client, attr, false, required);
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new ClassContentAssistCalculator(false);
                }
            }));
            return true;
        }
        if ("process-method".equals(attr)) {
            TextAttribute attrControl = createListenerMethodAttribute(client, attr, required);
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new GenericMethodContentAssistCalculator();
                }
            }));
            return true;
        }
        if ("scope".equals(attr)) {
            ComboAttribute scopeControl = createComboAttribute(client, attr, ApplicationNamespaceUtils.SCOPES, required);
            addWidget(scopeControl);
            return true;
        }

        return super.addCustomAttribute(client, attr, required);
    }

}
