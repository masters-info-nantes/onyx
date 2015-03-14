package com.onyx.platform;


import com.onyx.platform.errors.OPluginNotRunnableException;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

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
        DocumentBuilder documentBuilder = null;
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
                System.out.println("Plugin infos: "+ tempInfo.getName());
                try {
                    runPlugin(tempInfo);
                } catch (OPluginNotRunnableException e) {
                    e.printStackTrace();
                }
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
                    addPlugin(file.toURL());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        updateAllDependencies();
    }

    public List<URL> getDependenciesURL(OPluginProperty plugin) {
        List<URL> urls = new ArrayList<URL>();
        urls.add(plugin.url);
        for(OPluginProperty dependency : plugin.dependencies) {
            urls.addAll(getDependenciesURL(dependency));
        }
        return urls;
    }

    public void updateAllDependencies() {
        Map<String, Boolean> classPathUpdated = new HashMap<String, Boolean>();
        for(OPluginProperty pluginProperty : this.plugins.values()) {
            pluginProperty.updateDependencies(this);
            classPathUpdated.put(pluginProperty.id, false);
        }
        for(OPluginProperty plugin : this.plugins.values()) {
            System.out.println("plugin dep :" + plugin.getId());
            List<URL> urls = getDependenciesURL(plugin);
            for(URL url : urls) {
                System.out.println("  " + url.getFile());
            }
            URL UrlsArray[] = new URL[urls.size()];
            UrlsArray = urls.toArray(UrlsArray);
            plugin.classLoader = URLClassLoader.newInstance(UrlsArray, this.getClass().getClassLoader());
        }
    }

    public void addPlugin(URL url) {
        OPluginProperty pluginProperty = OPluginProperty.newInstance(url);
        plugins.put(pluginProperty.getId(),pluginProperty);
    }

    OPlugin runPlugin(OPluginProperty plugin) throws OPluginNotRunnableException {
        return runPlugin(plugin, plugin.classLoader);
    }

    OPlugin runPlugin(OPluginProperty plugin, URLClassLoader classLoader) throws OPluginNotRunnableException {
        if(plugin.mainClass == null)
            throw new OPluginNotRunnableException();

        Class<?> cl = null;
        OPlugin p = null;

        try {
            cl = Class.forName(plugin.mainClass, false, classLoader);
            if (OPlugin.class.isAssignableFrom(cl)) {
                p = (OPlugin) cl.newInstance();
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
        return p;
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
