package com.onyx.platform;

import com.onyx.platform.errors.OPluginNotRunnableException;

import java.net.URLClassLoader;

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

    protected abstract void onStop();

    public OPlatform getPlatform() {
        return platform;
    }

    public OPluginProperty getInfos() {
        return infos;
    }

    public URLClassLoader getURLClassLoader() {
        return infos.classLoader;
    }

    public OPlugin runPlugin(OPluginProperty pluginProperty) throws OPluginNotRunnableException {
        return platform.runPlugin(pluginProperty, infos.classLoader);
    }

}
