/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.ui.DashboardConstants;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * @author ctranxuan
 * 
 */
public final class FeedsUtil {
    private FeedsUtil() {
    }

    public static List<String> getAvailableGSProductIDs() {
        List<String> availableTags;

        String availableTagsPref;
        availableTagsPref = DashboardPlugin.getDefault().getPreferenceStore()
                .getString(DashboardConstants.RSS_AVAILABLE_PRODUCTS_LIST);

        if (Strings.isNullOrEmpty(availableTagsPref)) {
            availableTags = ImmutableList.of(DashboardConstants.RSS_ALL_PRODUCTS);

        } else {
            Iterable<String> rssTags;
            rssTags = Splitter.on(",").omitEmptyStrings().trimResults().split(availableTagsPref);

            if (Iterables.contains(rssTags, DashboardConstants.RSS_ALL_PRODUCTS)) {
                availableTags = ImmutableList.copyOf(rssTags);

            } else {
                availableTags = ImmutableList.<String> builder().add(DashboardConstants.RSS_ALL_PRODUCTS)
                        .addAll(rssTags).build();

            }
        }

        return availableTags;
    }

    public static String computeGSProductID(final String aProductPreference) {
        Preconditions.checkNotNull(aProductPreference, "aProductPreference cannot be null");

        return new String(aProductPreference.substring(aProductPreference.lastIndexOf('.') + 1));
    }

    public static String computeGSProductPreference(final String aGSProductID) {
        Preconditions.checkNotNull(aGSProductID, "aGSProductID cannot be null");

        return DashboardConstants.RSS_PRODUCTS_PREFERENCES_PREFIX + aGSProductID;
    }

    public static String removeHtmlEntities(final String aString) {
        Preconditions.checkNotNull(aString, "aString cannot be null");

        // it looks like obscure...

        StringBuilder result;
        result = new StringBuilder();

        boolean tagOpened = false;
        for (char currChar : aString.toCharArray()) {
            if (currChar == '<') {
                tagOpened = true;

            } else if (currChar == '>') {
                tagOpened = false;

            } else if (!tagOpened) {
                result.append(currChar);

            }
        }

        return StringEscapeUtils.unescapeHtml(result.toString());
    }

    public static String getDescription(final SyndEntry anEntry) {
        Preconditions.checkNotNull(anEntry, "anEntry cannot be null");

        String result;
        result = null;

        SyndContent content;
        content = anEntry.getDescription();

        if (content == null || content.getValue() == null) {
            result = "";

        } else {
            String value;
            value = content.getValue();

            if (value.startsWith("<form>")) {
                result = value;

            } else {
                result = removeHtmlEntities(value);

            }
        }

        return result;
    }
}
