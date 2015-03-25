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
        webEngine.load("http://google.fr");
        getPane().getChildren().add(browser);
    }

    @Override
    public void backBtn() {
        final WebHistory history = browser.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();

        Platform.runLater(new Runnable() {
            public void run() {
                history.go(1);
            }
        });
        if (currentIndex < entryList.size() - 2) {
            entryList.get(currentIndex + 1);
            // dans le cas ou il n'y a plus d'historique il dechanche le btn retour classique qui va "normalement" faire un retour au launcher
            super.backBtn();
        } else {
            entryList.get(history.getCurrentIndex());
            browser.getEngine().load(entryList.get(currentIndex).getUrl());
        }
    }
}