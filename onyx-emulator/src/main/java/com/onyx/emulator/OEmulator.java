package com.onyx.emulator;

import com.onyx.gui.OGui;
import com.onyx.platform.OPlugin;
import com.onyx.platform.OPluginProperty;

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
        OGui guiPlugin = (OGui) this.getPlatform().runPlugin(p);
        EmulatorUI ui = new EmulatorUI(guiPlugin.getMainPane());
        this.getPlatform().getPrimaryStage().setScene(ui.getScene());
        this.getPlatform().getPrimaryStage().show();
    }

}
