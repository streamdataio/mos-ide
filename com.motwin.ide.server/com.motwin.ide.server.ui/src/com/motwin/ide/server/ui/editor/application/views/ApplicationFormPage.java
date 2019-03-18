package com.motwin.ide.server.ui.editor.application.views;

import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterDetailsBlock;

public class ApplicationFormPage extends AbstractConfigFormPage {

    @Override
    protected AbstractConfigMasterDetailsBlock createMasterDetailsBlock() {
        return new ApplicationMasterDetailsBlock(this);
    }

}
