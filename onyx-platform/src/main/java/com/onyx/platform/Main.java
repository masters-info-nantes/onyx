package com.onyx.platform;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Maxime on 05/02/15.
 */
public class Main extends Application {

    private Stage primaryStage = null;

    public static void main (String[] args) {
        new OPlatform();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
    }

}
