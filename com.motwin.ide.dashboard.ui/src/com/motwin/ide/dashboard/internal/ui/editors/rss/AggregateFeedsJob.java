/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.progress.IProgressConstants;

import com.google.common.base.Preconditions;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.ui.DashboardImages;

/**
 * @author ctranxuan
 * 
 */
public class AggregateFeedsJob extends Job {
    public static final Object   CONTENT_FAMILY = new Object();

    private final FeedDataSource feedDataSource;
    private final FeedsReader    feedsReader;

    /**
     * @param aFeedName
     */
    public AggregateFeedsJob(final FeedDataSource aFeedDataSource) {
        super("Downloading RSS feeds");
        setProperty(IProgressConstants.ICON_PROPERTY, DashboardImages.RSS);

        Preconditions.checkNotNull(aFeedDataSource, "aFeedDataSource cannot be null");
        Preconditions.checkNotNull(aFeedDataSource.getName(), "aFeedDataSource.getName() cannot be null");

        feedDataSource = aFeedDataSource;
        feedsReader = new FeedsReader();
    }

    @Override
    public boolean belongsTo(final Object aFamily) {
        return CONTENT_FAMILY == aFamily;
    }

    public boolean isCoveredBy(final AggregateFeedsJob anOther) {
        Preconditions.checkNotNull(anOther, "anOther cannot be null");

        boolean result;
        result = (feedDataSource.getName().equals(anOther.feedDataSource.getName()));

        return result;
    }

    @Override
    protected IStatus run(final IProgressMonitor aMonitor) {
        IStatus status;
        status = null;

        synchronized (getClass()) {
            if (aMonitor.isCanceled()) {
                status = Status.CANCEL_STATUS;

            } else {
                cancelAnyOtherSameJobs();

                if (aMonitor.isCanceled()) {
                    status = Status.CANCEL_STATUS;

                } else {
                    final CountDownLatch resultLatch;
                    resultLatch = new CountDownLatch(1);

                    Runnable downloadRunnable;
                    downloadRunnable = new Runnable() {

                        @Override
                        public void run() {
                            feedsReader.readFeeds(feedDataSource, aMonitor);
                            resultLatch.countDown();
                        }
                    };

                    new Thread(downloadRunnable).start();

                    try {
                        if (resultLatch.await(30, TimeUnit.SECONDS)) {
                            status = Status.OK_STATUS;

                        }
                    } catch (InterruptedException e) {
                        status = Status.CANCEL_STATUS;
                        StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
                                "an unexpected error occurred while retrieving the feeds of the dataSource [name="
                                    + feedDataSource.getName() + "]", e));
                    }
                }
            }
        }

        return status;
    }

    private void cancelAnyOtherSameJobs() {
        Job[] buildJobs;
        buildJobs = Job.getJobManager().find(CONTENT_FAMILY);

        for (Job job : buildJobs) {
            if (shallCancel(job)) {
                job.cancel();
            }
        }
    }

    private boolean shallCancel(final Job aJob) {
        Preconditions.checkNotNull(aJob, "aJob cannot be null");

        boolean result;
        result = (aJob != this && aJob instanceof AggregateFeedsJob && isCoveredBy((AggregateFeedsJob) aJob));

        return result;
    }

    /**
     * @return the feedsReader
     */
    public FeedsReader getFeedsReader() {
        return feedsReader;
    }
}
