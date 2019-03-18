package com.motwin.ide.server.ui.editor.virtualdb.contentassist;

import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistCalculator;
import org.springframework.ide.eclipse.config.core.contentassist.XmlBackedContentProposalProvider;

/**

 */
@SuppressWarnings("restriction")
public class SourceRefContentProposalProvider extends XmlBackedContentProposalProvider {

    /**

	 */
    public SourceRefContentProposalProvider(IDOMElement input, String attrName) {
        super(input, attrName);
    }

    @Override
    protected IContentAssistCalculator createContentAssistCalculator() {
        return new SourceRefContentAssistCalculator();
    }

}
