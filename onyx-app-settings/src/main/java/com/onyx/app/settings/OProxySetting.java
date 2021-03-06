package com.onyx.app.settings;

import com.onyx.core.OActivity;
import com.onyx.core.setting.ONetworkSetting;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by Hollevoet Yehudi on 3/26/15.
 */
public class OProxySetting extends OActivity implements ONetworkSetting{

    @Override
    public void onCreate() {
        super.onCreate();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(4, 4, 4, 4));
        grid.add(new Label("Host : "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Port : "), 0, 1);
        grid.add(new TextField(), 1, 1);
        getPane().getChildren().add(grid);
    }
}
