package com.onyx.platform;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Maxime on 05/02/15.
 */
public class Main extends Application {

    private Stage primaryStage = null;

    public static void main (String[] args) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        OPlatform p = new OPlatform();
        p.primaryStage = primaryStage;
        if(this.getParameters().getRaw().size()>0) {
            for (String param : this.getParameters().getRaw()) {
                if (param.equals("-menu"))
                    p.showMenu();
                else if (param.equals("-debug")) {
                    //to-do
                } else {
                    System.out.println("Invalid Parameters");
                }
                //Others param to do
            }
        }
        else
        {
            p.loadDefaultPlugins();
        }
        this.primaryStage = primaryStage;
    }

}
