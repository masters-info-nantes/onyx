package com.onyx.webbrowser;


import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import com.onyx.core.OActivity;
import com.onyx.core.OActivityToolbar;

/**
 * Created by Maxime on 09/02/15.
 */


//Change to OActivityToolbar after
public class OWebBrowser extends OActivity {
	
	
	private WebView browser = new WebView();
    private WebEngine webEngine = browser.getEngine();
	
    @Override
    public void onCreate() {
    	super.onCreate();
    	//super.setTitle("Browser");
        System.out.println("run Browser");
        
        browser = new WebView();
        webEngine = browser.getEngine();
        
        webEngine.load("http://google.fr");
        getPane().getChildren().add(browser);
        
        
        
        
    }

}