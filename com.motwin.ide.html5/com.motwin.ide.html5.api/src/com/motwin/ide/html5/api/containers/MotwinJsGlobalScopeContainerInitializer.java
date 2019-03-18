package com.motwin.ide.html5.api.containers;

import org.eclipse.core.runtime.IPath;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainer;
import org.eclipse.wst.jsdt.core.IJsGlobalScopeContainerInitializer;
import org.eclipse.wst.jsdt.core.JsGlobalScopeContainerInitializer;
import org.eclipse.wst.jsdt.core.compiler.libraries.LibraryLocation;
import org.eclipse.wst.jsdt.core.compiler.libraries.SystemLibraryLocation;

import com.google.common.base.Preconditions;

/**
 * @author fbou
 * 
 */
public abstract class MotwinJsGlobalScopeContainerInitializer extends JsGlobalScopeContainerInitializer implements
        IJsGlobalScopeContainerInitializer {

    protected abstract String getPluginId();

    protected abstract String getLibraryFile();

    @Override
    public final LibraryLocation getLibraryLocation() {
        return new SystemLibraryLocation() {

            @Override
            public char[][] getLibraryFileNames() {
                String libraryFile;

                libraryFile = MotwinJsGlobalScopeContainerInitializer.this.getLibraryFile();
                Preconditions.checkNotNull(libraryFile);

                return new char[][] { libraryFile.toCharArray() };
            }

            @Override
            protected String getPluginId() {
                return MotwinJsGlobalScopeContainerInitializer.this.getPluginId();
            }

        };
    }

    @Override
    public boolean allowAttachJsDoc() {
        return false;
    }

    @Override
    public int getKind() {
        return IJsGlobalScopeContainer.K_SYSTEM;
    }

    @Override
    public String[] containerSuperTypes() {
        return new String[] { "window" };
    }

    @Override
    public boolean canUpdateJsGlobalScopeContainer(IPath containerPath, IJavaScriptProject project) {
        return true;
    }

}
