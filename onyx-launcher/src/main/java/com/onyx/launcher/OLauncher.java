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
        OApp app = null;
        for(Map.Entry<String, OApp> entry : this.getAppManager().getApps().entrySet()) {
            String key = entry.getKey();
            OApp a = entry.getValue();

            System.out.println("app install : "+a.getAppName());

            if(!a.getAppPackage().equals("com.onyx.launcher")) {
                app = a;
            }
        }

        if(app != null) {
            try {
                this.getAppManager().run(app);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
