package com.onyx.emulator;

import com.onyx.platform.OPlatform;

/**
 * Created by Maxime on 05/02/15.
 */
public class OEmulator {

    public OEmulator() {
        OPlatform platform = new OPlatform();
        platform.loadPlugin(platform.getPlugin("com.onyx.core"));
    }

}
