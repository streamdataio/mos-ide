/**
 * 
 */
package com.motwin.ide.html5.ui.wizards.pages;

import com.motwin.ide.html5.ui.HTML5Messages;
import com.motwin.ide.ui.Images;
import com.motwin.ide.ui.wizards.AbstractJavaProjectPage;

/**
 * @author fbou
 * 
 */
public class DefaultJavaProjectPage extends AbstractJavaProjectPage {

    /**
     * Default constructor
     */
    public DefaultJavaProjectPage() {
        super(HTML5Messages.wizardHTML5ProjectPageName);
        setTitle(HTML5Messages.wizardHTML5ProjectPageName);
        setDescription(HTML5Messages.wizardHTML5ProjectPageDescription);
        setImageDescriptor(Images.motwinMedium);
    }

}
