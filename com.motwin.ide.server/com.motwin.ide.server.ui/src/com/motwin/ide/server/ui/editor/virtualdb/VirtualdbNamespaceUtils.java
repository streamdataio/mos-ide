/**
 * 
 */
package com.motwin.ide.server.ui.editor.virtualdb;

/**
 * @author fbou
 * 
 */
public class VirtualdbNamespaceUtils {

    public final static String DOC_URL                      = null;

    public final static String VIRTUALDB_TAG                = "virtualdb";
    public final static String TABLE_TAG                    = "table";
    public final static String SCHEMA_TAG                   = "schema";
    public final static String COLUMN_TAG                   = "column";
    public final static String SOURCE_TAG                   = "source";

    public final static String TABLE_PERSISTENT_ATTRIBUTE   = "persistent";
    public final static String COLUMN_PART_OF_KEY_ATTRIBUTE = "part-of-key";
    public final static String SOURCE_REF_ATTRIBUTE         = "ref";
    public final static String TABLE_NAME_ATTRIBUTE         = "name";

    public static boolean isTrue(String aBooleanString, boolean aDefaultValue) {
        boolean value;
        if (aBooleanString != null && aBooleanString.equals("true")) {
            value = true;
        } else if (aBooleanString != null && aBooleanString.equals("false")) {
            value = false;
        } else {
            value = aDefaultValue;
        }
        return value;
    }

}
