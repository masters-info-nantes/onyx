package com.onyx.emulator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Maxime on 04/03/15.
 */
public class EmulatorApplication extends Application {

    public EmulatorApplication() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Web View");
        primaryStage.setWidth(50);
        primaryStage.setHeight(750);
        primaryStage.show();
    }
}
