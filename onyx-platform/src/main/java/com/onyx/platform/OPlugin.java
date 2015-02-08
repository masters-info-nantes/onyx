package com.onyx.platform;

/**
 * Created by Maxime on 05/02/15.
 */
public abstract class OPlugin {

    String name;
    String version;
    String description;

    protected abstract void onCreate();
}
