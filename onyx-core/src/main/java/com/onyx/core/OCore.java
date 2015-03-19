package com.onyx.core;

import com.onyx.gui.OGui;
import com.onyx.platform.OPlugin;
import com.onyx.platform.OPluginProperty;
import com.onyx.platform.errors.OPluginNotRunnableException;

import java.util.List;

/**
 * Created by Maxime on 05/02/15.
 */
public class OCore extends OPlugin {

	private OActivity currentApplication;
	private OGui gui;

	@Override
	public void onCreate() {
		System.out.println("Chargement du core");
		OPluginProperty p = this.getPlatform().getPlugin("com.onyx.gui");
		try {
			gui = (OGui) runPlugin(p);
		} catch (OPluginNotRunnableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setApplication("com.onyx.webbrowser.app");
	}

	@Override
	public void onStop() {

	}


	public void setApplication(String name){
		List<Object> apps = this.getPlatform().getServices("application");
		for (Object obj : apps) {
			OAppProperty app = (OAppProperty) obj;
			if(app.id.equals(name)) {
				OActivity activity = null;
				try {
					activity = (OActivity) app.mainActivity.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(currentApplication != null){
					currentApplication.onStop();
				}
				currentApplication = activity;
				activity.onCreate();
				gui.setPaneApplication(activity.getApplicationPane());
			}
		}




	}

	public OGui getGui() {
		return gui;
	}


}
