/*******************************************************************************
 *  Copyright (c) 2012 VMware, Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *      VMware, Inc. - initial API and implementation
 *******************************************************************************/
package com.motwin.ide.server.ui.editor.virtualdb.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigEditor;
import org.springframework.ide.eclipse.config.ui.editors.SpringConfigDetailsSectionPart;
import org.springframework.ide.eclipse.config.ui.widgets.TextAttribute;
import org.springframework.ide.eclipse.config.ui.widgets.TextAttributeProposalAdapter;

import com.motwin.ide.server.ui.editor.virtualdb.contentassist.SourceRefContentProposalProvider;

/**
 * @author Leo Dos Santos
 */
@SuppressWarnings("restriction")
public class VirtualdbDetailsSectionPart extends SpringConfigDetailsSectionPart {

    public VirtualdbDetailsSectionPart(AbstractConfigEditor editor, IDOMElement input, Composite parent,
            FormToolkit toolkit) {
        super(editor, input, parent, toolkit);
    }

    @Override
    protected boolean addCustomAttribute(Composite client, String attr, boolean required) {
        if ("ref".equals(attr)) {
            TextAttribute attrControl = createBeanAttribute(client, attr, required);
            addWidget(attrControl);
            addAdapter(new TextAttributeProposalAdapter(attrControl, new SourceRefContentProposalProvider(getInput(),
                    attr)));
            return true;
        }
        return super.addCustomAttribute(client, attr, required);
    }

}
