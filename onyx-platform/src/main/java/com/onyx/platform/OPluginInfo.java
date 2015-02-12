package com.onyx.platform;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Maxime on 12/02/15.
 */
public class OPluginInfo {

    String pluginName;
    String pluginVersion;
    String pluginDescription;
    String pluginPackage;
    String pluginMainClass;
    URLClassLoader pluginClassLoader;
    URL pluginUrl;

    public void show() {
        System.out.println("plugin " + pluginName);
        System.out.println("  version "+pluginVersion);
        System.out.println("  description "+pluginDescription);
        System.out.println("  package "+pluginPackage);
        System.out.println("  mainCLass "+pluginMainClass);
        System.out.println("  classLoader ");
        for(int i=0; i < pluginClassLoader.getURLs().length; i++) {
            System.out.println("    url "+pluginClassLoader.getURLs()[i]);
        }
    }

}
