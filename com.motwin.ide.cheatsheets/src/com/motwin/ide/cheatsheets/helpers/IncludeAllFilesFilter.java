/**
 * 
 */
package com.motwin.ide.cheatsheets.helpers;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author ctranxuan
 * 
 */
final class IncludeAllFilesFilter implements FilenameFilter {

    @Override
    public boolean accept(final File aDir, final String aName) {
        return true;
    }

}
