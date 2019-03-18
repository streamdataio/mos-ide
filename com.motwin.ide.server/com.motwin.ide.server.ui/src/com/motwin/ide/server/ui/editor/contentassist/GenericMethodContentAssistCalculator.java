/**
 * 
 */
package com.motwin.ide.server.ui.editor.contentassist;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IType;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistContext;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.MethodContentAssistCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.util.BeansEditorUtils;
import org.springframework.ide.eclipse.core.java.FlagsMethodFilter;
import org.springframework.ide.eclipse.core.java.JdtUtils;
import org.w3c.dom.Node;

/**
 * @author fbou
 * 
 */
public class GenericMethodContentAssistCalculator extends MethodContentAssistCalculator {

    /**
     * @param aFilter
     */
    public GenericMethodContentAssistCalculator() {
        super(new FlagsMethodFilter(FlagsMethodFilter.PUBLIC | FlagsMethodFilter.NOT_CONSTRUCTOR
            | FlagsMethodFilter.NOT_INTERFACE));
    }

    @Override
    protected IType calculateType(IContentAssistContext aContext) {
        IFile file = aContext.getFile();
        Node node = aContext.getNode();
        String ref;
        String className;

        if (BeansEditorUtils.hasAttribute(node, "ref")) {
            ref = BeansEditorUtils.getAttribute(node, "ref");
        } else if (BeansEditorUtils.hasAttribute(node, "idref")) {
            ref = BeansEditorUtils.getAttribute(node, "idref");
        } else if (BeansEditorUtils.hasAttribute(node, "bean")) {
            ref = BeansEditorUtils.getAttribute(node, "bean");
        } else {
            ref = null;
        }

        if (ref != null) {
            className = BeansEditorUtils.getClassNameForBean(file, node.getOwnerDocument(), ref);
        } else {
            className = BeansEditorUtils.getAttribute(node, "class");
        }

        if (className != null) {
            return JdtUtils.getJavaType(file.getProject(), className);
        }

        return null;
    }

}
