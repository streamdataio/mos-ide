package com.motwin.ide.server.ui.editor.messaging.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigEditor;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterPart;
import org.springframework.ide.eclipse.config.ui.editors.AbstractNamespaceDetailsPart;
import org.springframework.ide.eclipse.config.ui.editors.SpringConfigDetailsSectionPart;

@SuppressWarnings("restriction")
public class MessagingDetailsPart extends AbstractNamespaceDetailsPart {

    public MessagingDetailsPart(AbstractConfigMasterPart master) {
        super(master);
    }

    public MessagingDetailsPart(AbstractConfigMasterPart master, String docsUrl) {
        super(master, docsUrl);
    }

    @Override
    public SpringConfigDetailsSectionPart createDetailsSectionPart(AbstractConfigEditor editor, IDOMElement input,
                                                                   Composite parent, FormToolkit toolkit) {
        return new MessagingDetailsSectionPart(editor, input, parent, toolkit);
    }

}
