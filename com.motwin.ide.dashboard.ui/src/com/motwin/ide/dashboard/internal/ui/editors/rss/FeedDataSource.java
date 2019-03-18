/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * @author ctranxuan
 * 
 */
public class FeedDataSource {
    private final String             name;
    private final Collection<String> urls;

    /**
     * @param aName
     * @param aUrls
     */
    public FeedDataSource(final String aName, final Collection<String> aUrls) {
        Preconditions.checkNotNull(aName, "aName cannot be null");
        Preconditions.checkNotNull(aUrls, "aUrls cannot be null");

        name = aName;
        urls = ImmutableList.copyOf(aUrls);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the urls
     */
    public Collection<String> getUrls() {
        return urls;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((urls == null) ? 0 : urls.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FeedDataSource other = (FeedDataSource) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (urls == null) {
            if (other.urls != null) {
                return false;
            }
        } else if (!urls.equals(other.urls)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FeedDataSource [name=");
        builder.append(name);
        builder.append(", urls=");
        builder.append(Iterables.toString(urls));
        builder.append("]");
        return builder.toString();
    }

}
