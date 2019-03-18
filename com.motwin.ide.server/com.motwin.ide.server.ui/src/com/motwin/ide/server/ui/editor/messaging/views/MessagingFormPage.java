package com.motwin.ide.server.ui.editor.messaging.views;

import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterDetailsBlock;

public class MessagingFormPage extends AbstractConfigFormPage {

    @Override
    protected AbstractConfigMasterDetailsBlock createMasterDetailsBlock() {
        return new MessagingMasterDetailsBlock(this);
    }

}
