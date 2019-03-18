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
public final class OpenHTMLFileInEditorOperation extends AbstractOpenFileOperation {

    public OpenHTMLFileInEditorOperation(final IProject aProject, final String aPath, final String aPluginID) {
        super(aProject, aPath, aPluginID, JDTUtil.WTP_HTML_EDITOR_ID);
    }
}
