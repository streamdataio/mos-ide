/**
 * 
 */
package com.motwin.ide.server.ui.editor.requesting;

import org.w3c.dom.Element;

/**
 * @author fbou
 * 
 */
public class RequestingNamespaceUtils {

    public static final String DOC_URL                      = null;

    public final static String REQUEST_PROCESSOR_CLASS_NAME = "com.motwin.sdk.requesting.RequestProcessor";
    public static final String PROCESSOR_TAG                = "processor";

    /**
     * @param aElement
     * @return
     */
    public static String extractTextRef(Element aElement) {
        String value;
        if (aElement.hasAttribute("ref") && !aElement.getAttribute("ref").isEmpty()) {
            value = "ref:" + aElement.getAttribute("ref");
        } else if (aElement.hasAttribute("bean") && !aElement.getAttribute("bean").isEmpty()) {
            value = "bean:" + aElement.getAttribute("bean");
        } else if (aElement.hasAttribute("idref") && !aElement.getAttribute("idref").isEmpty()) {
            value = "idref:" + aElement.getAttribute("idref");
        } else if (aElement.hasAttribute("class") && !aElement.getAttribute("class").isEmpty()) {
            value = "class:" + aElement.getAttribute("class");
        } else {
            value = "(inner bean)";
        }
        return value;
    }

}
