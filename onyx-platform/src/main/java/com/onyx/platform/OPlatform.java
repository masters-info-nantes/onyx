package com.onyx.platform;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Maxime on 05/02/15.
 */
public class OPlatform {

    private Map<String, OPlugin> activePlugins = new HashMap<String, OPlugin>();

    public void loadAllPlugins() throws Exception{
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
                loadPlugin(file.toURL());
            }
        }
    }

    public void loadPlugin(String url) throws Exception {
        loadPlugin(new URL("file://"+url));
    }

    public void loadPlugin(URL url) throws Exception {
        URL[] urls = new URL[]{url};
        URLClassLoader classLoader = URLClassLoader.newInstance(urls, OPlugin.class.getClassLoader());

        InputStream is = classLoader.getResourceAsStream("manifest.oplugin");
        Properties p = new Properties();
        p.load(is);

        System.out.println(url.getPath());

         Class<?> cl = Class.forName(p.getProperty("mainClass"), false, classLoader);
        if (OPlugin.class.isAssignableFrom(cl)) {
            OPlugin plugin = (OPlugin) cl.newInstance();
            plugin.pluginName = p.getProperty("name");
            plugin.pluginVersion = p.getProperty("version");
            plugin.pluginDescription = p.getProperty("description");
            plugin.pluginPackage = p.getProperty("package");
            activePlugins.put(plugin.pluginPackage,plugin);
            plugin.onCreate();
        } else {
            throw new IllegalAccessException("La classe doit être une sous-classe de OPlugin");
        }
    }

    public Map<String, OPlugin> getActivePlugins() {
        return activePlugins;
    }

}
