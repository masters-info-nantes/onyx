package com.onyx.platform;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 12/02/15.
 */
public class OPluginProperty {

    private OPluginProperty() {

    }

    String id;
    String version;
    String name;
    String description;
    String mainClass;
    Document document;

    URL url;
    URLClassLoader classLoader;

    List<OPluginProperty> dependencies;


    public static OPluginProperty newInstance(URL url) {
        OPluginProperty plugin = new OPluginProperty();

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

        plugin.document = document;
        plugin.id = document.getElementsByTagName("id").item(0).getTextContent();
        plugin.version = document.getElementsByTagName("version").item(0).getTextContent();
        plugin.name = document.getElementsByTagName("name").item(0).getTextContent();
        plugin.description = document.getElementsByTagName("description").item(0).getTextContent();
        plugin.classLoader = classLoader;
        plugin.mainClass = document.getElementsByTagName("mainClass").item(0).getTextContent();
        plugin.url = url;
        return plugin;
    }

    public void updateDependencies(OPlatform platform) {
        this.dependencies = new ArrayList<OPluginProperty>();
        Element dependenciesXml = (Element)document.getElementsByTagName("dependencies").item(0);
        if(dependenciesXml != null) {
            NodeList dependenciesList = dependenciesXml.getElementsByTagName("dependency");
            for(int i=0; i<dependenciesList.getLength(); i++) {
                OPluginProperty dependency = platform.getPlugin(((Element)dependenciesList.item(i)).getElementsByTagName("id").item(0).getTextContent());
                if(dependency != null) {
                    if(dependency.getVersion().equals(((Element)dependenciesList.item(i)).getElementsByTagName("version").item(0).getTextContent())) {
                        this.dependencies.add(dependency);
                        System.out.println("dependecy OK : "+this.id+" "+dependency.id);
                    }
                }
            }
        }

    }

    public void show() {
        System.out.println("plugin " + getName());
        System.out.println("  id "+ getId());
        System.out.println("  version "+ getVersion());
        System.out.println("  description "+ getDescription());
        System.out.println("  mainCLass "+ mainClass);
        System.out.println("  classLoader ");
        for(int i=0; i < classLoader.getURLs().length; i++) {
            System.out.println("    url "+ classLoader.getURLs()[i]);
        }
    }


    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }


    public List<OPluginProperty> getDependencies() {
        return dependencies;
    }
}
