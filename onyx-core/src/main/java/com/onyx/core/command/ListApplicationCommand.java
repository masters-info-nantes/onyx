package com.onyx.core.command;

import com.onyx.core.OAppProperty;
import com.onyx.platform.commands.OCommand;
import com.onyx.platform.commands.OCommandProperty;

import java.util.List;

/**
 * Created by Maxime on 25/03/15.
 */
public class ListApplicationCommand extends OCommand{
    @Override
    public void run(List<String> args) {
        System.out.println("List of all applications");
        for(Object obj : getPlatform().getServices("application")) {
            OAppProperty appProperty = (OAppProperty) obj;
            System.out.println("\t"+appProperty.name);
        }

    }
}
