/**
 * 
 */
package com.motwin.ide.docs.exceptions;

/**
 * @author ctranxuan
 * 
 */
public class JavadocException extends Exception {

    private static final long serialVersionUID = 1400305951193077705L;

    /**
     * 
     */
    public JavadocException() {
        super();
    }

    /**
     * @param aMessage
     * @param aCause
     */
    public JavadocException(final String aMessage, final Throwable aCause) {
        super(aMessage, aCause);
    }

    /**
     * @param aMessage
     */
    public JavadocException(final String aMessage) {
        super(aMessage);
    }

    /**
     * @param aCause
     */
    public JavadocException(final Throwable aCause) {
        super(aCause);
    }

}
