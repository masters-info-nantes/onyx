package com.onyx.platform;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Created by Maxime on 12/02/15.
 */
public class OPluginProperty {

    String pluginName;
    String pluginVersion;
    String pluginDescription;
    String pluginPackage;
    String pluginMainClass;
    URLClassLoader pluginClassLoader;
    List<OPluginProperty> dependencies;
    URL pluginUrl;

    public void show() {
        System.out.println("plugin " + getPluginName());
        System.out.println("  version "+ getPluginVersion());
        System.out.println("  description "+ getPluginDescription());
        System.out.println("  package "+ getPluginPackage());
        System.out.println("  mainCLass "+pluginMainClass);
        System.out.println("  classLoader ");
        for(int i=0; i < pluginClassLoader.getURLs().length; i++) {
            System.out.println("    url "+pluginClassLoader.getURLs()[i]);
        }
    }


    public String getPluginName() {
        return pluginName;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public String getPluginDescription() {
        return pluginDescription;
    }

    public String getPluginPackage() {
        return pluginPackage;
    }
}
