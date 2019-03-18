/**
 * 
 */
package com.motwin.ide.dashboard.internal.ui.editors.rss;

import java.util.Comparator;
import java.util.Date;

import com.sun.syndication.feed.synd.SyndEntry;

public class SyndEntryComparator implements Comparator<SyndEntry> {

    @Override
    public int compare(final SyndEntry o1, final SyndEntry o2) {
        Date date1 = new Date(0);
        Date date2 = new Date(0);

        if (o1.getUpdatedDate() != null) {
            date1 = o1.getUpdatedDate();

        } else if (o1.getPublishedDate() != null) {
            date1 = o1.getPublishedDate();

        }

        if (o2.getUpdatedDate() != null) {
            date2 = o2.getUpdatedDate();

        } else if (o2.getPublishedDate() != null) {
            date2 = o2.getPublishedDate();

        }

        return -1 * date1.compareTo(date2);
    }

}