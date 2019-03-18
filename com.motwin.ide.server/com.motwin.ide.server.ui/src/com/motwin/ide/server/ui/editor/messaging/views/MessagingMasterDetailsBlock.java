package com.motwin.ide.server.ui.editor.messaging.views;

import org.eclipse.swt.widgets.Composite;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigDetailsPart;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterPart;
import org.springframework.ide.eclipse.config.ui.editors.AbstractNamespaceMasterDetailsBlock;

import com.motwin.ide.server.ui.editor.messaging.MessagingNamespaceUtils;

public class MessagingMasterDetailsBlock extends AbstractNamespaceMasterDetailsBlock {

    public MessagingMasterDetailsBlock(AbstractConfigFormPage page) {
        super(page);
    }

    @Override
    protected AbstractConfigMasterPart createMasterSectionPart(AbstractConfigFormPage page, Composite container) {
        return new MessagingMasterPart(page, container);
    }

    @Override
    public AbstractConfigDetailsPart getDetailsPage(Object key) {
        return new MessagingDetailsPart(getMasterPart(), MessagingNamespaceUtils.DOC_URL);
    }

}
