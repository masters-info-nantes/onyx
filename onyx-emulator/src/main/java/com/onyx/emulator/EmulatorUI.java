package com.onyx.emulator;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by Maxime on 04/03/15.
 */
public class EmulatorUI {


    private Group root = new Group();
    private Scene scene = new Scene(root, 428, 800);

    private ImageView background;
    private Pane pane;

    public EmulatorUI(Pane pane) {
        Image img = null;
        try {
            img = new Image(getClass().getClassLoader().getResource("nexus5.png").openStream());
            background = new ImageView(img);
            background.setFitHeight(800);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.pane = pane;
        pane = new Pane();
        pane.setLayoutX(47);
        pane.setLayoutY(95);
        pane.setMaxWidth(334);
        pane.setMinWidth(334);
        pane.setPrefWidth(334);
        pane.setMaxHeight(594);
        pane.setMinHeight(594);
        pane.setPrefHeight(594);
        root.getChildren().add(background);
        root.getChildren().add(pane);
    }


    public Scene getScene() {
        return scene;
    }
    
    public Pane getPane(){
    	return pane;
    }
}
