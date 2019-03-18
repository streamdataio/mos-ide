/**
 * 
 */
package com.motwin.ide.server.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.google.common.collect.Lists;
import com.motwin.ide.server.core.nature.MotwinProjectNature;
import com.motwin.ide.server.core.nature.NatureUtils;

/**
 * @author fbou
 * 
 */
public class ConvertToMotwinProject implements IObjectActionDelegate {

    private final List<IProject> selected = new ArrayList<IProject>();

    @Override
    public void run(IAction aAction) {
        Iterator<IProject> iter = selected.iterator();
        while (iter.hasNext()) {
            IProject project = iter.next();
            if (NatureUtils.isMotwinPlatformProject(project)) {
                removeFacetsFromProject(project);
            } else {
                addFacetsToProject(project);
            }
        }
    }

    @Override
    public void selectionChanged(IAction aAction, ISelection aSelection) {
        selected.clear();
        if (aSelection instanceof IStructuredSelection) {
            boolean enabled = true;
            Iterator<?> iter = ((IStructuredSelection) aSelection).iterator();
            while (iter.hasNext()) {
                Object obj = iter.next();
                if (obj instanceof IJavaProject) {
                    obj = ((IJavaProject) obj).getProject();
                }
                if (obj instanceof IProject) {
                    IProject project = (IProject) obj;
                    if (!project.isOpen()) {
                        enabled = false;
                        break;
                    } else {
                        selected.add(project);
                    }
                } else {
                    enabled = false;
                    break;
                }
            }
            aAction.setEnabled(enabled);
        }
    }

    @Override
    public void setActivePart(IAction aAction, IWorkbenchPart aTargetPart) {
    }

    public static void addFacetsToProject(final IProject aProject) {
        IWorkspaceRunnable oper = new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                IProjectDescription description = aProject.getDescription();
                List<String> newNatureIds = Lists.newArrayList(description.getNatureIds());
                newNatureIds.add(MotwinProjectNature.NATURE_ID);
                description.setNatureIds(newNatureIds.toArray(new String[newNatureIds.size()]));
                aProject.setDescription(description, monitor);
            }
        };

        try {
            ResourcesPlugin.getWorkspace().run(oper, new NullProgressMonitor());
        } catch (CoreException e) {
        }
    }

    public static void removeFacetsFromProject(final IProject aProject) {
        IWorkspaceRunnable oper = new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                IProjectDescription description = aProject.getDescription();
                List<String> newNatureIds = Lists.newArrayList(description.getNatureIds());
                newNatureIds.remove(MotwinProjectNature.NATURE_ID);
                description.setNatureIds(newNatureIds.toArray(new String[newNatureIds.size()]));
                aProject.setDescription(description, monitor);
            }
        };

        try {
            ResourcesPlugin.getWorkspace().run(oper, new NullProgressMonitor());
        } catch (CoreException e) {
        }
    }

}
