package com.motwin.ide.server.ui.editor.virtualdb.contentassist;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.motwin.ide.server.ui.editor.virtualdb.VirtualdbNamespaceUtils;
import com.motwin.ide.server.ui.internal.ServerImages;

/**
 */
@SuppressWarnings("restriction")
public class VirtualdbLabelProvider extends JFaceNodeLabelProvider {

    @Override
    public Image getImage(Object aElement) {
        Node node = (Node) aElement;
        String elementName = node.getLocalName();

        if (VirtualdbNamespaceUtils.VIRTUALDB_TAG.equals(elementName)) {
            return ServerImages.DATABASE;
        } else if (VirtualdbNamespaceUtils.TABLE_TAG.equals(elementName)) {
            Element element = (Element) node;
            String persistent = element.getAttribute(VirtualdbNamespaceUtils.TABLE_PERSISTENT_ATTRIBUTE);
            if (VirtualdbNamespaceUtils.isTrue(persistent, true)) {
                return ServerImages.TABLE_PERSISTENT;
            } else {
                return ServerImages.TABLE;
            }
        } else if (VirtualdbNamespaceUtils.SCHEMA_TAG.equals(elementName)) {
            return ServerImages.SCHEMA;
        } else if (VirtualdbNamespaceUtils.COLUMN_TAG.equals(elementName)) {
            Element element = (Element) node;
            String partOfKey = element.getAttribute(VirtualdbNamespaceUtils.COLUMN_PART_OF_KEY_ATTRIBUTE);
            if (VirtualdbNamespaceUtils.isTrue(partOfKey, false)) {
                return ServerImages.COLUMN_KEY;
            } else {
                return ServerImages.COLUMN;
            }
        } else if (VirtualdbNamespaceUtils.SOURCE_TAG.equals(elementName)) {
            return ServerImages.SOURCE;
        }

        return super.getImage(aElement);
    }

    @Override
    public String getText(Object aElement) {
        Element node = (Element) aElement;
        String elementName = node.getLocalName();

        if (VirtualdbNamespaceUtils.VIRTUALDB_TAG.equals(elementName)) {
            if (node.hasAttribute("id") && !node.getAttribute("id").isEmpty()) {
                return "Virtual Database [" + node.getAttribute("id") + "]";
            } else {
                return "New Virtual Database";
            }
        } else if (VirtualdbNamespaceUtils.TABLE_TAG.equals(elementName)) {
            if (node.hasAttribute("name") && !node.getAttribute("name").isEmpty()) {
                return node.getAttribute("name");
            } else {
                return "<New Table>";
            }
        } else if (VirtualdbNamespaceUtils.SCHEMA_TAG.equals(elementName)) {
            return "Schema";
        } else if (VirtualdbNamespaceUtils.COLUMN_TAG.equals(elementName)) {
            if (node.hasAttribute("name") && !node.getAttribute("name").isEmpty()) {
                return node.getAttribute("name");
            } else {
                return "<New Column>";
            }
        } else if (VirtualdbNamespaceUtils.SOURCE_TAG.equals(elementName)) {
            if (node.hasAttribute("ref") && !node.getAttribute("ref").isEmpty()) {
                return "Source [" + node.getAttribute("ref") + "]";
            } else if (node.hasAttribute("bean") && !node.getAttribute("bean").isEmpty()) {
                return "Source [" + node.getAttribute("bean") + "]";
            } else if (node.hasAttribute("name") && !node.getAttribute("name").isEmpty()) {
                return "Source [" + node.getAttribute("name") + "]";
            } else {
                return "<New Source>";
            }
        }

        return super.getText(aElement);
    }

}
