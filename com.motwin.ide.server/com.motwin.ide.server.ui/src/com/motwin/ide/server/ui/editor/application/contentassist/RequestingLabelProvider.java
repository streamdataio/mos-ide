package com.motwin.ide.server.ui.editor.application.contentassist;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.motwin.ide.server.ui.editor.application.ApplicationNamespaceUtils;
import com.motwin.ide.server.ui.internal.ServerImages;

/**
 */
@SuppressWarnings("restriction")
public class RequestingLabelProvider extends JFaceNodeLabelProvider {

    @Override
    public Image getImage(Object aElement) {
        Node node = (Node) aElement;
        String elementName = node.getLocalName();

        if (ApplicationNamespaceUtils.PROCESSOR_TAG.equals(elementName)) {
            return ServerImages.REQUEST_PROCESSOR;
        }

        return super.getImage(aElement);
    }

    @Override
    public String getText(Object aElement) {
        Element node = (Element) aElement;
        String elementName = node.getLocalName();
        String type;

        if (node.hasAttribute("type") && !node.getAttribute("type").isEmpty()) {
            type = node.getAttribute("type");
        } else {
            type = "<empty type>";
        }

        if (ApplicationNamespaceUtils.PROCESSOR_TAG.equals(elementName)) {
            return "RequestProcessor:" + type + ":" + ApplicationNamespaceUtils.extractTextRef(node);
        }

        return super.getText(aElement);
    }

}
