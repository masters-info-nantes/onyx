package com.onyx.webbrowser;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import com.onyx.core.OActivity;
import com.onyx.core.OActivityToolbar;

/**
 * Created by Maxime on 09/02/15.
 */
//Change to OActivityToolbar after
public class OWebBrowser extends OActivity {
    private WebView browser = new WebView();
    private WebEngine webEngine = browser.getEngine();

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("run Browser");
        browser = new WebView();
        webEngine = browser.getEngine();
        webEngine.setUserAgent("Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
        webEngine.load("http://www.google.fr");
        browser.setMaxHeight(536);
        browser.setMaxWidth(334);

        getPane().getChildren().add(browser);
    }

    @Override
    public void backBtn() {
        final WebHistory history = browser.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();

        if(history.getCurrentIndex() == 0) {
            super.backBtn();
            return;
        }

        Platform.runLater(new Runnable() {
            public void run() {
                history.go(- 1);
            }
        });


    }
}