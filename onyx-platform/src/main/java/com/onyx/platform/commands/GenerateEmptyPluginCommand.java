package com.onyx.platform.commands;

import com.onyx.platform.OPluginProperty;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Maxime on 09/03/15.
 */
public class GenerateEmptyPluginCommand extends OCommand {
    @Override
    public void run(List<String> params)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom du plugin: ");
        String pluginName = sc.nextLine();
        System.out.println("Id du plugin: ");
        String pluginId = sc.nextLine();
        System.out.println("Version du plugin: ");
        String pluginVersion = sc.nextLine();
        System.out.println("Description du plugin: ");
        String pluginDescription = sc.nextLine();
        System.out.println("Classe principale du plugin: ");
        String pluginMainClass = sc.nextLine();
        System.out.println("Package du plugin: ");
        String pluginPackage = sc.nextLine();

        System.out.println();
        System.out.println("OManifest");
        System.out.println("---------");
        System.out.println("<?xml version=\"1.0\"?>");
        System.out.println("<manifest>");
        System.out.println("\t<id>"+pluginId+"</id>");
        System.out.println("\t<version>"+pluginVersion+"</version>");
        System.out.println("\t<name>"+pluginName+"</name>");
        System.out.println("\t<description>"+pluginDescription+"</description>");
        if(!pluginMainClass.isEmpty())
            System.out.println("\t<mainClass>"+pluginMainClass+"</mainClass>");
        System.out.println("</manifest>");


        System.out.println();
        System.out.println("Manifest généré!");
    }
}
