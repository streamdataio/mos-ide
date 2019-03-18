/*******************************************************************************
 * Copyright (c) 2009 - 2012 SpringSource, a divison of VMware, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SpringSource, a division of VMware, Inc. - initial API and implementation
 *******************************************************************************/
package com.motwin.ide.server.core.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.statushandlers.StatusManager;

import com.motwin.ide.server.core.MotwinServerCorePlugin;

/**
 * Utility to check if the given {@link IResource} belongs to a project that has the par or bundle facet.
 * 
 * @author Christian Dupuis
 * @author Leo Dos Santos
 * @since 1.0.0
 */
public class NatureUtils {

	/**
	 * Checks if a given {@link IResource} has the bundle facet.
	 */
	public static boolean isMotwinPlatformProject(IResource resource) {
		return hasNature(resource, JavaCore.NATURE_ID) && hasNature(resource, MotwinProjectNature.NATURE_ID);
	}

	/**
	 * Checks if a {@link IResource} has a given project nature.
	 */
	public static boolean hasNature(IResource resource, String natureId) {
		if (resource != null && resource.isAccessible()) {
			IProject project = resource.getProject();
			if (project != null) {
				try {
					return project.hasNature(natureId);
				} catch (CoreException e) {
					StatusManager.getManager().handle(
							new Status(IStatus.ERROR, MotwinServerCorePlugin.PLUGIN_ID,
									"An error occurred inspecting project nature", e));
				}
			}
		}
		return false;
	}


}
