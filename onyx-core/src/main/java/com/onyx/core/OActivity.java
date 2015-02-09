package com.onyx.core;

/**
 * Created by Maxime on 09/02/15.
 */
public abstract class OActivity {

    OAppManager appManager;

    protected abstract void onCreate();

    protected OAppManager getAppManager() {
        return appManager;
    }

}
