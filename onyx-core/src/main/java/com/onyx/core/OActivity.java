package com.onyx.core;

import com.onyx.platform.OPlatform;
import javafx.scene.layout.Pane;

/**
 * Created by Maxime on 09/02/15.
 */
public abstract class OActivity {

	private Pane mainPane;
    OPlatform platform;
    OCore core;
    OActivity parent = null;
    OAppProperty property;
	
    public void onCreate(){
    	mainPane = new Pane();
    }
    
    public void onStop(){
    	
    }

    public void backBtn(){
        if(parent != null)
            getCore().setApplication(parent.property.id);
        else
            getCore().setApplication(OCore.LAUNCHER_APP);
    }
    
    protected Pane getPane(){
    	return mainPane;
    }
    
    public Pane getApplicationPane(){
    	return mainPane;
    }

    public OPlatform getPlatform() {
        return platform;
    }

    public OCore getCore() {
        return core;
    }
    
}
