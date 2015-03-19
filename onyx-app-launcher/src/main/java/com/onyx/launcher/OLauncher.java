package com.onyx.launcher;

import com.onyx.core.OActivity;

/**
 * Created by Maxime on 08/02/15.
 */
public class OLauncher extends OActivity {

    @Override
    public void onCreate() {
        System.out.println("run launcher");
        
        /*List<Object> apps = getPlatform().getServices("application");
        for(Object app : apps) {
            System.out.println("youpi ! "+((OAppProperty)app).mainActivity );
            try {
                Object o = (((OAppProperty) app).mainActivity).newInstance();
                OActivity activity = (OActivity) o;
                activity.onCreate();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }*/
    }
}