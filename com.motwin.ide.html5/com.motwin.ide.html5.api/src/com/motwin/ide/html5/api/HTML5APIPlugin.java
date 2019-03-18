package com.motwin.ide.html5.api;

import java.util.Map;

import org.eclipse.core.runtime.Plugin;

import com.google.common.collect.ImmutableMap;
import com.motwin.ide.html5.api.containers.MotwinSDKJsGlobalScopeContainerInitializer;

public class HTML5APIPlugin extends Plugin {

    public static final String PLUGIN_ID = "com.motwin.ide.html5.api";

    public static Map<String, String> getContainers() {
        return ImmutableMap.<String, String> of(MotwinSDKJsGlobalScopeContainerInitializer.DESCRIPTION,
                MotwinSDKJsGlobalScopeContainerInitializer.CONTAINER_ID);
    }
}
