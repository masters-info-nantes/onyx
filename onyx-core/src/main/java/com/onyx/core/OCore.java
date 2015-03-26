package com.onyx.core;

import com.onyx.platform.OPlugin;
import com.onyx.platform.OPluginProperty;
import com.onyx.platform.errors.OPluginNotRunnableException;

import java.util.List;

/**
 * Created by Maxime on 05/02/15.
 */
public class OCore extends OPlugin {

	private OActivity currentActivity;
	private OAppProperty currentApp;
	private OGui gui;

	public static String LAUNCHER_APP = "com.onyx.launcher.app";

	@Override
	public void onCreate() {
		System.out.println("Chargement du core");
		gui = new OGui();
		gui.onCreate();
		gui.core = this;
		/*OPluginProperty p = this.getPlatform().getPlugin("com.onyx.gui");
		try {
			gui = (OGui) runPlugin(p);
			gui.core = this;
		} catch (OPluginNotRunnableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        setApplication(LAUNCHER_APP);
	}

	@Override
	public void onStop() {

	}


	public void setApplication(String name){
		List<Object> apps = this.getPlatform().getServices("application");
		for (Object obj : apps) {
			OAppProperty app = (OAppProperty) obj;
			if(app.id.equals(name)) {
				if(getCurrentActivity() != null){
					getCurrentActivity().onStop();
				}
				currentApp = app;
				currentActivity = null;
				setActivity(app.mainActivity);
			}
		}

	}

	public void setActivity(Class className){
				if(getCurrentActivity() != null){
					getCurrentActivity().onStop();
				}
				OActivity activity = null;
				try {
					activity = (OActivity) className.newInstance();
					activity.platform = this.getPlatform();
					activity.core = this;
					activity.parent = getCurrentActivity();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				currentActivity = activity;
				activity.onCreate();
				gui.setPaneApplication(activity.getApplicationPane());

	}

	public OGui getGui() {
		return gui;
	}


	public OActivity getCurrentActivity() {
		return currentActivity;
	}

	public OAppProperty getCurrentApp() {
		return currentApp;
	}
}
