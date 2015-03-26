package com.onyx.app.settings;

import com.onyx.core.OActivity;
import com.onyx.core.OSetting;
import com.onyx.core.OSettingProperty;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hollevoet Yehudi on 3/19/15.
 */
public class OSettingsApp extends OActivity {
    Map<String, List<OSettingProperty> > catProperties;
    VBox vboxpanel;

    public void onCreate() {
        super.onCreate();
        vboxpanel = new VBox();
        vboxpanel.setMinWidth(getPane().getWidth());
        catProperties = new HashMap<>();
        List<Object> settings = getPlatform().getServices("setting");
        for (Object s : settings) {
            OSettingProperty prop = (OSettingProperty) s;
            String name = prop.settingInterface.getName();
            try {
                Field field = prop.settingInterface.getDeclaredField("name");
                name = (String) field.get(null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!catProperties.containsKey(name)){
                catProperties.put(name, new ArrayList<>());
            }
            catProperties.get(name).add(prop);
        }
        for(String s : catProperties.keySet()) {
            TitledPane tp = new TitledPane();
            tp.setMinWidth(vboxpanel.getMinWidth());
            tp.setText(s);
            VBox buttons = new VBox();
            for(OSettingProperty prop : catProperties.get(s)){
                TitledPane tp1 = new TitledPane();
                tp1.setText(prop.name);
                try {
                    OSetting setting = (OSetting) prop.settingClass.newInstance();
                    VBox inside = new VBox();
                    tp1.setContent(inside);
                    tp1.setExpanded(false);
                    setting.setDisplayPanel(inside);
                    setting.onCreate();
                    buttons.getChildren().add(tp1);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tp.setContent(buttons);
            vboxpanel.getChildren().add(tp);
        }
        System.out.println(getPane());
        System.out.println(getPane().getChildren());
        getPane().getChildren().add(vboxpanel);
        System.out.println("com.onyx.app.settings.OSettingsApp on create");
    }
}
