/**
 * 
 */
package com.motwin.ide.server.ui.editor.application.contentassist;

import org.springframework.ide.eclipse.beans.ui.editor.contentassist.ClassHierachyContentAssistCalculator;

/**
 * @author fbou
 * 
 */
public class GenericClassContentAssistCalculator extends ClassHierachyContentAssistCalculator {

    /**
     * @param aTypeName
     */
    public GenericClassContentAssistCalculator(String aTypeName) {
        super(aTypeName);
    }

}
