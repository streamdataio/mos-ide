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
public class ApplicationLabelProvider extends JFaceNodeLabelProvider {

    @Override
    public Image getImage(Object aElement) {
        if (aElement instanceof Node) {
            Node node = (Node) aElement;
            String elementName = node.getLocalName();

            if (ApplicationNamespaceUtils.INTERCEPTOR_TAG.equals(elementName)
                || ApplicationNamespaceUtils.INTERCEPTORS_TAG.equals(elementName)) {
                return ServerImages.CHANNEL_INTERCEPTOR;
            } else if (ApplicationNamespaceUtils.PROCESSOR_TAG.equals(elementName)) {
                return ServerImages.CHANNEL_PROCESSOR;
            } else if (ApplicationNamespaceUtils.SERIALIZABLE_TAG.equals(elementName)) {
                return ServerImages.SERIALIZABLE;
            }
        }

        return super.getImage(aElement);
    }

    @Override
    public String getText(Object aElement) {
        if (aElement instanceof Node) {
            Element node = (Element) aElement;
            String elementName = node.getLocalName();

            if (ApplicationNamespaceUtils.INTERCEPTORS_TAG.equals(elementName)) {
                return "interceptors";
            } else if (ApplicationNamespaceUtils.INTERCEPTOR_TAG.equals(elementName)) {
                return ApplicationNamespaceUtils.extractTextRef(node);
            } else if (ApplicationNamespaceUtils.PROCESSOR_TAG.equals(elementName)) {
                return ApplicationNamespaceUtils.extractTextRef(node);
            } else if (ApplicationNamespaceUtils.SERIALIZABLE_TAG.equals(elementName)) {
                String name;
                String className;
                if (node.hasAttribute("name") && !node.getAttribute("name").isEmpty()) {
                    name = node.getAttribute("name");
                } else {
                    name = "<empty name>";
                }
                if (node.hasAttribute("class") && !node.getAttribute("class").isEmpty()) {
                    className = node.getAttribute("class");
                } else {
                    className = "<empty class>";
                }
                return name + " ↔ " + className;
            }
        }

        return super.getText(aElement);
    }

}
