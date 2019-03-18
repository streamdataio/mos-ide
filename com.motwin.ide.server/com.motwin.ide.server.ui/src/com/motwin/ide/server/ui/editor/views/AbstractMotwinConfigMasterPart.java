package com.motwin.ide.server.ui.editor.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.config.core.contentassist.SpringConfigContentAssistProcessor;
import org.springframework.ide.eclipse.config.core.formatting.ShallowFormatProcessorXML;
import org.springframework.ide.eclipse.config.ui.actions.CollapseNodeAction;
import org.springframework.ide.eclipse.config.ui.actions.DeleteNodeAction;
import org.springframework.ide.eclipse.config.ui.actions.ExpandNodeAction;
import org.springframework.ide.eclipse.config.ui.actions.InsertNodeAction;
import org.springframework.ide.eclipse.config.ui.actions.LowerNodeAction;
import org.springframework.ide.eclipse.config.ui.actions.RaiseNodeAction;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigLabelProvider;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterPart;
import org.springframework.ide.eclipse.config.ui.editors.Messages;
import org.springframework.ide.eclipse.config.ui.editors.SpringConfigContentProvider;
import org.springframework.ide.eclipse.config.ui.editors.SpringConfigLabelProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

@SuppressWarnings("restriction")
public abstract class AbstractMotwinConfigMasterPart extends AbstractConfigMasterPart {

    //private static String                            SCHEMA_URI_PREFIX = "http://www.springframework.org/schema/"; //$NON-NLS-1$

    protected SpringConfigContentAssistProcessor     contentAssistProcessor;
    protected ShallowFormatProcessorXML              formatter;
    protected SelectionChangedListener               selectionListener;

    protected TreeViewer                             treeViewer;

    private Button                                   upButton;
    private Button                                   downButton;

    private final SpringConfigContentAssistProcessor xmlProcessor;

    private class SelectionChangedListener implements ISelectionChangedListener {
        @Override
        public void selectionChanged(final SelectionChangedEvent event) {
            treeViewerSelectionChanged();
        }
    }

    protected void treeViewerSelectionChanged() {
        if (upButton != null && downButton != null) {
            TreeItem[] items = getSelection();
            if (items.length == 0 || items.length > 1) {
                upButton.setEnabled(false);
                downButton.setEnabled(false);
            } else {
                upButton.setEnabled(true);
                downButton.setEnabled(true);
            }
        }
    }

    public AbstractMotwinConfigMasterPart(final AbstractConfigFormPage page, final Composite parent) {
        super(page, parent);
        xmlProcessor = page.getXmlProcessor();
        contentAssistProcessor = new SpringConfigContentAssistProcessor();
        formatter = new ShallowFormatProcessorXML();
        selectionListener = new SelectionChangedListener();
    }

    /**
     * This method is called automatically when a context menu is invoked on the
     * master viewer, and adds new actions to create child nodes and delete the
     * selected node. Clients may extend to add additional actions to the menu.
     * 
     * @param manager
     *            the menu manager on the master viewer
     * @param parent
     *            the selected XML node
     */
    protected void createNodeInsertActions(final IMenuManager manager, final IDOMElement parent) {
        StructuredTextViewer textView = getConfigEditor().getTextViewer();
        Map<String, List<String>> childMap = getChildNames(parent);
        if (childMap.keySet().size() > 1) {
            for (String prefix : childMap.keySet()) {
                MenuManager subManager;
                if (prefix.trim().equals("")) {
                    subManager = new MenuManager(
                            Messages.getString("AbstractNamespaceMasterPart.DEFAULT_NAMESPACE_SUBMENU"));
                } else {
                    subManager = new MenuManager(prefix);
                }
                for (String childName : childMap.get(prefix)) {
                    subManager.add(new InsertNodeAction(treeViewer, xmlProcessor, textView, childName));
                }
                manager.add(subManager);
            }
        } else {
            for (String prefix : childMap.keySet()) {
                for (String childName : childMap.get(prefix)) {
                    manager.add(new InsertNodeAction(treeViewer, xmlProcessor, textView, childName));
                }
            }
        }
    }

    @Override
    protected ColumnViewer createViewer(final Composite client) {
        FilteredTree filter = new FilteredTree(client, SWT.MULTI | SWT.BORDER, new FilteredViewerFilter(), true);
        treeViewer = filter.getViewer();
        return treeViewer;
    }

    @Override
    protected void createButtons(final Composite aClient) {
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = 100;

        // button "up"
        upButton = toolkit.createButton(aClient,
                Messages.getString("AbstractNamespaceMasterPart.MOVE_UP_BUTTON"), SWT.FLAT); //$NON-NLS-1$
        upButton.setLayoutData(data);
        upButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                StructuredTextViewer textView = getConfigEditor().getTextViewer();
                Action action = new RaiseNodeAction(treeViewer, xmlProcessor, textView);
                action.run();
            }
        });
        upButton.setEnabled(false);

        // button "down"
        downButton = toolkit.createButton(aClient,
                Messages.getString("AbstractNamespaceMasterPart.MOVE_DOWN_BUTTON"), SWT.FLAT); //$NON-NLS-1$
        downButton.setLayoutData(data);
        downButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                StructuredTextViewer textView = getConfigEditor().getTextViewer();
                Action action = new LowerNodeAction(treeViewer, xmlProcessor, textView);
                action.run();
            }
        });
        downButton.setEnabled(false);

        getViewer().addSelectionChangedListener(selectionListener);
        selectionListener.selectionChanged(null);

    }

    @Override
    public void dispose() {
        if (treeViewer != null && selectionListener != null) {
            treeViewer.removeSelectionChangedListener(selectionListener);
        }
        super.dispose();
    }

    protected IDOMElement getElementFromTreeItem(final TreeItem item) {
        if (item != null) {
            Object data = item.getData();
            if (data != null && data instanceof IDOMElement) {
                return (IDOMElement) data;
            }
        }
        return null;
    }

    protected TreeItem[] getSelection() {
        TreeViewer treeViewer = ((TreeViewer) getViewer());
        TreeItem[] items = treeViewer.getTree().getSelection();
        return items;
    }

    protected boolean hasAnyParentNodeName(final IDOMElement aElement, final String aNodeName) {
        Preconditions.checkNotNull(aNodeName);
        String nodeName = aElement.getNodeName();
        if (aNodeName.equals(nodeName)) {
            return true;
        } else if (aElement.getParentNode() == null || !(aElement.getParentNode() instanceof IDOMElement)) {
            return false;
        } else {
            return hasAnyParentNodeName((IDOMElement) aElement.getParentNode(), aNodeName);
        }
    }

    protected IDOMElement getSelectedElement() {
        TreeItem[] selection = getSelection();
        if (selection.length == 1) {
            return getElementFromTreeItem(selection[0]);
        }
        return null;
    }

    protected IDOMElement locateNearestNode(final IDOMElement aCurrentElement, final String aNodeName,
                                            final String aLimitNodeName) {
        Preconditions.checkNotNull(aCurrentElement, "aCurrentElement cannot be null");
        Preconditions.checkNotNull(aNodeName, "aNodeName cannot be null");
        Preconditions.checkNotNull(aLimitNodeName, "aLimitNodeName cannot be null");

        Node rootNode;

        // find root node
        rootNode = aCurrentElement;
        while (rootNode instanceof IDOMElement && !((IDOMElement) rootNode).getNodeName().equals(aLimitNodeName)) {
            rootNode = ((IDOMElement) rootNode).getParentNode();
        }

        // find target node
        if (rootNode instanceof IDOMElement) {
            return locateNode((IDOMElement) rootNode, aNodeName);
        }

        return null;
    }

    protected IDOMElement locateNode(final IDOMElement aCurrentElement, final String aNodeName) {
        Preconditions.checkNotNull(aCurrentElement, "aCurrentElement cannot be null");
        Preconditions.checkNotNull(aNodeName, "aNodeName cannot be null");

        if (aCurrentElement.getNodeName().equals(aNodeName)) {
            return aCurrentElement;
        }

        NodeList childs = aCurrentElement.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node node = childs.item(i);
            if (node instanceof IDOMElement) {
                IDOMElement foundElement = locateNode((IDOMElement) node, aNodeName);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }

        return null;
    }

    /**
     * This method is called automatically when the master part is created. This
     * implementation returns a {@link ApplicationConfigContentProvider}, but
     * clients may override to return their own content provider.
     * 
     * @return content provider for the master viewer
     */
    @Override
    protected SpringConfigContentProvider createViewerContentProvider() {
        return new SpringConfigContentProvider(getFormPage());
    }

    /**
     * This method is called automatically when the master part is created. This
     * implementation returns a {@link ApplicationConfigLabelProvider}, but
     * clients may override to return their own label provider.
     * 
     * @return label provider for the master viewer
     */
    @Override
    protected AbstractConfigLabelProvider createViewerLabelProvider() {
        return new SpringConfigLabelProvider();
    }

    @Override
    protected void fillContextMenu(final IMenuManager manager) {
        IStructuredSelection selection = (IStructuredSelection) getViewer().getSelection();
        StructuredTextViewer textView = getConfigEditor().getTextViewer();
        if (selection.size() == 1) {
            Object obj = selection.getFirstElement();
            if (obj != null && obj instanceof IDOMElement) {
                IDOMElement node = (IDOMElement) obj;
                createNodeInsertActions(manager, node);
                manager.add(new Separator());
                manager.add(new DeleteNodeAction(textView, node));
            }
        } else {
            IDOMDocument doc = getConfigEditor().getDomDocument();
            if (doc != null) {
                createEmptyDocumentActions(manager, doc);
            }
        }
    }

    /**
     * @param aManager
     * @param aDoc
     */
    private void createEmptyDocumentActions(final IMenuManager aManager, final IDOMDocument aDoc) {
        // TODO Auto-generated method stub

    }

    /**
     * Returns a map of namespace prefixes to node names that can be added as
     * children to the given parent. Clients may override if necessary.
     * 
     * @see ApplicationConfigContentProvider#getChildNames(String)
     * @param parent
     *            the parent element
     * @return map of namespace prefixes to child names for the given parent
     */
    protected Map<String, List<String>> getChildNames(final IDOMElement parent) {
        SpringConfigContentAssistProcessor proc = getFormPage().getXmlProcessor();
        Node grandParent = parent.getParentNode();
        Map<String, List<String>> childMap = new TreeMap<String, List<String>>();
        if (proc != null) {
            List<String> list = proc.getChildNames(parent);
            String uri = getFormPage().getNamespaceUri();
            for (String name : list) {
                String prefix = "";
                List<String> names;
                int pos = name.indexOf(':');

                if (pos > -1) {
                    prefix = name.substring(0, pos);
                }

                if (childMap.containsKey(prefix)) {
                    names = childMap.get(prefix);
                } else {
                    names = new ArrayList<String>();
                }

                if (grandParent instanceof Document && uri != null) {
                    String prefixForUri = getFormPage().getPrefixForNamespaceUri();
                    if (prefix.equals(prefixForUri)) {
                        names.add(name);
                        childMap.put(prefix, names);
                    }
                } else {
                    names.add(name);
                    childMap.put(prefix, names);
                }
            }
        }
        return childMap;
    }

    @Override
    protected void postCreateContents() {
        if (treeViewer != null) {
            selectionListener = new SelectionChangedListener();
            treeViewer.addSelectionChangedListener(selectionListener);
            ToolBarManager manager = getToolBarManager();
            manager.add(new CollapseNodeAction(treeViewer, xmlProcessor));
            manager.add(new ExpandNodeAction(treeViewer, xmlProcessor));
            treeViewer.expandToLevel(2);
        }
    }

    protected IDOMElement createElement(final IDOMDocument aDocument, final String aTagName) {
        String tagNameWithPrefix = getTagWithPrefix(aTagName);
        String namespaceUri = getFormPage().getNamespaceUri();
        return (IDOMElement) aDocument.createElementNS(namespaceUri, tagNameWithPrefix);
    }

    protected String getTagWithPrefix(final String aTagName) {
        String tagNameWithPrefix;
        String namespaceUriPrefix = getFormPage().getPrefixForNamespaceUri();

        if (namespaceUriPrefix.trim().isEmpty()) {
            tagNameWithPrefix = aTagName;
        } else {
            tagNameWithPrefix = namespaceUriPrefix + ":" + aTagName;
        }

        return tagNameWithPrefix;
    }

    /**
     * Custom FilteredViewerFilter: show all the subnodes of a matching
     * 
     * @author fbou
     */
    private class FilteredViewerFilter extends PatternFilter {

        private boolean checkNode(final Viewer viewer, final Object element) {
            String labelText = ((ILabelProvider) ((StructuredViewer) viewer).getLabelProvider()).getText(element);

            if (labelText == null) {
                return false;
            }

            return wordMatches(labelText);
        }

        private boolean checkChildreen(final Viewer viewer, final Element aElement) {
            NodeList nodes = aElement.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (checkNode(viewer, node)) {
                    return true;
                }
                if (node instanceof Element && checkChildreen(viewer, (Element) node)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected boolean isLeafMatch(final Viewer viewer, final Object element) {
            if (checkNode(viewer, element)) {
                return true;
            }

            Node rootNode;

            // find root node
            rootNode = (Node) element;
            while (rootNode instanceof Element && !((IDOMElement) rootNode).getNodeName().equals("table")) {
                rootNode = ((IDOMElement) rootNode).getParentNode();
            }

            // find target node
            if (rootNode instanceof Element) {
                return checkNode(viewer, rootNode) || checkChildreen(viewer, (Element) rootNode);
            }

            return false;
        }
    }

}
