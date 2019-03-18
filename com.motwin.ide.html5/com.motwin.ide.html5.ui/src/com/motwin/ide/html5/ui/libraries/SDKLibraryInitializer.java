/**
 * 
 */
package com.motwin.ide.html5.ui.libraries;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.internal.ui.IJsGlobalScopeContainerInitializerExtension;

import com.motwin.ide.html5.ui.HTML5Images;

/**
 * @author fbou
 * 
 */
@SuppressWarnings("restriction")
public class SDKLibraryInitializer implements IJsGlobalScopeContainerInitializerExtension {

    @Override
    public ImageDescriptor getImage(IPath aPath, String aString, IJavaScriptProject aJavaScriptProject) {
        return HTML5Images.LOGO;
    }

}
