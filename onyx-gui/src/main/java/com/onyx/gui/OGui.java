package com.onyx.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import com.onyx.platform.OPlugin;

/**
 * Created by Maxime on 12/02/15.
 */
public class OGui extends OPlugin {
	
	private BorderPane mainPane;
	private MenuBar mainMenu;
	private Menu hour, network;
	private Pane centerPane;
	
	
    @Override
    public void onCreate() {
    	
        System.out.println("Chargement de l'interface");
        mainPane = new BorderPane();
        mainPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        hour = new Menu("Heure");
        network = new Menu("Network");
        mainMenu = new MenuBar();
        Button button = new Button("Button");
        System.out.println("Toto");
        
        centerPane = new Pane();
        centerPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        
        mainMenu.getMenus().addAll(hour, network);

        mainPane.setTop(mainMenu);
        mainPane.setCenter(centerPane);
        mainPane.setBottom(button);
       
    }

    @Override
    public void onStop() {

    }
    
    public Pane getMainPane(){
    	return mainPane;
    }

	public void setPaneApplication(Pane activity) {
		centerPane = activity;
        mainPane.setCenter(centerPane);
	}
}
