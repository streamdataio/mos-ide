/**
 * 
 */
package com.motwin.ide.cheatsheets.exceptions;

/**
 * @author ctranxuan
 * 
 */
public class CheatSheetActionException extends Exception {
    private static final long serialVersionUID = -5681309993953024145L;

    /**
     * 
     */
    public CheatSheetActionException() {
        super();
    }

    /**
     * @param aMessage
     * @param aCause
     */
    public CheatSheetActionException(final String aMessage, final Throwable aCause) {
        super(aMessage, aCause);
    }

    /**
     * @param aMessage
     */
    public CheatSheetActionException(final String aMessage) {
        super(aMessage);
    }

    /**
     * @param aCause
     */
    public CheatSheetActionException(final Throwable aCause) {
        super(aCause);
    }

}
