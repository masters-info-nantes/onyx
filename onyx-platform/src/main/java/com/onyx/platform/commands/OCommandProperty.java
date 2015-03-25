package com.onyx.platform.commands;

import com.onyx.platform.OService;
import com.onyx.platform.OServiceValue;

/**
 * Created by Maxime on 25/03/15.
 */
@OService(name = "command")
public class OCommandProperty {
    @OServiceValue(name = "name")
    public String name;

    @OServiceValue(name = "usage", required = false)
    public String usage = "";

    @OServiceValue(name = "id")
    public String id;

    @OServiceValue(name = "commandClass")
    public Class commandClass;

}
