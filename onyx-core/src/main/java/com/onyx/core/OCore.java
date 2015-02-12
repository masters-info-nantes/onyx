package com.onyx.core;

import com.onyx.platform.OPlugin;

/**
 * Created by Maxime on 05/02/15.
 */
public class OCore extends OPlugin {

    private OAppManager appManager;

    @Override
    protected void onCreate() {
        System.out.println("Chargement du core");
        this.loadPlugin("com.onyx.gui");
        appManager = new OAppManager();
        appManager.run(appManager.getApp("com.onyx.launcher"));
    }

}
