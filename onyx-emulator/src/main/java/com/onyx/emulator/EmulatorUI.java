package com.onyx.emulator;

import java.io.IOException;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by Maxime on 04/03/15.
 */
public class EmulatorUI {


    private Group root = new Group();
    
    private Scene scene = new Scene(root, 428, 800);

    private ImageView background;
    private Pane pane;
    
    private final int WIDTH = 334;
    private final int HEIGHT = 594;

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
        pane.setLayoutX(47);
        pane.setLayoutY(95);
        pane.setMaxWidth(WIDTH);
        pane.setMinWidth(WIDTH);
        pane.setPrefWidth(WIDTH);
        pane.setMaxHeight(HEIGHT);
        pane.setMinHeight(HEIGHT);
        pane.setPrefHeight(HEIGHT);
        root.getChildren().add(background);
        root.getChildren().add(pane);
    }


    public Scene getScene() {
        return scene;
    }
    
    public Pane getPane(){
    	return pane;
    }


	public int getWIDTH() {
		return WIDTH;
	}


	public int getHEIGHT() {
		return HEIGHT;
	}
    
    
}
