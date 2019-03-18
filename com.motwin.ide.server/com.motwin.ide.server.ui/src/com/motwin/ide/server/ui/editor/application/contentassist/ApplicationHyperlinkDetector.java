/**
 * 
 */
package com.motwin.ide.server.ui.editor.application.contentassist;

import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.springframework.ide.eclipse.beans.ui.editor.hyperlink.BeanHyperlinkCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.hyperlink.NamespaceHyperlinkDetectorSupport;

/**
 * @author fbou
 * 
 */
public class ApplicationHyperlinkDetector extends NamespaceHyperlinkDetectorSupport implements IHyperlinkDetector {

    @Override
    public void init() {
        BeanHyperlinkCalculator beanRef = new BeanHyperlinkCalculator();
        registerHyperlinkCalculator("idref", beanRef);
        registerHyperlinkCalculator("ref", beanRef);

        /*
         * MethodHyperlinkCalculator methodRef = new
         * RegistrationMethodHyperlinkCalculator();
         * registerHyperlinkCalculator("registration-method", methodRef);
         * registerHyperlinkCalculator("unregistration-method", methodRef);
         */

    }

}
