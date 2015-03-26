package com.onyx.core.command;

import com.onyx.platform.commands.OCommand;

import java.util.List;

/**
 * Created by Maxime on 25/03/15.
 */
public class GenerateEmptyApplicationCommand extends OCommand{
    @Override
    public void run(List<String> args) {
        if(args.size() != 4) {
            System.out.println("invalid command, use :" + getProperty().usage);
            return;
        }
        System.out.println("OManifest");
        System.out.println("---------");
        System.out.println("<?xml version=\"1.0\"?>");
        System.out.println("<manifest>");
        System.out.println("\t<id>"+args.get(0)+"</id>");
        System.out.println("\t<version>"+args.get(2)+"</version>");
        System.out.println("\t<name>"+args.get(1)+"</name>");
        System.out.println("\t<description></description>");
        System.out.println("\t<application>");
        System.out.println("\t\t<id>"+args.get(0)+".app</id>");
        System.out.println("\t\t<name>"+args.get(1)+"</name>");
        System.out.println("\t\t<mainActivity>"+args.get(3)+"</mainActivity>");
        System.out.println("\t</application>");
        System.out.println("</manifest>");

        System.out.println();
        System.out.println("Manifest généré!");

    }
}
