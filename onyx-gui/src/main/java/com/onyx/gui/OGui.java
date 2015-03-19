package com.onyx.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import com.onyx.platform.OPlugin;

/**
 * Created by Maxime on 12/02/15.
 */
public class OGui extends OPlugin {
	
	private Pane mainPane;
	private BorderPane borderPane;
	private MenuBar mainMenu;
	private Menu hour, network;
	private Pane centerPane;
	
	
    @Override
    public void onCreate() {
    	
        System.out.println("Chargement de l'interface");
        mainPane = new Pane();
        mainPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane = new BorderPane();
        hour = new Menu("Heure");
        network = new Menu("Network");
        mainMenu = new MenuBar();
        Button button = new Button("Button");
        System.out.println("Toto");
        //OWebBrowser wb = new OWebBrowser();
        
        centerPane = new Pane();
        centerPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		centerPane.setMinHeight(300);
        
        
        mainMenu.getMenus().addAll(hour, network);
        
        borderPane.setTop(mainMenu);
        borderPane.setCenter(centerPane);
        borderPane.setBottom(button);
        //mainPane.getChildren().add(borderPane);
       
    }

    @Override
    public void onStop() {

    }
    
    public Pane getMainPane(){
    	return mainPane;
    }
    
    public BorderPane getBorderPane(){
    	return borderPane;
    }

	public void setPaneApplication(Pane activity) {
		activity.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		/*centerPane = activity;
		centerane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		centerPane.setMinHeight(300);
		borderPane.setCenter(centerPane);*/
		mainPane.getChildren().add(activity);
	}
}
