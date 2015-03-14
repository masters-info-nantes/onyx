package com.onyx.emulator;

import com.onyx.gui.OGui;
import com.onyx.platform.OPlugin;
import com.onyx.platform.OPluginProperty;
import com.onyx.platform.errors.OPluginNotRunnableException;

/**
 * Created by Maxime on 05/02/15.
 */
public class OEmulator extends OPlugin{

    public OEmulator() {

    }

    @Override
    protected void onCreate() {
        this.getPlatform().getPrimaryStage().setTitle("Onyx - Emulator");
        OPluginProperty p = this.getPlatform().getPlugin("com.onyx.gui");
        try {
            OGui guiPlugin = (OGui) runPlugin(p);
            EmulatorUI ui = new EmulatorUI(guiPlugin.getMainPane());
            this.getPlatform().getPrimaryStage().setScene(ui.getScene());
        } catch (OPluginNotRunnableException e) {
            e.printStackTrace();
        }
        this.getPlatform().getPrimaryStage().show();
    }

    @Override
    protected void onStop() {

    }

}
