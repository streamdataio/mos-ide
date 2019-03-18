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
package com.motwin.ide.dashboard.internal.ui.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;

/**
 * @author Christian Dupuis
 * @since 2.3.0
 */
public class DashboardEditorInputFactory implements IElementFactory {

    public static final String FACTORY_ID = DashboardPlugin.PLUGIN_ID + ".dashboard.elementFactory";

    @Override
    public IAdaptable createElement(final IMemento memento) {
        return DashboardEditorInput.INSTANCE;
    }

    public static void save(final IMemento memento) {
        // nothing to do really
        memento.putString("openDashboard", "true");
    }
}
