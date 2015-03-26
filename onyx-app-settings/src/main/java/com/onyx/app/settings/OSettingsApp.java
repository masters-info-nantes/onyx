package com.onyx.app.settings;

import com.onyx.core.OActivity;
import com.onyx.core.setting.OSettingProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;


import javafx.scene.control.Label;
import javafx.scene.paint.Color;
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
        Background normal= new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY));
        Background hover= new Background(new BackgroundFill(Color.LIGHTSALMON, new CornerRadii(5), Insets.EMPTY));
        for(String s : catProperties.keySet()) {
            VBox tp= vboxpanel;
            VBox buttons = new VBox();
            Label title = new Label(s);
            title.setFont(new Font(20));
            buttons.getChildren().add(title);
            for(OSettingProperty prop : catProperties.get(s)){

                /*TitledPane tp1 = new TitledPane();
                tp1.setText(prop.name);*/
                Label label = new Label(prop.name);
                label.setPrefWidth(334);
                label.setFont(new Font("Arial", 15));
                label.setBackground(normal);
                label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        label.setBackground(hover);
                    }
                });
                label.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        label.setBackground(normal);
                    }
                });
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        getCore().setActivity(prop.settingClass);
                    }
                });
                buttons.getChildren().add(label);
                    /*VBox inside = new VBox();
                    tp1.setContent(inside);
                    tp1.setExpanded(false);
                    setting.setDisplayPanel(inside);
                    setting.onCreate();
                    buttons.getChildren().add(tp1);*/
            }
            tp.getChildren().add(buttons);
        }
        System.out.println(getPane());
        System.out.println(getPane().getChildren());
        getPane().getChildren().add(vboxpanel);
        System.out.println("com.onyx.app.settings.OSettingsApp on create");
    }
}
