package com.onyx.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import com.onyx.platform.OPlugin;

/**
 * Created by Maxime on 12/02/15.
 */
public class OGui extends OPlugin {
	
	private Pane mainPane;
	
	
    @Override
    protected void onCreate() {
        System.out.println("Chargement de l'interface");
        mainPane = new Pane();
        mainPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        
    
    }

    @Override
    protected void onStop() {

    }

    public Pane getMainPane(){
    	return mainPane;
    }
}
