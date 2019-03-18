package com.motwin.ide.server.ui.editor.requesting.views;

import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterDetailsBlock;

public class RequestingFormPage extends AbstractConfigFormPage {

    @Override
    protected AbstractConfigMasterDetailsBlock createMasterDetailsBlock() {
        return new RequestingMasterDetailsBlock(this);
    }

}
