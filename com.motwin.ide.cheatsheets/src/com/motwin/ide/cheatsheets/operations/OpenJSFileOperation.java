/**
 * 
 */
package com.motwin.ide.cheatsheets.operations;

import org.eclipse.core.resources.IProject;

import com.motwin.ide.cheatsheets.util.jdt.JDTUtil;

/**
 * @author ctranxuan
 * 
 */
public class OpenJSFileOperation extends AbstractOpenFileOperation {

    public OpenJSFileOperation(final IProject aProject, final String aPath, final String aPluginID) {
        super(aProject, aPath, aPluginID, JDTUtil.WTP_JSDT_EDITOR_ID);
    }
}
