/**
 * 
 */
package com.motwin.ide.html5.cheatsheets.samples.rssreader.actions;

import com.motwin.ide.html5.cheatsheets.actions.AbstractHTML5SampleAction;
import com.motwin.ide.html5.cheatsheets.samples.rssreader.internal.RSSReaderCheatSheetPlugin;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractRssReaderAction extends AbstractHTML5SampleAction {

    @Override
    public String getProjectName() {
        return "RssReader-HTML5";
    }

    @Override
    public String getPluginID() {
        return RSSReaderCheatSheetPlugin.PLUGIN_ID;
    }

    @Override
    public String getSampleCodePath() {
        return "code/rssreader";
    }
}
