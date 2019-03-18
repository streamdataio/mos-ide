package com.motwin.ide.server.ui.editor.virtualdb.views;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;

import com.motwin.ide.server.ui.editor.views.AbstractMotwinConfigMasterPart;
import com.motwin.ide.server.ui.editor.virtualdb.VirtualdbNamespaceUtils;

@SuppressWarnings("restriction")
public class VirtualdbMasterPart extends AbstractMotwinConfigMasterPart {

    private Button createTableButton;
    private Button createColumnButton;
    private Button createSourceButton;

    public VirtualdbMasterPart(AbstractConfigFormPage page, Composite parent) {
        super(page, parent);
    }

    @Override
    protected void treeViewerSelectionChanged() {
        super.treeViewerSelectionChanged();
        if (createColumnButton != null && createSourceButton != null) {
            TreeItem[] items = getSelection();
            if (items.length == 0 || items.length > 1) {
                createSourceButton.setEnabled(false);
                createColumnButton.setEnabled(false);
                createTableButton.setEnabled(false);

            } else {
                IDOMElement selectedElement = getElementFromTreeItem(items[0]);
                if (hasAnyParentNodeName(selectedElement, getTagWithPrefix(VirtualdbNamespaceUtils.VIRTUALDB_TAG))) {
                    createTableButton.setEnabled(true);

                } else {
                    createTableButton.setEnabled(false);

                }

                if (hasAnyParentNodeName(selectedElement, getTagWithPrefix(VirtualdbNamespaceUtils.TABLE_TAG))) {
                    createSourceButton.setEnabled(true);
                    createColumnButton.setEnabled(true);

                } else {
                    createSourceButton.setEnabled(false);
                    createColumnButton.setEnabled(false);

                }
            }
        }
    }

    @Override
    protected String getSectionTitle() {
        return "Motwin Virtual Database";
    }

    @Override
    protected String getSectionDescription() {
        return "Select an element to edit its details. Click right on an element to add or remove.";
    }

    @Override
    protected void createButtons(Composite aClient) {
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = 100;

        // button "New Table"
        createTableButton = toolkit.createButton(aClient, "New Table", SWT.FLAT);
        createTableButton.setLayoutData(data);
        createTableButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TreeViewer treeViewer = ((TreeViewer) getViewer());
                IDOMDocument document = getConfigEditor().getDomDocument();
                IDOMElement selectedElement = getSelectedElement();
                IDOMElement targetElement = locateNearestNode(selectedElement,
                        getTagWithPrefix(VirtualdbNamespaceUtils.VIRTUALDB_TAG),
                        getTagWithPrefix(VirtualdbNamespaceUtils.VIRTUALDB_TAG));

                if (targetElement != null) {
                    // create <table name="">
                    IDOMElement tableElement = createElement(document, VirtualdbNamespaceUtils.TABLE_TAG);
                    targetElement.appendChild(tableElement);
                    contentAssistProcessor.insertDefaultAttributes(tableElement);

                    // create <schema>
                    IDOMElement schemaElement = createElement(document, VirtualdbNamespaceUtils.SCHEMA_TAG);
                    tableElement.appendChild(schemaElement);
                    contentAssistProcessor.insertDefaultAttributes(schemaElement);

                    // create <source>
                    IDOMElement sourceElement = createElement(document, VirtualdbNamespaceUtils.SOURCE_TAG);
                    tableElement.appendChild(sourceElement);
                    contentAssistProcessor.insertDefaultAttributes(sourceElement);

                    formatter.formatNode(schemaElement);
                    formatter.formatNode(sourceElement);
                    formatter.formatNode(tableElement);
                    formatter.formatNode(tableElement.getParentNode());

                    treeViewer.setSelection(new StructuredSelection(tableElement));

                }
            }
        });

        // button "New Column"
        createColumnButton = toolkit.createButton(aClient, "New Column", SWT.FLAT);
        createColumnButton.setLayoutData(data);
        createColumnButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IDOMDocument document = getConfigEditor().getDomDocument();
                IDOMElement selectedElement = getSelectedElement();
                IDOMElement targetElement = locateNearestNode(selectedElement,
                        getTagWithPrefix(VirtualdbNamespaceUtils.SCHEMA_TAG),
                        getTagWithPrefix(VirtualdbNamespaceUtils.TABLE_TAG));

                if (targetElement != null) {
                    IDOMElement columnElement = createElement(document, VirtualdbNamespaceUtils.COLUMN_TAG);
                    targetElement.appendChild(columnElement);
                    contentAssistProcessor.insertDefaultAttributes(columnElement);

                    formatter.formatNode(columnElement);
                    formatter.formatNode(columnElement.getParentNode());

                    ((TreeViewer) getViewer()).setSelection(new StructuredSelection(columnElement));
                }
            }
        });

        createSourceButton = toolkit.createButton(aClient, "New Source", SWT.FLAT);
        createSourceButton.setLayoutData(data);
        createSourceButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IDOMDocument document = getConfigEditor().getDomDocument();
                IDOMElement selectedElement = getSelectedElement();
                IDOMElement targetElement = locateNearestNode(selectedElement,
                        getTagWithPrefix(VirtualdbNamespaceUtils.TABLE_TAG),
                        getTagWithPrefix(VirtualdbNamespaceUtils.TABLE_TAG));

                if (targetElement != null) {
                    IDOMElement sourceElement = createElement(document, VirtualdbNamespaceUtils.SOURCE_TAG);
                    targetElement.appendChild(sourceElement);
                    contentAssistProcessor.insertDefaultAttributes(sourceElement);

                    formatter.formatNode(sourceElement);
                    formatter.formatNode(sourceElement.getParentNode());

                    ((TreeViewer) getViewer()).setSelection(new StructuredSelection(sourceElement));
                }

            }
        });

        // separator
        // toolkit.createSeparator(aClient, SWT.HORIZONTAL | SWT.CENTER);

    }

}
