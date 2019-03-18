package com.motwin.ide.server.ui.editor.requesting.contentassist;

import org.springframework.ide.eclipse.beans.ui.editor.contentassist.bean.BeansContentAssistProcessor;

import com.motwin.ide.server.ui.editor.contentassist.GenericClassContentAssistCalculator;
import com.motwin.ide.server.ui.editor.contentassist.GenericMethodContentAssistCalculator;
import com.motwin.ide.server.ui.editor.contentassist.GenericRefContentAssistCalculator;
import com.motwin.ide.server.ui.editor.requesting.RequestingNamespaceUtils;

/**

 */
public class RequestingContentAssistProcessor extends BeansContentAssistProcessor {

    @Override
    public void init() {
        GenericRefContentAssistCalculator processorRef = new GenericRefContentAssistCalculator(
                RequestingNamespaceUtils.REQUEST_PROCESSOR_CLASS_NAME);
        registerContentAssistCalculator("processor", "ref", processorRef);
        registerContentAssistCalculator("processor", "idref", processorRef);
        registerContentAssistCalculator("processor", "bean", processorRef);

        GenericMethodContentAssistCalculator method = new GenericMethodContentAssistCalculator();
        registerContentAssistCalculator("processor", "process-method", method);

        GenericClassContentAssistCalculator processorClazz = new GenericClassContentAssistCalculator(
                RequestingNamespaceUtils.REQUEST_PROCESSOR_CLASS_NAME);
        registerContentAssistCalculator("processor", "class", processorClazz);

    }

}
