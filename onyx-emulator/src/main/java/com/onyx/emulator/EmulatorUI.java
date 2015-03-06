package com.onyx.emulator;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by Maxime on 04/03/15.
 */
public class EmulatorUI {


    private Group root = new Group();
    private Scene scene = new Scene(root, 428, 800);

    private ImageView background;

    public EmulatorUI() {
        Image img = null;
        try {
            img = new Image(getClass().getClassLoader().getResource("nexus5.png").openStream());
            System.out.println(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        background = new ImageView(img);

        background.setX(0);
        background.setY(0);
        background.setFitHeight(800);

        root.getChildren().add(background);
    }


    public Scene getScene() {
        return scene;
    }
}
