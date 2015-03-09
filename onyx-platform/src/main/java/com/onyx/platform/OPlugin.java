package com.onyx.platform;

/**
 * Created by Maxime on 05/02/15.
 */
public abstract class OPlugin {

    OPlatform platform;
    OPluginProperty infos;

    public OPlugin(){}

    private OPlugin(OPlatform platform){
        this.platform = platform;
    }

    protected abstract void onCreate();

    public OPlatform getPlatform() {
        return platform;
    }

    public OPluginProperty getInfos() {
        return infos;
    }

    public void loadPlugin(String name) {
        platform.runPlugin(platform.getPlugin(name), this.getInfos());
    }
}
