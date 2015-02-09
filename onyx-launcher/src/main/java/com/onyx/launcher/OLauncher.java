package com.onyx.launcher;

import com.onyx.core.OActivity;
import com.onyx.core.OApp;

import java.util.Map;

/**
 * Created by Maxime on 08/02/15.
 */
public class OLauncher extends OActivity {

    @Override
    protected void onCreate() {
        System.out.println("run launcher");
        for(Map.Entry<String, OApp> entry : this.getAppManager().getApps().entrySet()) {
            String key = entry.getKey();
            OApp app = entry.getValue();

            System.out.println("app install : "+app.getAppName());
        }
    }
}
