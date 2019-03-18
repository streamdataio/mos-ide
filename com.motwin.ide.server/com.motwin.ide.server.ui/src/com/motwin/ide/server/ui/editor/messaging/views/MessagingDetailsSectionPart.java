package com.motwin.ide.server.ui.editor.messaging.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
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
import com.motwin.ide.server.ui.editor.messaging.MessagingNamespaceUtils;
import com.motwin.ide.server.ui.editor.views.MotwinConfigDetailsSectionPart;

@SuppressWarnings("restriction")
public class MessagingDetailsSectionPart extends MotwinConfigDetailsSectionPart {

    public MessagingDetailsSectionPart(final AbstractConfigEditor editor, final IDOMElement input,
            final Composite parent, final FormToolkit toolkit) {
        super(editor, input, parent, toolkit);
    }

    @Override
    protected boolean addCustomAttribute(final Composite client, final String attr, final boolean required) {
        String element = getInput().getLocalName();
        if (MessagingNamespaceUtils.PROCESSOR_TAG.equals(element)
            && ("ref".equals(attr) || "idref".equals(attr) || "bean".equals(attr))) {
            TextAttribute attrControl = createBeanAttribute(client, attr, required);
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new GenericRefContentAssistCalculator(MessagingNamespaceUtils.MESSAGE_PROCESSOR_CLASS_NAME);
                }
            }));
            return true;
        }
        if (MessagingNamespaceUtils.PROCESSOR_TAG.equals(element) && "class".equals(attr)) {
            ButtonAttribute attrControl = createClassAttribute(client, attr, false, required,
                    MessagingNamespaceUtils.MESSAGE_PROCESSOR_CLASS_NAME + "<Object>");
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new XmlBackedContentProposalProvider(getInput(),
                    attr) {
                @Override
                protected IContentAssistCalculator createContentAssistCalculator() {
                    return new GenericClassContentAssistCalculator(MessagingNamespaceUtils.MESSAGE_PROCESSOR_CLASS_NAME);
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
