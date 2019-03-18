package com.motwin.ide.server.ui.editor.virtualdb.contentassist;

import org.springframework.ide.eclipse.beans.ui.editor.contentassist.AbstractIdContentAssistCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistContext;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistProposalRecorder;
import org.springframework.ide.eclipse.beans.ui.editor.util.BeansCompletionUtils;

import com.google.common.collect.Lists;

/**

 */
public class SourceRefContentAssistCalculator extends AbstractIdContentAssistCalculator {

    private static final String SOURCE_CLASS_NAME = "com.motwin.streamdata.spi.Source";

    @Override
    public void computeProposals(IContentAssistContext context, IContentAssistProposalRecorder recorder) {
        BeansCompletionUtils.addBeanReferenceProposals(context, recorder, true, Lists.newArrayList(SOURCE_CLASS_NAME));
    }

}
