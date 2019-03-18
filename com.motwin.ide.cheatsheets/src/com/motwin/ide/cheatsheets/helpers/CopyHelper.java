/**
 * 
 */
package com.motwin.ide.cheatsheets.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;

import com.google.common.base.Preconditions;
import com.google.common.io.PatternFilenameFilter;

/**
 * @author ctranxuan
 * 
 */
public final class CopyHelper {

    public final static class CopyHelperBuilder {
        private File           from;
        private IContainer     to;
        private boolean        force;

        private FilenameFilter includeFilesFilter;
        private FilenameFilter excludeFilesFilter;

        public CopyHelperBuilder() {
            force = true;
        }

        public CopyHelperBuilder from(final File aFrom) {
            Preconditions.checkNotNull(aFrom, "aFrom cannot be null");
            Preconditions.checkArgument(aFrom.isDirectory(), "aFrom must be a directory");

            from = aFrom;
            return this;
        }

        public CopyHelperBuilder force(final boolean aForce) {
            force = aForce;
            return this;
        }

        public CopyHelperBuilder includeFiles(final String aRegexp) {
            includeFilesFilter = new PatternFilenameFilter(aRegexp);
            return this;
        }

        public CopyHelperBuilder excludeFiles(final String aRegexp) {
            excludeFilesFilter = new PatternFilenameFilter(aRegexp);
            return this;
        }

        public CopyHelperBuilder to(final IFolder aFolder) {
            to = aFolder;
            return this;
        }

        public CopyHelperBuilder to(final IProject aProject, final String aFolderPath) {
            Preconditions.checkNotNull(aProject, "aProject cannot be null");
            Preconditions.checkNotNull(aFolderPath, "aFolderPath can be empty but not null");

            if (aFolderPath.isEmpty()) {
                to = aProject;

            } else {
                to = aProject.getFolder(aFolderPath);
            }

            return this;
        }

        public CopyHelperBuilder to(final IJavaProject aJavaProject, final String aProjectFolder) {
            Preconditions.checkNotNull(aJavaProject, "aJavaProject cannot be null");
            Preconditions.checkNotNull(aProjectFolder, "aProjectFolder can be empty but not be null");
            Preconditions.checkState(aJavaProject.getProject() != null, "aJavaProject.project cannot be null");

            to(aJavaProject.getProject(), aProjectFolder);
            return this;
        }

        public CopyHelper build() {
            Preconditions.checkState(from != null, "from (i.e. the source of the copy) cannot be null");
            Preconditions.checkState(from.exists(), "from '%s' (i.e. the source of the copy) must exist",
                    from.getAbsolutePath());

            Preconditions.checkState(to != null, "to (i.e. the destination folder of the copy) cannot be null");
            Preconditions.checkState(to.exists(), "to '%s' (i.e. the destination folder of the copy) must exist",
                    to.getFullPath());

            Preconditions.checkState(includeFilesFilter != null || excludeFilesFilter != null,
                    "includeFilesFilter and excludeFilesFilter cannot be both null");

            if (includeFilesFilter == null) {
                includeFilesFilter = new IncludeAllFilesFilter();
            }

            if (excludeFilesFilter == null) {
                excludeFilesFilter = new ExcludeNoneFilesFilter();
            }
            return new CopyHelper(this);
        }
    }

    private final File           from;
    private final IContainer     to;

    private final boolean        force;

    private final FilenameFilter includeFilesFilter;
    private final FilenameFilter excludeFilesFilter;

    private CopyHelper(final CopyHelperBuilder aBuilder) {
        from = aBuilder.from;
        to = aBuilder.to;
        force = aBuilder.force;

        includeFilesFilter = aBuilder.includeFilesFilter;
        excludeFilesFilter = aBuilder.excludeFilesFilter;
    }

    public void copy() throws IOException {
        copy(from, to);
    }

    private void copy(final File aFrom, final IContainer aTo) throws IOException {
        try {
            File[] children;
            children = aFrom.listFiles(new ExcludeHiddenFilesFilter());

            for (File child : children) {

                if (child.isDirectory()) {
                    String name;
                    name = child.getName();

                    IResource subFolder;
                    subFolder = aTo.findMember(name);

                    if (subFolder == null) {
                        IFolder folder;
                        folder = aTo.getFolder(new Path(name));
                        folder.create(IResource.FORCE, true, new NullProgressMonitor());

                        copy(child, folder);

                    } else {
                        copy(child, (IFolder) subFolder);

                    }

                } else if (isEligibleForCopy(child)) {
                    String name;
                    name = child.getName();

                    IPath path;
                    path = new Path(name);

                    IFile destFile;
                    destFile = aTo.getFile(path);

                    if (aTo.findMember(path) != null && force) {
                        destFile.delete(true, new NullProgressMonitor());
                    }

                    destFile.create(new FileInputStream(child), true, new NullProgressMonitor());
                }
            }

        } catch (CoreException e) {
            throw new IOException("A workspace error occured during the copy [src=" + aFrom.getAbsolutePath()
                + ", dest=" + aTo + "]", e);
        }
    }

    private boolean isEligibleForCopy(final File aFile) {
        Preconditions.checkNotNull(aFile, "aFile cannot be null");

        File parentFile;
        parentFile = aFile.getParentFile();

        String name;
        name = aFile.getName();

        return includeFilesFilter.accept(parentFile, name) && !excludeFilesFilter.accept(parentFile, name);
    }
}
