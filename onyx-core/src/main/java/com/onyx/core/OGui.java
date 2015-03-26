package com.onyx.core;

import com.onyx.core.OCore;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
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
	private MenuBar bottomMenu;
	private Menu back, home, settings;
	private Pane centerPane;
	public OCore core;
	
    @Override
    public void onCreate() {
    	
        System.out.println("Chargement de l'interface");
        mainPane = new BorderPane();
        mainPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        hour = new Menu("Heure");
        network = new Menu("Network");
        mainMenu = new MenuBar();
		mainMenu.getMenus().addAll(hour, network);

		back = new Menu();
		Label backLabel = new Label("Back");
		backLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				core.getCurrentActivity().backBtn();
			}
		});
		back.setGraphic(backLabel);
		home = new Menu();
		Label homeLabel = new Label("Home");
		homeLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				core.setApplication(OCore.LAUNCHER_APP);
			}
		});
		home.setGraphic(homeLabel);
		settings = new Menu("Settings");
		bottomMenu = new MenuBar();
		bottomMenu.getMenus().addAll(back, home, settings);

        
        centerPane = new Pane();


        mainPane.setTop(mainMenu);
        mainPane.setCenter(centerPane);
        mainPane.setBottom(bottomMenu);
       
    }

    @Override
    public void onStop() {

    }
    
    public Pane getMainPane(){
    	return mainPane;
    }

	public void setPaneApplication(Pane activity) {
		centerPane = activity;
		mainPane.setMaxHeight(536);
		mainPane.setMaxWidth(334);
        mainPane.setCenter(centerPane);
	}
	
}
