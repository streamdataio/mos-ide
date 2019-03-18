/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import com.google.common.base.Preconditions;

/**
 * @author ctranxuan
 * 
 */
public interface IFeedDataSourceListener {

    public static class DataSourceChangedEvent {
        private final FeedDataSource feedDataSource;

        public DataSourceChangedEvent(final FeedDataSource aFeedDataSource) {
            Preconditions.checkNotNull(aFeedDataSource, "aFeedDataSource cannot be null");
            feedDataSource = aFeedDataSource;
        }

        public FeedDataSource getFeedDataSource() {
            return feedDataSource;
        }
    }

    public void dataSourceChanged(DataSourceChangedEvent anEvent);
}
