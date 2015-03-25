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

        //OPluginProperty newPlugin = new OPluginProperty();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nom du plugin: ");
        //newPlugin.pluginName = sc.nextLine();
        System.out.print("Description du plugin: ");
        //newPlugin.pluginDescription = sc.nextLine();
        System.out.print("Classe principale du plugin: ");
        //newPlugin.pluginMainClass = sc.nextLine();
        System.out.print("Package du plugin: ");
        //newPlugin.pluginPackage = sc.nextLine();
        System.out.print("Emplacement du plugin: ");
        try {
            //newPlugin.pluginUrl = new URL("file://"+sc.nextLine());
            new URL("file://"+sc.nextLine());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // TO-DO
        System.out.println("Plugin généré!");
    }
}
