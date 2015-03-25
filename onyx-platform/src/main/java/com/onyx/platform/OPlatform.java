package com.onyx.platform;


import com.onyx.platform.commands.GenerateEmptyPluginCommand;
import com.onyx.platform.commands.HelpCommand;
import com.onyx.platform.commands.ListPluginCommand;
import com.onyx.platform.commands.OCommandManager;
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
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Created by Maxime on 05/02/15.
 */
public class OPlatform {

    private static final Logger logger = LogManager.getLogger(OPlatform.class);

    private Map<String, OPluginProperty> plugins = new HashMap<String, OPluginProperty>();
    Map<String, List> services = new HashMap<String, List>();
    Map<String, Class> servicesAvailable = new HashMap<String, Class>();
    public URLClassLoader classLoader;
    Stage primaryStage;
    private OCommandManager commandManager;

    public OPlatform() {
        logger.info("Lancement de la plateforme");
        commandManager = new OCommandManager(this);
        commandManager.addDefaultCommand("Help", "help", "", HelpCommand.class);
        commandManager.addDefaultCommand("List all plugins", "pluginList", "", ListPluginCommand.class);
        commandManager.addDefaultCommand("Generate empty plugin", "generatePlugin", "", GenerateEmptyPluginCommand.class);
        scanPluginDirectory();
    }

    public void runCommandManager(List<String> params) {
        commandManager.run(params);
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

        List<URL> urls = new ArrayList<URL>();//TODO
        for(File f : files) {
            try {
                urls.add(f.toURL());
                System.out.println(f.getAbsolutePath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        URL UrlsArray[] = new URL[urls.size()];
        urls.toArray(UrlsArray);
        classLoader = URLClassLoader.newInstance(UrlsArray, this.getClass().getClassLoader());//TODO
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
        try {
            updateAllServices();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        for(OPluginProperty pluginProperty : this.plugins.values()) {
            pluginProperty.updateDependencies(this);
            pluginProperty.classLoader = classLoader;//TODO
            System.out.println(pluginProperty.classLoader);
        }
        /*for(OPluginProperty plugin : this.plugins.values()) {
            List<URL> urls = getDependenciesURL(plugin);
            URL UrlsArray[] = new URL[urls.size()];
            UrlsArray = urls.toArray(UrlsArray);
            plugin.classLoader = URLClassLoader.newInstance(UrlsArray, this.getClass().getClassLoader());
        }*/

    }

    public void updateAllServices() throws IllegalAccessException {
        Map<String, Boolean> added = new HashMap<String, Boolean>();
        for(OPluginProperty pluginProperty : this.plugins.values()) {
            added.put(pluginProperty.id, false);
        }
        for(OPluginProperty pluginProperty : this.plugins.values()) {
            if(added.get(pluginProperty.id).booleanValue() == false) {
                for(OPluginProperty dependency : pluginProperty.dependencies) {
                    if(added.get(dependency.id).booleanValue() == false) {
                        updateServices(dependency);
                        added.put(dependency.id, true);
                    }
                }
                updateServices(pluginProperty);
                added.put(pluginProperty.id, true);
            }
        }
    }

    public void addNewServiceAvailable(Class<?> cl) throws IllegalAccessException {
        if (cl.isAnnotationPresent(OService.class)) {
            OService service = cl.getAnnotation(OService.class);
            if(!servicesAvailable.containsKey(service.name())) {
                servicesAvailable.put(service.name(), cl);
                services.put(service.name(), new ArrayList<Object>());
            } else {
                throw new IllegalAccessException("this service already exist");
            }
        } else {
            throw new IllegalAccessException("Class must have the OService annotation");
        }
    }

    public void addNewService(Class<?> cl, Object classProperty) throws IllegalAccessException {
        if(classProperty.getClass() == cl) {
            if (cl.isAnnotationPresent(OService.class)) {
                OService service = cl.getAnnotation(OService.class);
                if(servicesAvailable.containsKey(service.name())) {
                    services.get(service.name()).add(classProperty);
                } else {
                    throw new IllegalAccessException("this serviceType not exist");
                }
            } else {
                throw new IllegalAccessException("Class must have the OService annotation");
            }
        } else {
            throw new IllegalAccessException("this objectproperty is not instance of the service class");
        }
    }

    private void updateServices(OPluginProperty pluginProperty) throws IllegalAccessException {
        Element servicesXml = (Element)pluginProperty.document.getElementsByTagName("services").item(0);
        if(servicesXml != null) {
            NodeList dependenciesList = servicesXml.getElementsByTagName("service");
            for(int i=0; i<dependenciesList.getLength(); i++) {
                String serviceClass = ((Element)dependenciesList.item(i)).getTextContent();
                Class<?> cl = null;
                try {
                    cl = Class.forName(serviceClass, false, pluginProperty.classLoader);
                    addNewServiceAvailable(cl);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for(Map.Entry<String, Class> newService : servicesAvailable.entrySet()) {
            NodeList allnew = pluginProperty.document.getElementsByTagName(newService.getKey());
            for(int i = 0; i<allnew.getLength(); i++) {
                Element e = (Element)allnew.item(i);
                Object service = null;
                try {
                    service = newService.getValue().newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
                if (service != null) {
                    for(Field field : newService.getValue().getFields()) {
                        if(field.isAnnotationPresent(OServiceValue.class)){
                            OServiceValue value = field.getAnnotation(OServiceValue.class);
                            String v = null;
                            try {
                                v = e.getElementsByTagName(value.name()).item(0).getTextContent();
                            } catch (Exception e2) {
                                if(value.required())
                                    throw new IllegalAccessException("the value "+value.name()+" is required");
                            }
                            if(v != null) {
                                if (field.getType() == Integer.class) {
                                    field.setInt(service, Integer.valueOf(v));
                                } else if (field.getType() == String.class) {
                                    field.set(service, v);
                                } else if (field.getType() == Class.class) {
                                    try {
                                        Class cl = Class.forName(v, false, pluginProperty.classLoader);
                                        field.set(service, cl);
                                    } catch (ClassNotFoundException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            }
                        }
                    }
                    services.get(newService.getKey()).add(service);
                }
            }
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

    public List<Object> getServices(String name) {
        return services.get(name);
    }

}
