package com.onyx.emulator;

import com.onyx.core.OCore;
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
    public void onCreate() {
        this.getPlatform().getPrimaryStage().setTitle("Onyx - Emulator");
        OPluginProperty p = this.getPlatform().getPlugin("com.onyx.core");
        try {
            OCore core = (OCore) runPlugin(p);
            EmulatorUI ui = new EmulatorUI(core.getGui().getMainPane());
            this.getPlatform().getPrimaryStage().setScene(ui.getScene());
        } catch (OPluginNotRunnableException e) {
            e.printStackTrace();
        }
        this.getPlatform().getPrimaryStage().show();
    }

    @Override
    public void onStop() {

    }

}
