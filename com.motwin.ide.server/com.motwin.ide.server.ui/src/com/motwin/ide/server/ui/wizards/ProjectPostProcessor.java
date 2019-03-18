/**
 * 
 */
package com.motwin.ide.server.ui.wizards;

import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants2;

import com.motwin.ide.server.docs.ServerJavaDocs;

class ProjectPostProcessor {
    private static final String SPRING_CONFIG_EDITOR_ID = "com.springsource.sts.config.ui.editors.SpringConfigEditor";

    public static void postProcess(final IJavaProject aProject) {
        attachJavadoc(aProject);
        openSpringPerspective();
    }

    private static void attachJavadoc(final IJavaProject aProject) {
        Job job = new WorkspaceJob("Attach Javadoc Job") {

            @Override
            public IStatus runInWorkspace(final IProgressMonitor aMonitor) throws CoreException {
                new ServerJavaDocs().attachJavadoc(aProject);
                return Status.OK_STATUS;
            }
        };

        IWorkspace workspace;
        workspace = ResourcesPlugin.getWorkspace();

        IResourceRuleFactory ruleFactory;
        ruleFactory = workspace.getRuleFactory();

        ISchedulingRule rule;
        rule = new MultiRule(new ISchedulingRule[] { ruleFactory.createRule(aProject.getResource()),
                ruleFactory.modifyRule(aProject.getResource()) });

        job.setProperty(IProgressConstants2.SHOW_IN_TASKBAR_ICON_PROPERTY, Boolean.TRUE);
        job.setPriority(Job.BUILD);
        job.setRule(rule);
        job.schedule(12000); // start 12s to ensure other classpath jobs been
                             // performed (bug-9294)
    }

    private static void openSpringPerspective() {
        IEditorRegistry registry;
        registry = PlatformUI.getWorkbench().getEditorRegistry();
        registry.setDefaultEditor("*.xml", SPRING_CONFIG_EDITOR_ID);
    }
}