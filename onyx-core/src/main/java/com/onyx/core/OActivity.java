package com.onyx.core;

import javafx.scene.layout.Pane;

/**
 * Created by Maxime on 09/02/15.
 */
public abstract class OActivity {

	private Pane mainPane;
	
    public void onCreate(){
    	mainPane = new Pane();
    }
    
    public void onStop(){
    	
    }
    
    protected Pane getPane(){
    	return mainPane;
    }
    
    public Pane getApplicationPane(){
    	return mainPane;
    }
}
