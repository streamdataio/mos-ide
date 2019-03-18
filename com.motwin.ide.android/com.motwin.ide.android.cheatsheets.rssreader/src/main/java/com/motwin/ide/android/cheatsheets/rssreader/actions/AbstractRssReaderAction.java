/**
 * 
 */
package com.motwin.ide.android.cheatsheets.rssreader.actions;

import com.motwin.ide.android.cheatsheets.actions.AbstractAndroidProjectAction;
import com.motwin.ide.android.cheatsheets.rssreader.internal.RssReaderCheatSheetPlugin;
import com.motwin.ide.cheatsheets.actions.ISampleAction;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractRssReaderAction extends AbstractAndroidProjectAction implements ISampleAction {

    private static final String RSS_READER_APPLICATION_PROJECT_NAME = "RssReader-Android";

    private static final String SAMPLE_PROJECT_LOCATION_PATH        = "/code/rssreader";

    private static final String SAMPLE_BASE_PACKAGE                 = "com.motwin.sample.rssreader";
    
    @Override
    public String getProjectName() {
        return RSS_READER_APPLICATION_PROJECT_NAME;
    }

    @Override
    public String getPluginID() {
        return RssReaderCheatSheetPlugin.PLUGIN_ID;
    }

    @Override
    public String getSampleCodePath() {
        return SAMPLE_PROJECT_LOCATION_PATH;
    }
    
    @Override
    public String getJavaBasePackage() {
        return SAMPLE_BASE_PACKAGE;
    }
}
