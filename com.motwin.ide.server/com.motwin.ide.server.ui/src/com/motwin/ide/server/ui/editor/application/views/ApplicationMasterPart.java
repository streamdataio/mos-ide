package com.motwin.ide.server.ui.editor.application.views;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;

import com.motwin.ide.server.ui.editor.application.ApplicationNamespaceUtils;
import com.motwin.ide.server.ui.editor.views.AbstractMotwinConfigMasterPart;

@SuppressWarnings("restriction")
public class ApplicationMasterPart extends AbstractMotwinConfigMasterPart {

    private Button createProcessorButton;
    private Button createInterceptorButton;
    private Button createSerializableButton;

    public ApplicationMasterPart(AbstractConfigFormPage page, Composite parent) {
        super(page, parent);
    }

    @Override
    protected String getSectionTitle() {
        return "Motwin Channel";
    }

    @Override
    protected String getSectionDescription() {
        return "Select an element to edit its details. Click right on an element to add or remove.";
    }

    @Override
    protected void createButtons(Composite aClient) {
        super.createButtons(aClient);

        // separator
        toolkit.createSeparator(aClient, SWT.HORIZONTAL | SWT.CENTER);

        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = 125;

        // button "New Processor"
        createProcessorButton = toolkit.createButton(aClient, "New Processor", SWT.FLAT);
        createProcessorButton.setLayoutData(data);
        createProcessorButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TreeViewer treeViewer = ((TreeViewer) getViewer());
                IDOMDocument document = getConfigEditor().getDomDocument();

                // create <interceptor>
                IDOMElement processorElement = createElement(document, ApplicationNamespaceUtils.PROCESSOR_TAG);
                document.getDocumentElement().appendChild(processorElement);
                contentAssistProcessor.insertDefaultAttributes(processorElement);

                formatter.formatNode(processorElement);
                formatter.formatNode(processorElement.getParentNode());

                treeViewer.setSelection(new StructuredSelection(processorElement));

            }
        });

        // button "New Interceptor"
        createInterceptorButton = toolkit.createButton(aClient, "New Interceptor", SWT.FLAT);
        createInterceptorButton.setLayoutData(data);
        createInterceptorButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IDOMDocument document = getConfigEditor().getDomDocument();
                IDOMElement documentElement = (IDOMElement) document.getDocumentElement();
                IDOMElement interceptorsElement = locateNode(documentElement,
                        getTagWithPrefix(ApplicationNamespaceUtils.INTERCEPTORS_TAG));

                // add <interceptors> if needed
                if (interceptorsElement == null) {
                    interceptorsElement = createElement(document, ApplicationNamespaceUtils.INTERCEPTORS_TAG);
                    documentElement.appendChild(interceptorsElement);
                    contentAssistProcessor.insertDefaultAttributes(interceptorsElement);

                    formatter.formatNode(interceptorsElement);
                    formatter.formatNode(interceptorsElement.getParentNode());

                }

                // add <interceptor>
                IDOMElement interceptorElement = createElement(document, ApplicationNamespaceUtils.INTERCEPTOR_TAG);
                interceptorsElement.appendChild(interceptorElement);
                contentAssistProcessor.insertDefaultAttributes(interceptorElement);

                formatter.formatNode(interceptorElement);
                formatter.formatNode(interceptorElement.getParentNode());

                ((TreeViewer) getViewer()).setSelection(new StructuredSelection(interceptorElement));

            }
        });

        createSerializableButton = toolkit.createButton(aClient, "New Serializable", SWT.FLAT);
        createSerializableButton.setLayoutData(data);
        createSerializableButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TreeViewer treeViewer = ((TreeViewer) getViewer());
                IDOMDocument document = getConfigEditor().getDomDocument();

                // create <serializable>
                IDOMElement serializableElement = createElement(document, ApplicationNamespaceUtils.SERIALIZABLE_TAG);
                document.getDocumentElement().appendChild(serializableElement);
                contentAssistProcessor.insertDefaultAttributes(serializableElement);

                formatter.formatNode(serializableElement);
                formatter.formatNode(serializableElement.getParentNode());

                treeViewer.setSelection(new StructuredSelection(serializableElement));

            }
        });

    }

}
