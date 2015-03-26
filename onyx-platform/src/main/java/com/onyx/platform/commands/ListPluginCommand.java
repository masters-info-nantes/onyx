package com.onyx.platform.commands;

import com.onyx.platform.OPlatform;

import java.util.List;

/**
 * Created by Maxime on 09/03/15.
 */
public class ListPluginCommand extends OCommand{
    @Override
    public void run(List<String> params)
    {
        System.out.println("Liste des plugins disponibles:");
        for(String key : getPlatform().getPlugins().keySet())
        {
            System.out.println("Plugin: "+ getPlatform().getPlugin(key).getName());
            System.out.println("Version: "+ getPlatform().getPlugin(key).getVersion());
            System.out.println("Description: "+ getPlatform().getPlugin(key).getDescription()+"\n");
        }
    }

}
