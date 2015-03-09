package com.onyx.emulator;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Maxime on 04/03/15.
 */
public class EmulatorUI {


    private Group root = new Group();
    private Scene scene = new Scene(root, 428, 800);

    private ImageView background;
    private Pane pane;

    public EmulatorUI() {
        Image img = null;
        try {
            img = new Image(getClass().getClassLoader().getResource("nexus5.png").openStream());
            background = new ImageView(img);
            background.setFitHeight(800);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pane = new Pane();
        pane.setLayoutX(47);
        pane.setLayoutY(95);
        pane.setMaxWidth(334);
        pane.setMinWidth(334);
        pane.setPrefWidth(334);
        pane.setMaxHeight(594);
        pane.setMinHeight(594);
        pane.setPrefHeight(594);
        pane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(background);
        root.getChildren().add(pane);
    }


    public Scene getScene() {
        return scene;
    }
}
