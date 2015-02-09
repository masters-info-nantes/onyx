package com.onyx.platform;

/**
 * Created by Maxime on 05/02/15.
 */
public abstract class OPlugin {

    String pluginName;
    String pluginVersion;
    String pluginDescription;
    String pluginPackage;

    protected abstract void onCreate();
}
