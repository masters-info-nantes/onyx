package com.onyx.core.command;

import com.onyx.platform.commands.OCommand;

import java.util.List;

/**
 * Created by Maxime on 25/03/15.
 */
public class GenerateEmptyApplicationCommand extends OCommand{
    @Override
    public void run(List<String> args) {
        System.out.println("Generate empty app");
        for (String a : args) {
            System.out.println(a);
        }
    }
}
