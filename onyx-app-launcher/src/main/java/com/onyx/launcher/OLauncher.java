package com.onyx.launcher;

import com.onyx.core.OActivity;
import com.onyx.core.OAppProperty;

import java.util.List;

/**
 * Created by Maxime on 08/02/15.
 */
public class OLauncher extends OActivity {

    @Override
    public void onCreate() {
        System.out.println("run launcher");
        
        List<Object> apps = getPlatform().getServices("application");
        for(Object app : apps) {
            System.out.println("youpi ! "+((OAppProperty)app).id );
            // run app
            //this.getCore().setApplication(((OAppProperty)app).id);
            // run another activity in same app
            //this.getCore().setActivity(OLauncher.class);
        }
    }
}