package com.onyx.webbrowser;

import com.onyx.core.OActivity;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by Maxime on 26/03/15.
 */
public class OWebBrowserSetting extends OActivity {

    @Override
    public void onCreate() {
        super.onCreate();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(4, 4, 4, 4));
        grid.add(new Label("Homepage : "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Your email : "), 0, 1);
        grid.add(new TextField(), 1, 1);
        getPane().getChildren().add(grid);
    }

}
