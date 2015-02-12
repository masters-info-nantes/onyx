package com.onyx.platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Maxime on 05/02/15.
 */
public class OPlatform {

    private Map<String, OPluginInfo> plugins = new HashMap<String, OPluginInfo>();

    public OPlatform() {
        loadAllPluginsInfo();
    }

    public void loadAllPluginsInfo() {
        File dirContent = new File("target/plugins");
        System.out.println(dirContent.getAbsolutePath());
        File[] dirfiles = dirContent.listFiles();
        for (File file : dirfiles) {
            String extension = "";
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i + 1);
            }
            if(extension.equals("jar")) {
                try {
                    loadPluginInfo(file.toURL());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadPluginInfo(String url) {
        try {
            loadPluginInfo(new URL("file://" + url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPluginInfo(URL url) {
        URL[] urls = new URL[]{url};
        URLClassLoader classLoader = URLClassLoader.newInstance(urls, OPlugin.class.getClassLoader());

        InputStream is = classLoader.getResourceAsStream("manifest.oplugin");
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(url.getPath());

        OPluginInfo plugin = new OPluginInfo();
        plugin.pluginName = p.getProperty("name");
        System.out.print(plugin.pluginName);
        plugin.pluginVersion = p.getProperty("version");
        plugin.pluginDescription = p.getProperty("description");
        plugin.pluginPackage = p.getProperty("package");
        plugin.pluginClassLoader = classLoader;
        plugin.pluginMainClass = p.getProperty("mainClass");
        plugin.pluginUrl = url;
        plugins.put(plugin.pluginPackage,plugin);
    }

    public void loadPlugin(OPluginInfo plugin) {
        loadPlugin(plugin, plugin);
    }

    public void loadPlugin(OPluginInfo plugin, OPluginInfo pluginDependency) {
        if(plugin != pluginDependency) {
            URL[] urls = new URL[]{plugin.pluginUrl};
            plugin.pluginClassLoader = URLClassLoader.newInstance(urls, pluginDependency.pluginClassLoader);
        }

        Class<?> cl = null;

        try {
            cl = Class.forName(plugin.pluginMainClass, false, plugin.pluginClassLoader);
            System.out.print(cl);
            if (OPlugin.class.isAssignableFrom(cl)) {
                OPlugin p = (OPlugin) cl.newInstance();
                p.platform = this;
                p.infos = plugin;
                p.onCreate();
            } else {
                throw new IllegalAccessException("Class must be subclass of OPlugin");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Map<String, OPluginInfo> getPlugins() {
        return plugins;
    }

    public OPluginInfo getPlugin(String name) {
        return plugins.get(name);
    }

}
