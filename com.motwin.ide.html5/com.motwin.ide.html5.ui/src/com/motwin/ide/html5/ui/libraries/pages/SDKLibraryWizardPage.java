/**
 * 
 */
package com.motwin.ide.html5.ui.libraries.pages;

import com.motwin.ide.html5.api.HTML5APIPlugin;

/**
 * @author fbou
 * 
 */
public class SDKLibraryWizardPage extends AbstractLibraryWizardPage {

    public SDKLibraryWizardPage() {
        super("MotwinSDKLib", HTML5APIPlugin.getContainers());
        setTitle("Motwin Javascript SDK");
        setDescription("Select the Motwin Javascript SDK version to use for Javascript Editor auto-completion.");
    }

}