package com.onyx.platform.commands;

import com.onyx.platform.OPlatform;

import java.util.List;

/**
 * Created by Maxime on 25/03/15.
 */
public abstract class OCommand {

    OPlatform platform;

    public abstract void run(List<String> args);

    public OPlatform getPlatform() {
        return platform;
    }
}
