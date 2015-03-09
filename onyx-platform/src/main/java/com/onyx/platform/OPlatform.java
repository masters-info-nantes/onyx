package com.onyx.platform;


import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.net.MalformedURLException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
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

    private static final Logger logger = LogManager.getLogger(OPlatform.class);

    private Map<String, OPluginProperty> plugins = new HashMap<String, OPluginProperty>();
    Stage primaryStage;

    public OPlatform() {
        logger.info("Lancement de la plateforme");
        scanPluginDirectory();
    }

    public void loadDefaultPlugins()
    {
        System.out.println("Chargement des plugins par défault...");
        File file = new File("target/default-plugins.xml");
        if(!file.exists()) {
            System.out.println(file.getName()+" does not exist "+ file.getAbsolutePath());
            return;
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder =null;
        Document document = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch(ParserConfigurationException e) {
            System.out.println("parser configuration error");
        } catch(Exception e) {
            System.out.println("parsing error");
        }
        NodeList xmlPluginsElements = null;
        try {
            if(document != null)
                xmlPluginsElements = document.getElementsByTagName("plugin");
        } catch(NullPointerException e) {
            System.out.println("no plugin tags found");
        }
        if(xmlPluginsElements != null) {
            for(int i=0;i<xmlPluginsElements.getLength();i++)
            {
                System.out.println("Load plugin: "+xmlPluginsElements.item(i).getTextContent());
                OPluginProperty tempInfo = getPlugin(xmlPluginsElements.item(i).getTextContent());
                System.out.println("Plugin infos: "+ tempInfo.getPluginName());
                runPlugin(tempInfo);
            }
        }
    }

    public void scanPluginDirectory() {
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
                    addPlugin(file.toURL());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addPlugin(String url) {
        try {
            addPlugin(new URL("file://" + url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPlugin(URL url) {
        URL[] urls = new URL[]{url};
        URLClassLoader classLoader = URLClassLoader.newInstance(urls, OPlugin.class.getClassLoader());

        InputStream is = classLoader.getResourceAsStream("OManifest");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder =null;
        Document document = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(is);
        } catch(ParserConfigurationException e) {
            System.out.println("parser configuration error");
        } catch(Exception e) {
            System.out.println("parsing error");
        }


        OPluginProperty plugin = new OPluginProperty();
        plugin.pluginName = document.getElementsByTagName("name").item(0).getTextContent();
        plugin.pluginVersion = document.getElementsByTagName("version").item(0).getTextContent();
        plugin.pluginDescription = document.getElementsByTagName("description").item(0).getTextContent();
        plugin.pluginPackage = document.getElementsByTagName("package").item(0).getTextContent();
        plugin.pluginClassLoader = classLoader;
        plugin.pluginMainClass = document.getElementsByTagName("mainClass").item(0).getTextContent();
        plugin.pluginUrl = url;
        plugins.put(plugin.getPluginPackage(),plugin);
    }

    public void runPlugin(OPluginProperty plugin) {
        runPlugin(plugin, null);
    }

    public void runPlugin(OPluginProperty plugin, OPluginProperty pluginDependency) {
        if(pluginDependency != null) {
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

    public Map<String, OPluginProperty> getPlugins() {
        return plugins;
    }

    public OPluginProperty getPlugin(String name) {
        return plugins.get(name);
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
