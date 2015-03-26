package com.onyx.app.settings;

import com.onyx.core.setting.ONetworkSetting;
import com.onyx.core.setting.OSetting;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hollevoet Yehudi on 3/26/15.
 */
public class OWifiSetting extends OSetting implements ONetworkSetting{


    @Override
    public void onCreate() {
        super.onCreate();
        final VBox vbox=new VBox(5);
        List<String> ssids = new ArrayList<>();
        Background normal= new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY));
        Background hover= new Background(new BackgroundFill(Color.LIGHTSALMON, new CornerRadii(5), Insets.EMPTY));
        ssids.add("univ-nantes");
        ssids.add("eduroam");
        vbox.getChildren().add(0,new Label("Not connected"));
        for(String ssid : ssids){
            Label label = new Label(ssid);
            label.setPrefWidth(334);
            label.setFont(new Font("Arial", 20));
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
                    vbox.getChildren().remove(0);
                    vbox.getChildren().add(0,new Label("Connected to "+label.getText()));
                }
            });
            vbox.getChildren().add(label);
        }
        getPane().getChildren().add(vbox);
    }
}
