package com.onyx.platform;

import java.net.URLClassLoader;

/**
 * Created by Maxime on 05/02/15.
 */
public abstract class OPlugin {

    OPlatform platform;
    OPluginInfo infos;

    public OPlugin(){}

    private OPlugin(OPlatform platform){
        this.platform = platform;
    }

    protected abstract void onCreate();

    public OPlatform getPlatform() {
        return platform;
    }

    public OPluginInfo getInfos() {
        return infos;
    }

    public void loadPlugin(String name) {
        platform.loadPlugin(platform.getPlugin(name), this.getInfos());
    }
}
