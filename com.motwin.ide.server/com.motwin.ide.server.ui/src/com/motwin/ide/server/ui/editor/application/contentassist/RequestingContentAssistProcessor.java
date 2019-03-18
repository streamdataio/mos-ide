package com.motwin.ide.server.ui.editor.application.contentassist;

import org.springframework.ide.eclipse.beans.ui.editor.contentassist.bean.BeansContentAssistProcessor;

/**

 */
public class RequestingContentAssistProcessor extends BeansContentAssistProcessor {

    private final static String REQUEST_PROCESSOR_CLASS_NAME = "com.motwin.sdk.requesting.RequestProcessor";

    @Override
    public void init() {
        GenericRefContentAssistCalculator processorRef = new GenericRefContentAssistCalculator(
                REQUEST_PROCESSOR_CLASS_NAME);
        registerContentAssistCalculator("processor", "ref", processorRef);
        registerContentAssistCalculator("processor", "idref", processorRef);
        registerContentAssistCalculator("processor", "bean", processorRef);

        GenericMethodContentAssistCalculator method = new GenericMethodContentAssistCalculator();
        registerContentAssistCalculator("processor", "process-method", method);

        GenericClassContentAssistCalculator processorClazz = new GenericClassContentAssistCalculator(
                REQUEST_PROCESSOR_CLASS_NAME);
        registerContentAssistCalculator("processor", "class", processorClazz);

    }

}
