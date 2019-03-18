/**
 * 
 */
package com.motwin.ide.server.core.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * @author fbou
 *
 */
public class MotwinProjectNature implements IProjectNature {

	public static final String NATURE_ID = "com.motwin.ide.server.core.motwinProjectNature";

	private IProject project;

	@Override
	public void configure() throws CoreException {

	}

	@Override
	public void deconfigure() throws CoreException {

	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject aProject) {
		project = aProject;
	}


}
