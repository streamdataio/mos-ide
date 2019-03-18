package com.motwin.ide.server.ui.editor.application.views;

import org.eclipse.swt.widgets.Composite;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigDetailsPart;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterPart;
import org.springframework.ide.eclipse.config.ui.editors.AbstractNamespaceMasterDetailsBlock;

import com.motwin.ide.server.ui.editor.application.ApplicationNamespaceUtils;

public class ApplicationMasterDetailsBlock extends AbstractNamespaceMasterDetailsBlock {

    public ApplicationMasterDetailsBlock(AbstractConfigFormPage page) {
        super(page);
    }

    @Override
    protected AbstractConfigMasterPart createMasterSectionPart(AbstractConfigFormPage page, Composite container) {
        return new ApplicationMasterPart(page, container);
    }

    @Override
    public AbstractConfigDetailsPart getDetailsPage(Object key) {
        return new ApplicationDetailsPart(getMasterPart(), ApplicationNamespaceUtils.DOC_URL);
    }

}
