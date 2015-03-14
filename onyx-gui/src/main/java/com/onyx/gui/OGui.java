package com.onyx.gui;

import com.onyx.core.OActivity;
import com.onyx.core.OApp;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import com.onyx.platform.OPlugin;

import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.JarFile;

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
        List<Object> apps = getPlatform().getServices("application");
        for(Object app : apps) {
            System.out.println("youpi ! "+((OApp)app).mainActivity );
            try {
                Object o = (((OApp) app).mainActivity).newInstance();
                OActivity activity = (OActivity) o;
                activity.onCreate();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {

    }

    public Pane getMainPane(){
    	return mainPane;
    }
}
