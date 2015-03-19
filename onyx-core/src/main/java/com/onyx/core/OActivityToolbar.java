package com.onyx.core;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class OActivityToolbar extends OActivity {

	
	private Pane mainPane;
	private Pane toolbar;
	private Label label;
	private HBox hBox;
	
	public void onCreate(){
		super.getPane();
		hBox = new HBox();
		hBox.getChildren().add(label);
	}

	protected Pane getPane(){
		return mainPane;
	}

	protected void setTitle(String name) {
		super.getPane();
		
	}



}
