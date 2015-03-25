package com.onyx.platform.commands;

import com.onyx.platform.OPlatform;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 09/03/15.
 */
public class OCommandManager {

    private OPlatform platform;

    public OCommandManager(OPlatform platform) {
        try {
            this.platform = platform;
            platform.addNewServiceAvailable(OCommandProperty.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addDefaultCommand(String name, String id, String usage, Class commandClass) {
        OCommandProperty commandProperty = new OCommandProperty();
        commandProperty.id = id;
        commandProperty.name = name;
        commandProperty.usage = usage;
        commandProperty.commandClass = commandClass;
        try {
            platform.addNewService(OCommandProperty.class, commandProperty);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void run(List<String> params) {
        for(Object obj : platform.getServices("command")) {
            OCommandProperty command = (OCommandProperty) obj;
            if(params.get(0).equals("--" + command.id)) {
                OCommand oCommand = null;
                try {
                    oCommand = (OCommand) command.commandClass.newInstance();
                    oCommand.platform = this.platform;
                    List<String> newParams = new ArrayList<String>();
                    for(int i = 0; i<params.size(); i++) {
                        if(i != 0)
                            newParams.add(params.get(i));
                    }
                    oCommand.run(newParams);
                    Platform.exit();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
