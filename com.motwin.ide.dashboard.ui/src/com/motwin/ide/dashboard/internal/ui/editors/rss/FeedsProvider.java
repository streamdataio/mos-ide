/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import static com.motwin.ide.dashboard.ui.DashboardConstants.RSS_ALL_PRODUCTS;
import static com.motwin.ide.dashboard.ui.DashboardConstants.RSS_ALL_PRODUCTS_PREFERENCE;
import static com.motwin.ide.dashboard.ui.DashboardConstants.RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME;
import static com.motwin.ide.dashboard.ui.DashboardConstants.RSS_PRODUCTS_PREFERENCES_PREFIX;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.internal.ui.editors.rss.IFeedDataSourceListener.DataSourceChangedEvent;

/**
 * @author ctranxuan
 * 
 */
public enum FeedsProvider implements IPropertyChangeListener {
    INSTANCE;

    private static final String           DEFAULT_RSS_URL      = "https://getsatisfaction.com/motwin/topics.rss?sort=recently_active";
    private static final String           PRODUCT_RSS_ROOT_URL = "https://getsatisfaction.com/motwin/products/";

    private Map<String, FeedDataSource>   dataSources;
    private List<IFeedDataSourceListener> listeners;

    private FeedsProvider() {
        listeners = Lists.newArrayList();
        dataSources = Maps.newHashMap();
        initDefaultDataSource();

        getPreferenceStore().addPropertyChangeListener(this);
    }

    private void initDefaultDataSource() {
        Collection<String> availableTags;
        availableTags = FeedsUtil.getAvailableGSProductIDs();

        Collection<String> urls;
        urls = Lists.newArrayList();
        // Preconditions.checkState(availableTags.isEmpty(),
        // "availableTags cannot be empty");
        for (String tag : availableTags) {
            IPreferenceStore preferenceStore;
            preferenceStore = getPreferenceStore();

            boolean isSelected;
            isSelected = preferenceStore.getBoolean(FeedsUtil.computeGSProductPreference(tag));

            if (isSelected) {
                urls.add(toFeedUrl(tag));
            }
        }

        registerDefaultDataSource(urls);
    }

    public FeedDataSource getDataSource(final String aDataSourceName) {
        Preconditions.checkNotNull(aDataSourceName, "aDataSourceName cannot be null");

        return dataSources.get(aDataSourceName);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent anEvent) {
        Preconditions.checkNotNull(anEvent, "anEvent cannot be null");

        String property;
        property = anEvent.getProperty();

        if (RSS_ALL_PRODUCTS_PREFERENCE.equals(property)) {
            registerDefaultDataSource(ImmutableList.of(DEFAULT_RSS_URL));

        } else if (property != null && property.startsWith(RSS_PRODUCTS_PREFERENCES_PREFIX)) {
            FeedDataSource defaultDataSource;
            defaultDataSource = getDefaultDataSource();

            Preconditions.checkState(defaultDataSource != null, "defaultDataSource cannot be null");
            String tagUrl;
            tagUrl = toFeedUrl(FeedsUtil.computeGSProductID(property));

            boolean isSelected;
            isSelected = getPreferenceStore().getBoolean(property);

            Collection<String> urls;
            urls = Lists.newArrayList(defaultDataSource.getUrls());
            urls.remove(DEFAULT_RSS_URL);

            if (isSelected && !urls.contains(tagUrl)) {
                urls.add(tagUrl);

            } else {
                urls.remove(tagUrl);

            }

            registerDefaultDataSource(urls);
        }
    }

    private String toFeedUrl(final String aGSProductID) {
        Preconditions.checkNotNull(aGSProductID, "aGSProductID cannot be null");

        String url;

        if (RSS_ALL_PRODUCTS.equals(aGSProductID)) {
            url = DEFAULT_RSS_URL;

        } else {
            url = PRODUCT_RSS_ROOT_URL + aGSProductID + ".rss?sort=recently_created";

        }

        return url;
    }

    private IPreferenceStore getPreferenceStore() {
        return DashboardPlugin.getDefault().getPreferenceStore();
    }

    private FeedDataSource getDefaultDataSource() {
        return dataSources.get(RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME);
    }

    private void registerDefaultDataSource(final Collection<String> aUrls) {
        Preconditions.checkNotNull(aUrls, "aUrls cannot be null");

        FeedDataSource dataSource;
        dataSource = new FeedDataSource(RSS_DEFAULT_FEEDS_DATA_SOURCE_NAME, ImmutableList.copyOf(aUrls));

        registerDataSource(dataSource);
    }

    private void registerDataSource(final FeedDataSource aDataSource) {
        Preconditions.checkNotNull(aDataSource, "aDataSource cannot be null");

        dataSources.put(aDataSource.getName(), aDataSource);
        notifyDataSourceChanged(aDataSource);
    }

    private void notifyDataSourceChanged(final FeedDataSource aDataSource) {
        Preconditions.checkNotNull(aDataSource, "aDataSource cannot be null");

        for (IFeedDataSourceListener listener : listeners) {
            listener.dataSourceChanged(new DataSourceChangedEvent(aDataSource));
        }
    }

    public void addDataSourceListener(final IFeedDataSourceListener aListener) {
        Preconditions.checkNotNull(aListener, "aListener cannot be null");
        listeners.add(aListener);
    }

    public void removeDataSourceListener(final IFeedDataSourceListener aListener) {
        Preconditions.checkNotNull(aListener, "aListener cannot be null");
        listeners.remove(aListener);
    }
}
