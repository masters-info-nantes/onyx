package com.onyx.platform.commands;

import java.util.List;

/**
 * Created by Maxime on 25/03/15.
 */
public class HelpCommand extends OCommand {
    @Override
    public void run(List<String> args) {
        System.out.println("Help : list of all commands");
        for(Object obj : getPlatform().getServices("command")) {
            OCommandProperty commandProperty = (OCommandProperty) obj;
            System.out.println("\t-"+commandProperty.id+" "+commandProperty.usage+" : "+commandProperty.name);
        }
    }
}
