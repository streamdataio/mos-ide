/**
 * 
 */
package com.motwin.ide.docs;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;

/**
 * A delegate of {@link IClasspathContainer} instance.
 */
class UpdatedClasspathContainer implements IClasspathContainer {

    /** The container. */
    private final IClasspathContainer container;

    /** The classpath entries. */
    private final IClasspathEntry[]   classpathEntries;

    /**
     * Instantiates a new updated classpath container.
     * 
     * @param aContainer
     *            the container
     * @param aClasspathEntries
     *            the classpath entries
     */
    public UpdatedClasspathContainer(final IClasspathContainer aContainer, final IClasspathEntry[] aClasspathEntries) {
        container = aContainer;
        classpathEntries = aClasspathEntries;
    }

    /**
     * Gets the classpath entries.
     * 
     * @return the classpath entries
     * @see org.eclipse.jdt.core.IClasspathContainer#getClasspathEntries()
     */
    @Override
    public IClasspathEntry[] getClasspathEntries() {
        return classpathEntries;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     * @see org.eclipse.jdt.core.IClasspathContainer#getDescription()
     */
    @Override
    public String getDescription() {
        return container.getDescription();
    }

    /**
     * Gets the kind.
     * 
     * @return the kind
     * @see org.eclipse.jdt.core.IClasspathContainer#getKind()
     */
    @Override
    public int getKind() {
        return container.getKind();
    }

    /**
     * Gets the path.
     * 
     * @return the path
     * @see org.eclipse.jdt.core.IClasspathContainer#getPath()
     */
    @Override
    public IPath getPath() {
        return container.getPath();
    }

}