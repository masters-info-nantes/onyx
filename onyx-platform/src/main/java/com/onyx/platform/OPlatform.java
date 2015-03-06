package com.onyx.platform;


import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.MalformedURLException;
import java.util.Scanner;

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
    Stage primaryStage;

    public OPlatform() {
        System.out.println("Lancement de la plateforme...");
        loadAllPluginsInfo();
    }

    public void showMenu()
    {
        System.out.println("Menu:");
        System.out.println("0 - Liste des plugins disponibles");
        System.out.println("1 - Lancement des plugins par défaut");
        System.out.println("2 - Chargement de plugins spécifiques");
        System.out.println("3 - Création d'un plugin d'example");
        System.out.println("4 - Quitter");
        Scanner sc = new Scanner(System.in);
        System.out.println("Que voulez vous faire :");
        int choice = sc.nextInt();
        if(choice==0) listPlugins();
        else if(choice==1) loadDefaultPlugins();
        else if(choice==2) loadSpecificPlugins();
        else if(choice==3) generateEmptyPlugin();
        else if(choice==4) System.out.println("La plateforme va se fermer...");
    }

    public void listPlugins()
    {
        System.out.println("Liste des plugins disponibles:");
        for(String key : plugins.keySet())
        {
            System.out.println("Plugin: "+ plugins.get(key).pluginName);
            System.out.println("Description: "+ plugins.get(key).pluginDescription+"\n");
        }
        showMenu();
    }

    public void loadDefaultPlugins()
    {
        System.out.println("Chargement des plugins par défault...");
        loadPluginsFromXMLFile("target/default-plugins.xml");
    }

    private void loadSpecificPlugins()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Chemin du fichier xml des plugins :");
        String file = sc.next();
        loadPluginsFromXMLFile(file);
    }

    private void generateEmptyPlugin()
    {

        OPluginInfo newPlugin = new OPluginInfo();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nom du plugin: ");
        newPlugin.pluginName = sc.nextLine();
        System.out.print("Description du plugin: ");
        newPlugin.pluginDescription = sc.nextLine();
        System.out.print("Classe principale du plugin: ");
        newPlugin.pluginMainClass = sc.nextLine();
        System.out.print("Package du plugin: ");
        newPlugin.pluginPackage = sc.nextLine();
        System.out.print("Emplacement du plugin: ");
        try {
            newPlugin.pluginUrl = new URL("file://"+sc.nextLine());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // TO-DO
        System.out.println("Plugin généré!");
        showMenu();
    }

    public void loadPluginsFromXMLFile(String xmlFile)
    {

        File file = new File(xmlFile);

        if(!file.exists()) {
            System.out.println(file.getName()+" does not exist "+ file.getAbsolutePath());
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
        loadPlugin(plugin, null);
    }

    public void loadPlugin(OPluginInfo plugin, OPluginInfo pluginDependency) {
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

    public Map<String, OPluginInfo> getPlugins() {
        return plugins;
    }

    public OPluginInfo getPlugin(String name) {
        return plugins.get(name);
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
