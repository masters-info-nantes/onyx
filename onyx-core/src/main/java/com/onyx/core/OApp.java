package com.onyx.core;

import java.net.URLClassLoader;

/**
 * Created by Maxime on 08/02/15.
 */
public class OApp {

    String appPackage;
    String appName;
    String appVersion;
    String appDescription;
    String appMainClass;
    URLClassLoader appClassLoader;


    public String getAppPackage() {
        return appPackage;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppDescription() {
        return appDescription;
    }
}
