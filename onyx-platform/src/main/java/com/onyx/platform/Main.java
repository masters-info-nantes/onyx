package com.onyx.platform;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Maxime on 05/02/15.
 */
public class Main extends Application {

    private Stage primaryStage = null;
    private static OPlatform platform;

    public static void main (String[] args) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        platform = new OPlatform();
        platform.primaryStage = primaryStage;
        if(this.getParameters().getRaw().size() > 0) {
            OCommandManager commandManager = new OCommandManager(platform);
            commandManager.run(this.getParameters().getRaw());
        } else {
            platform.loadDefaultPlugins();
        }
        this.primaryStage = primaryStage;
    }

}
