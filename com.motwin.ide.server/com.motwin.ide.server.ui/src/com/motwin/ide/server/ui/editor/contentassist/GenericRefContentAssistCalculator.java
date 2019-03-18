package com.motwin.ide.server.ui.editor.contentassist;

import org.springframework.ide.eclipse.beans.ui.editor.contentassist.AbstractIdContentAssistCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistContext;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistProposalRecorder;
import org.springframework.ide.eclipse.beans.ui.editor.util.BeansCompletionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**

 */
public class GenericRefContentAssistCalculator extends AbstractIdContentAssistCalculator {

    private final String className;

    public GenericRefContentAssistCalculator(String aClassName) {
        className = Preconditions.checkNotNull(aClassName);
    }

    @Override
    public void computeProposals(IContentAssistContext context, IContentAssistProposalRecorder recorder) {
        BeansCompletionUtils.addBeanReferenceProposals(context, recorder, true, Lists.newArrayList(className));
    }

}
