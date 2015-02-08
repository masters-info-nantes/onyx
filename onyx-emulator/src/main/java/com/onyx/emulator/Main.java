package com.onyx.emulator;

import com.onyx.platform.OPlatform;
import com.onyx.platform.OPlugin;

import java.util.Map;

/**
 * Created by Maxime on 05/02/15.
 */
public class Main {
    public static void main (String[] args) throws Exception{
        new OEmulator();
        OPlatform platform = new OPlatform();
        platform.loadAllPlugins();
        for(Map.Entry<String, OPlugin> entry : platform.getActivePlugins().entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
            OPlugin value = entry.getValue();
            System.out.println(value);
        }
        System.out.println("ok :)");
    }
}
