package com.onyx.core;

import com.onyx.platform.OPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Maxime on 08/02/15.
 */
public class OAppManager {

    private Map<String, OApp> apps = new HashMap<String, OApp>();

    public void loadAllApps() {
        File dirContent = new File("target/apps");
        File[] dirfiles = dirContent.listFiles();
        for (File file : dirfiles) {
            String extension = "";
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i + 1);
            }
            if(extension.equals("jar")) {
                try {
                    addApp(file.toURL());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void addApp(String url) {
        try {
            addApp(new URL("file://" + url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void addApp(URL url) {
        URL[] urls = new URL[]{url};
        URLClassLoader classLoader = URLClassLoader.newInstance(urls, OAppManager.class.getClassLoader());

        InputStream is = classLoader.getResourceAsStream("manifest.oapp");
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OApp app = new OApp();
        app.appName = p.getProperty("name");
        app.appVersion = p.getProperty("version");
        app.appDescription = p.getProperty("description");
        app.appPackage = p.getProperty("package");
        app.appMainClass = p.getProperty("mainClass");
        app.appClassLoader = classLoader;

        apps.put(p.getProperty("package"), app);

    }

    public void run(OApp app) {

        Class<?> cl = null;
        try {
            cl = Class.forName(app.appMainClass, false, app.appClassLoader);
            if (OActivity.class.isAssignableFrom(cl)) {
                OActivity activity = (OActivity) cl.newInstance();
                activity.appManager = this;
                activity.onCreate();
            } else {
                throw new IllegalAccessException("Class must be subclass of OActivity");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public Map<String, OApp> getApps() {
        return apps;
    }

    public OApp getApp(String name) {
        return apps.get(name);
    }
}
