/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.mylyn.commons.net.WebLocation;
import org.eclipse.mylyn.commons.net.WebUtil;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.io.Closeables;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author ctranxuan
 * 
 */
public class FeedsReader {

    private final Multimap<String, SyndFeed> feeds;

    public FeedsReader() {
        feeds = ArrayListMultimap.create();

    }

    public Collection<SyndFeed> readFeeds(final FeedDataSource aDataSource, final IProgressMonitor aMonitor) {
        Preconditions.checkNotNull(aDataSource, "aDataSource cannot be null");

        String dataSourceName;
        dataSourceName = aDataSource.getName();
        feeds.removeAll(dataSourceName);

        Collection<String> urls;
        urls = aDataSource.getUrls();

        SubMonitor monitor;
        monitor = SubMonitor.convert(aMonitor, urls.size());

        for (String url : urls) {

            try {
                SyndFeed feed;
                feed = readFeed(url, monitor);
                feeds.put(dataSourceName, feed);
                // TODO update cache
                // TODO use cache to retrieve in case of error

            } catch (IllegalArgumentException e) {
                StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
                        "failed to get the feed for the url [" + url
                            + "]: the associated stream is refused as a feed stream.", e));

            } catch (IOException e) {
                StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
                        "failed to get the feed for the url [" + url + "]: an I/O error occurred.", e));

            } catch (FeedException e) {
                StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID,
                        "failed to get the feed for the url [" + url + "]: a feed error occurred.", e));

            }
        }

        Collection<SyndFeed> result;
        result = getFeeds(dataSourceName);

        return result;

    }

    public Collection<SyndFeed> getFeeds(final String aDataSourceName, final IProgressMonitor aMonitor) {
        Preconditions.checkNotNull(aDataSourceName, "aDataSourceName cannot be null");

        Collection<SyndFeed> result;
        result = getFeeds(aDataSourceName);

        return result;
    }

    private Collection<SyndFeed> getFeeds(final String aDataSourceName) {
        Preconditions.checkNotNull(aDataSourceName, "aDataSourceName cannot be null");

        Collection<SyndFeed> result;
        Collection<SyndFeed> dataSourceFeeds;
        dataSourceFeeds = feeds.get(aDataSourceName);

        if (dataSourceFeeds == null) {
            result = ImmutableList.of();

        } else {
            result = ImmutableList.copyOf(dataSourceFeeds);

        }
        return result;
    }

    private SyndFeed readFeed(final String aUrl, final SubMonitor aMonitor) throws IOException,
            IllegalArgumentException, FeedException {
        Preconditions.checkNotNull(aUrl, "aUrl cannot be null");
        Preconditions.checkNotNull(aMonitor, "aMonitor cannot be null");

        SyndFeed syndFeed;
        syndFeed = null;

        InputStream inputStream;
        inputStream = null;

        try {
            inputStream = stream(aUrl, aMonitor.newChild(1));

            XmlReader reader;
            reader = new XmlReader(inputStream);

            SyndFeedInput input;
            input = new SyndFeedInput();

            syndFeed = input.build(reader);

        } finally {
            Closeables.closeQuietly(inputStream);

        }

        return syndFeed;
    }

    private InputStream stream(final String aUrl, final IProgressMonitor aMonitor) throws IOException {
        Preconditions.checkNotNull(aUrl, "aUrl cannot be null");

        InputStream inputStream;
        inputStream = null;

        WebLocation webLocation;
        webLocation = new WebLocation(aUrl);

        GetMethod method;
        method = new GetMethod(webLocation.getUrl());

        SubMonitor monitor;
        monitor = SubMonitor.convert(aMonitor);
        monitor.subTask(String.format("Fetching %s", webLocation.getUrl()));

        boolean success;
        success = false;

        try {
            HttpClient httpClient;
            httpClient = new HttpClient();
            WebUtil.configureHttpClient(httpClient, "");

            HostConfiguration hostConfig;
            hostConfig = WebUtil.createHostConfiguration(httpClient, webLocation, monitor);

            int result;
            result = WebUtil.execute(httpClient, hostConfig, method, monitor);

            if (result == HttpStatus.SC_OK) {
                inputStream = WebUtil.getResponseBodyAsStream(method, monitor);
                success = true;
            }

        } catch (IOException e) {
            throw new IOException("Failed to get a feed from url [" + aUrl + "]: an I/O error occurred", e);

        } finally {
            if (!success) {
                // TODO check if this shouldn't be released in any cases
                method.releaseConnection();
            }

            monitor.done();
        }

        return inputStream;
    }
}
