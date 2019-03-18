/**
 * 
 */
package com.motwin.ide.cheatsheets.helpers;

import java.io.File;
import java.io.FilenameFilter;

import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
final class ExcludeHiddenFilesFilter implements FilenameFilter {

    @Override
    public boolean accept(final File aDir, final String aName) {
        Preconditions.checkNotNull(aName, "aName cannot be null");

        boolean result = true;

        if (aName.startsWith(".")) {
            result = false;
        }

        return result;
    }
}
