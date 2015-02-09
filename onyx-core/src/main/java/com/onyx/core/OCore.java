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
        appManager = new OAppManager();
        try {
            appManager.loadAllApps();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appManager.run(appManager.getApps().get("com.onyx.launcher"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
