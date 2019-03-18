package com.motwin.ide.server.ui.editor.virtualdb.contentassist;

import org.springframework.ide.eclipse.beans.ui.editor.contentassist.bean.BeansContentAssistProcessor;

/**

 */
public class VirtualdbContentAssistProcessor extends BeansContentAssistProcessor {

    @Override
    public void init() {
        SourceRefContentAssistCalculator sourceRefBean = new SourceRefContentAssistCalculator();
        registerContentAssistCalculator("source", "ref", sourceRefBean);
    }

}
