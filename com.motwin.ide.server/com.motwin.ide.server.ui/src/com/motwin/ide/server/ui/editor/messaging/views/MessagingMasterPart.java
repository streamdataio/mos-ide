package com.motwin.ide.server.ui.editor.messaging.views;

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

import com.motwin.ide.server.ui.editor.messaging.MessagingNamespaceUtils;
import com.motwin.ide.server.ui.editor.views.AbstractMotwinConfigMasterPart;

@SuppressWarnings("restriction")
public class MessagingMasterPart extends AbstractMotwinConfigMasterPart {

    private Button createProcessorButton;

    public MessagingMasterPart(AbstractConfigFormPage page, Composite parent) {
        super(page, parent);
    }

    @Override
    protected String getSectionTitle() {
        return "Motwin Messaging";
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
                IDOMElement processorElement = createElement(document, MessagingNamespaceUtils.PROCESSOR_TAG);
                document.getDocumentElement().appendChild(processorElement);
                contentAssistProcessor.insertDefaultAttributes(processorElement);

                formatter.formatNode(processorElement);
                formatter.formatNode(processorElement.getParentNode());

                treeViewer.setSelection(new StructuredSelection(processorElement));

            }
        });

    }

}
