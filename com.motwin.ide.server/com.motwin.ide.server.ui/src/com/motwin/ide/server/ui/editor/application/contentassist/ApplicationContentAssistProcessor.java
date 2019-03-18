package com.motwin.ide.server.ui.editor.application.contentassist;

import org.springframework.ide.eclipse.beans.ui.editor.contentassist.ClassContentAssistCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.bean.BeansContentAssistProcessor;

import com.motwin.ide.server.ui.editor.application.ApplicationNamespaceUtils;
import com.motwin.ide.server.ui.editor.contentassist.GenericClassContentAssistCalculator;
import com.motwin.ide.server.ui.editor.contentassist.GenericMethodContentAssistCalculator;
import com.motwin.ide.server.ui.editor.contentassist.GenericRefContentAssistCalculator;

/**

 */
public class ApplicationContentAssistProcessor extends BeansContentAssistProcessor {

    @Override
    public void init() {
        GenericRefContentAssistCalculator interceptorRef = new GenericRefContentAssistCalculator(
                ApplicationNamespaceUtils.CHANNEL_INTERCEPTOR_CLASS_NAME);
        registerContentAssistCalculator("interceptor", "ref", interceptorRef);
        registerContentAssistCalculator("interceptor", "idref", interceptorRef);
        registerContentAssistCalculator("interceptor", "bean", interceptorRef);

        GenericRefContentAssistCalculator processorRef = new GenericRefContentAssistCalculator(
                ApplicationNamespaceUtils.CHANNEL_PROCESSOR_CLASS_NAME);
        registerContentAssistCalculator("processor", "ref", processorRef);
        registerContentAssistCalculator("processor", "idref", processorRef);
        registerContentAssistCalculator("processor", "bean", processorRef);

        GenericMethodContentAssistCalculator method = new GenericMethodContentAssistCalculator();
        registerContentAssistCalculator("interceptor", "process-method", method);
        registerContentAssistCalculator("processor", "process-method", method);

        GenericClassContentAssistCalculator interceptorClazz = new GenericClassContentAssistCalculator(
                ApplicationNamespaceUtils.CHANNEL_INTERCEPTOR_CLASS_NAME);
        registerContentAssistCalculator("interceptor", "class", interceptorClazz);

        GenericClassContentAssistCalculator processorClazz = new GenericClassContentAssistCalculator(
                ApplicationNamespaceUtils.CHANNEL_PROCESSOR_CLASS_NAME);
        registerContentAssistCalculator("processor", "class", processorClazz);

        ClassContentAssistCalculator clazz = new ClassContentAssistCalculator(false);
        registerContentAssistCalculator("serializable", "class", clazz);

    }

}
