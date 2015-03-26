package com.onyx.app.settings;

import com.onyx.core.OActivity;
import com.onyx.core.setting.OSetting;
import com.onyx.core.setting.OSettingProperty;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;


import javafx.scene.control.Label;
import javafx.scene.text.Font;

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
        vboxpanel.setPrefWidth(334);

        vboxpanel.setStyle("-fx-background-color: #cccccc;");
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
            VBox tp= vboxpanel;
            VBox buttons = new VBox();
            Label title = new Label(s);
            title.setFont(new Font(20));
            buttons.getChildren().add(title);
            for(OSettingProperty prop : catProperties.get(s)){
                TitledPane tp1 = new TitledPane();
                tp1.setText(prop.name);
                try {
                    OSetting setting = (OSetting) prop.settingClass.newInstance();
                    setting.setCore(this.getCore());
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
            tp.getChildren().add(buttons);
        }
        System.out.println(getPane());
        System.out.println(getPane().getChildren());
        getPane().getChildren().add(vboxpanel);
        System.out.println("com.onyx.app.settings.OSettingsApp on create");
    }
}
