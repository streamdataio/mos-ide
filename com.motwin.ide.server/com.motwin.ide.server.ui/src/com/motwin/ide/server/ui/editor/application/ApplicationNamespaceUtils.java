/**
 * 
 */
package com.motwin.ide.server.ui.editor.application;

import org.w3c.dom.Element;

/**
 * @author fbou
 * 
 */
public class ApplicationNamespaceUtils {

    public static final String   DOC_URL                        = null;

    public static final String[] SCOPES                         = new String[] { "", "singleton", "prototype",
            "channel"                                          };

    public static final String   CHANNEL_INTERCEPTOR_CLASS_NAME = "com.motwin.sdk.application.channel.ChannelInterceptor";
    public static final String   CHANNEL_PROCESSOR_CLASS_NAME   = "com.motwin.sdk.application.channel.ChannelProcessor";

    public static final String   INTERCEPTORS_TAG               = "interceptors";
    public static final String   INTERCEPTOR_TAG                = "interceptor";
    public static final String   PROCESSOR_TAG                  = "processor";
    public static final String   SERIALIZABLE_TAG               = "serializable";

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
