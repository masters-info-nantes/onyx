package com.onyx.core;

import java.text.DateFormat;
import java.util.Calendar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
	private Menu clock;
	private Pane centerPane;
	public OCore core;
	
    @Override
    public void onCreate() {
    	
        System.out.println("Chargement de l'interface");
        mainPane = new BorderPane();
        mainPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        network = new Menu("Network");
        clock = new Menu();
        mainMenu = new MenuBar();
        
          
        final DateFormat format = DateFormat.getInstance();  
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {  
             @Override  
             public void handle(ActionEvent event) {  
            	 final Calendar cal = Calendar.getInstance();  
                 clock.setText(format.format(cal.getTime()));  
             }  
        }));  

        timeline.setCycleCount(Animation.INDEFINITE);  
        timeline.play();  
        
        mainMenu.getMenus().addAll(clock, network);

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
