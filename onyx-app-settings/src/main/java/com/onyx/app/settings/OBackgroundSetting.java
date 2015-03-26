package com.onyx.app.settings;

import com.onyx.core.OActivity;
import com.onyx.core.setting.ODesignSetting;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.layout.*;

/**
 * Created by wolkom on 3/26/15.
 */
public class OBackgroundSetting extends OActivity implements ODesignSetting {
    @Override
    public void onCreate() {
        super.onCreate();
        HBox box = new HBox();
        final Label label = new Label("Color : ");
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.RED);

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                getCore().getGui().getMainPane().setBackground(new Background(new BackgroundFill(colorPicker.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        box.getChildren().addAll(label, colorPicker);
        getPane().getChildren().add(box);
    }
}
