/**
 * 
 */
package com.motwin.ide.docs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.core.utils.CoreUtil;
import com.motwin.ide.docs.exceptions.JavadocException;

/**
 * Abstract class that defines the default behaviour to attach javadocs to a
 * given java project.
 * 
 * @author ctranxuan
 * 
 */
public abstract class AbstractJavaDocs {
    private final static String JAVADOC_PATH      = "docs/javadoc";
    private final static String JAVADOC_EXTENSION = "-sources.jar";

    private Map<String, File>   javadocs;

    public AbstractJavaDocs() {
    }

    /**
     * Attach javadoc to the {@link IClasspathEntry}s of the given project if
     * ever a corresponding javadoc is found.
     * 
     * @param aProject
     *            the java project
     */
    public void attachJavadoc(final IJavaProject aProject) {
        Preconditions.checkNotNull(aProject, "aProject cannot be null");

        try {
            IClasspathEntry[] classpathEntries;
            classpathEntries = aProject.getRawClasspath();

            for (IClasspathEntry classpathEntry : classpathEntries) {

                if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
                    IClasspathContainer container;
                    container = JavaCore.getClasspathContainer(classpathEntry.getPath(), aProject);

                    attachJavadoc(aProject, container);
                }
            }

        } catch (JavaModelException e) {
            StatusHandler
                    .log(new Status(IStatus.ERROR, getPluginID(),
                            "Failed to attach the Motwin javadoc for the project [name=" + aProject.getElementName()
                                + "].", e));
        }
    }

    /**
     * Attach javadoc to the {@link IClasspathEntry}s of the given project for
     * the given classpath container if ever a corresponding javadoc is found.
     * 
     * @param aProject
     *            the java project
     * @param aContainer
     *            the container
     */
    protected void attachJavadoc(final IJavaProject aProject, final IClasspathContainer aContainer) {
        Preconditions.checkNotNull(aProject, "aProject cannot be null");
        Preconditions.checkNotNull(aContainer, "aContainer cannot be null");
        try {
            IClasspathEntry[] classpathEntries;
            classpathEntries = aContainer.getClasspathEntries();

            IClasspathEntry[] newClasspathEntries;
            newClasspathEntries = new IClasspathEntry[classpathEntries.length];

            boolean requestClasspathContainerUpdate;
            requestClasspathContainerUpdate = false;

            for (int i = 0; i < classpathEntries.length; i++) {
                newClasspathEntries[i] = attachJavadoc(classpathEntries[i]);

                requestClasspathContainerUpdate = requestClasspathContainerUpdate
                    || (newClasspathEntries[i] != classpathEntries[i]);
            }

            if (requestClasspathContainerUpdate) {
                UpdatedClasspathContainer newContainer;
                newContainer = new UpdatedClasspathContainer(aContainer, newClasspathEntries);

                ClasspathContainerInitializer initializer;
                initializer = JavaCore.getClasspathContainerInitializer(newContainer.getPath().segment(0));

                if (initializer != null) {
                    initializer.requestClasspathContainerUpdate(newContainer.getPath(), aProject, newContainer);
                }
            }

        } catch (CoreException e) {
            StatusHandler.log(new Status(IStatus.ERROR, getPluginID(),
                    "Failed to attach the javadoc for the project [name=" + aProject.getElementName() + "]", e));
        }
    }

    /**
     * Return a new {@link IClasspathEntry} with the attached javadoc to the
     * given {@link IClasspathEntry} or the unchanged given classpath entry if
     * no corresponding javadoc has been found. .
     * 
     * @param aClasspathEntry
     *            the classpath entry
     * @return the new classpath entry with the attached javadoc or the original
     *         one if no javadoc has been found
     */
    protected IClasspathEntry attachJavadoc(final IClasspathEntry aClasspathEntry) {
        Preconditions.checkNotNull(aClasspathEntry, "aClasspathEntry cannot be null");

        IClasspathEntry classpathEntry;
        classpathEntry = aClasspathEntry;

        try {
            File javaDoc;
            javaDoc = getJavaDoc(aClasspathEntry);

            if (javaDoc != null && javaDoc.exists()) {
                IPath javaDocPath;
                javaDocPath = Path.fromOSString(javaDoc.getAbsolutePath());

                // @formatter:off
            classpathEntry = JavaCore.newLibraryEntry(
                    aClasspathEntry.getPath(), 
                    javaDocPath, 
                    null,
                    aClasspathEntry.getAccessRules(), 
                    aClasspathEntry.getExtraAttributes(),
                    aClasspathEntry.isExported());
            // @formatter:on
            }

        } catch (JavadocException e) {
            StatusHandler.log(new Status(IStatus.WARNING, getPluginID(),
                    "Cannot attach any javadoc for the classpath entry [" + aClasspathEntry.getPath()
                        + "]: no javadoc found.", e));
        }

        return classpathEntry;
    }

    private File getJavaDoc(final IClasspathEntry aClasspathEntry) throws JavadocException {
        Preconditions.checkNotNull(aClasspathEntry, "aClasspathEntry cannot be null");

        String artifactName;
        artifactName = getArtifactName(aClasspathEntry);

        File file;
        file = getJavadocs().get(artifactName + JAVADOC_EXTENSION);

        return file;
    }

    private String getArtifactName(final IClasspathEntry aClasspathEntry) {
        Preconditions.checkNotNull(aClasspathEntry, "aClasspathEntry cannot be null");

        IPath path;
        path = aClasspathEntry.getPath().removeFileExtension();

        String artifactName;
        artifactName = path.segment(path.segmentCount() - 1);

        return artifactName;
    }

    /**
     * Lazy instanciation
     * 
     * @return the javadocs
     */
    private Map<String, File> getJavadocs() {
        if (javadocs == null) {
            ImmutableMap.Builder<String, File> builder;
            builder = ImmutableMap.builder();

            File javadocParent;
            javadocParent = null;

            try {
                javadocParent = CoreUtil.locateResource(getPluginID(), getJavadocPath());

                if (javadocParent != null && javadocParent.exists()) {
                    locateJavaDocs(javadocParent, builder);
                }

            } catch (URISyntaxException e) {
                StatusHandler.log(new Status(IStatus.WARNING, getPluginID(),
                        "Unable to locate the javadocs parent folder [name=" + getJavadocPath()
                            + "]: an URI exception occured", e));
            } catch (IOException e) {
                StatusHandler.log(new Status(IStatus.WARNING, getPluginID(),
                        "Unable to locate the javadocs parent folder  [name=" + getJavadocPath()
                            + "]: a I/O exception occured", e));
            }

            javadocs = builder.build();

        }

        return javadocs;
    }

    private void locateJavaDocs(final File aParent, final Builder<String, File> aBuilder) {
        Preconditions.checkNotNull(aParent, "aParent cannot be null");
        Preconditions.checkNotNull(aBuilder, "aBuilder cannot be null");

        File[] files;
        files = aParent.listFiles();

        for (File file : files) {

            if (file.isDirectory()) {
                locateJavaDocs(file, aBuilder);

            } else {
                aBuilder.put(file.getName(), file);
            }
        }
    }

    protected String getJavadocPath() {
        return JAVADOC_PATH;
    }

    protected abstract String getPluginID();
}
