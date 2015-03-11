package com.onyx.platform.commands;

import com.onyx.platform.OPlatform;

/**
 * Created by Maxime on 09/03/15.
 */
public class ListPluginCommand {

    public void listPlugins(OPlatform platform)
    {
        System.out.println("Liste des plugins disponibles:");
        for(String key : platform.getPlugins().keySet())
        {
            System.out.println("Plugin: "+ platform.getPlugin(key).getName());
            System.out.println("Description: "+ platform.getPlugin(key).getDescription()+"\n");
        }
    }

}
