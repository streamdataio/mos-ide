/**
 * 
 */
package com.motwin.ide.server.cheatsheets.actions;

import com.motwin.ide.cheatsheets.actions.AbstractProjectAction;

/**
 * @author ctranxuan
 * 
 */
public abstract class AbstractServerProjectAction extends AbstractProjectAction {

    private static final String SRC_MAIN_JAVA_FOLDER      = "src/main/java";

    private static final String SRC_MAIN_RESOURCES_FOLDER = "src/main/resources";

    private static final String SRC_SPRING_FOLDER         = SRC_MAIN_RESOURCES_FOLDER + "/META-INF/spring";

    public String getSrcJavaFolder() {
        return SRC_MAIN_JAVA_FOLDER;
    }

    public String getSrcResourcesFolder() {
        return SRC_MAIN_RESOURCES_FOLDER;
    }

    public String getSrcSpringFolder() {
        return SRC_SPRING_FOLDER;
    }

    public String getSrcTestResourcesFolder() {
        return "src/test/resources";
    }
}
