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

    public static void main (String[] args) throws Exception{
        OPlatform platform = new OPlatform();
        platform.loadAllPlugins();
        for(Map.Entry<String, OPlugin> entry : platform.getActivePlugins().entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
            OPlugin value = entry.getValue();
            System.out.println(value);
        }
        System.out.println("ok :)");
    }

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
            if(extension.equals(".jar")) {
                loadPlugin(file.toURL());
            }
            loadPlugin(file.toURL());
        }
    }

    public void loadPlugin(String url) throws Exception {
        loadPlugin(new URL("file://"+url));
    }

    public void loadPlugin(URL url) throws Exception {
        URL[] urls = new URL[]{url};
        URLClassLoader classLoader = URLClassLoader.newInstance(urls, OPlugin.class.getClassLoader());

        InputStream is = classLoader.getResourceAsStream("manifest.plugin");
        Properties p = new Properties();
        p.load(is);

        System.out.println(url.getPath());

         Class<?> cl = Class.forName(p.getProperty("class"), false, classLoader);
        if (OPlugin.class.isAssignableFrom(cl)) {
            OPlugin plugin = (OPlugin) cl.newInstance();
            plugin.name = p.getProperty("name");
            plugin.name = p.getProperty("version");
            plugin.name = p.getProperty("description");
            activePlugins.put(cl.getPackage().getName(),plugin);
            plugin.onCreate();
        } else {
            throw new IllegalAccessException("La classe doit être une sous-classe de OPlugin");
        }
    }

    public Map<String, OPlugin> getActivePlugins() {
        return activePlugins;
    }

}
