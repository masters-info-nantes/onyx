package com.onyx.emulator;

import com.onyx.platform.OPlugin;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Maxime on 05/02/15.
 */
public class OEmulator extends OPlugin{

    public OEmulator() {

    }

    @Override
    protected void onCreate() {
        new Test();
    }

}
