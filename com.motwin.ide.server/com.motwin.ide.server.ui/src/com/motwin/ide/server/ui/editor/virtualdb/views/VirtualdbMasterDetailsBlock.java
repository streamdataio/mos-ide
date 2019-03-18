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
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigDetailsPart;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigFormPage;
import org.springframework.ide.eclipse.config.ui.editors.AbstractConfigMasterPart;
import org.springframework.ide.eclipse.config.ui.editors.AbstractNamespaceMasterDetailsBlock;

import com.motwin.ide.server.ui.editor.virtualdb.VirtualdbNamespaceUtils;

/**
 * @author Leo Dos Santos
 * @author Christian Dupuis
 */
public class VirtualdbMasterDetailsBlock extends AbstractNamespaceMasterDetailsBlock {

    public VirtualdbMasterDetailsBlock(AbstractConfigFormPage page) {
        super(page);
    }

    @Override
    protected AbstractConfigMasterPart createMasterSectionPart(AbstractConfigFormPage page, Composite container) {
        return new VirtualdbMasterPart(page, container);
    }

    @Override
    public AbstractConfigDetailsPart getDetailsPage(Object key) {
        return new VirtualdbDetailsPart(getMasterPart(), VirtualdbNamespaceUtils.DOC_URL);
    }

}
