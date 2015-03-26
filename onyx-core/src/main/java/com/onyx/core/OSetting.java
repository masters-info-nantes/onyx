package com.onyx.core;

import javafx.scene.layout.Pane;

/**
 * Created by Hollevoet Yehudi on 3/19/15.
 */
public abstract class OSetting {
    Pane panel;

    public abstract void onCreate();

    public Pane getPanel(){
        return panel;
    }
    public void setDisplayPanel(Pane p){
        panel = p;
    }
}
