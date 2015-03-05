package com.onyx.platform;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
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
        loadDefaultPlugins();
    }

    public void loadDefaultPlugins()
    {
        File file = new File("target/default-plugins.xml");

        if(!file.exists()) {
            System.out.println("default-plugins.xml not exist "+ file.getAbsolutePath());
            return;
        }


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder =null;
        Document document = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        }
        catch(ParserConfigurationException e)
        {
            System.out.println("parser configuration error");
        }
        try {
            if(documentBuilder != null)
                document = documentBuilder.parse(file);
        }
        catch(Exception e)
        {
            System.out.println("parsing error");
        }
        NodeList xmlPluginsElements = null;
        try{
            if(document != null)
                xmlPluginsElements = document.getElementsByTagName("plugin");
        }
        catch(NullPointerException e)
        {
            System.out.println("no plugin tags found");
        }
        if(xmlPluginsElements != null) {
            for(int i=0;i<xmlPluginsElements.getLength();i++)
            {
                System.out.println("Load plugin: "+xmlPluginsElements.item(i).getTextContent());
                OPluginInfo tempInfo = getPlugin(xmlPluginsElements.item(i).getTextContent());
                System.out.println("Plugin infos: "+tempInfo.pluginName);
                loadPlugin(tempInfo);
            }
        }

    }

    public void loadAllPluginsInfo() {
        File dirContent = new File("target/plugins");
        System.out.println(dirContent.getAbsolutePath());
        File[] files = dirContent.listFiles();
        for (File file : files) {
            String extension = "";
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i + 1);
            }
            if(extension.equals("jar")) {
                try {
                    System.out.println("Plugin available: "+file.getName());
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
            //e.printStackTrace();
            System.out.println("[ONYX] Fichier manifest du plugin introuvable");
        }

        OPluginInfo plugin = new OPluginInfo();
        plugin.pluginName = p.getProperty("name");
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
