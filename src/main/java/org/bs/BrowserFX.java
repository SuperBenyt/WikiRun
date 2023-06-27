package org.bs;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import javax.swing.*;

public class BrowserFX {

    private JFXPanel jfxpanel = new JFXPanel();
    private WebEngine engine;
    private WebView view;
    private Scene scene;

    public BrowserFX(String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                view = new WebView();
                engine = view.getEngine();
                loadURL(url);
                scene = new Scene(view);
                jfxpanel.setScene(scene);
            }
        });
    }

    public BrowserFX() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                view = new WebView();
                engine = view.getEngine();
                //loadURL(url);
                jfxpanel.setScene(new Scene(view));
            }
        });
    }

    public void loadURL(String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.load(url);
            }
        });
    }

    public String goBack() {
        final WebHistory history = engine.getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();

//        Platform.runLater(() ->
//        {
//            history.go(entryList.size() > 1
//                    && currentIndex > 0
//                    ? -1
//                    : 0);
//        });
        return entryList.get(currentIndex<entryList.size()-1?currentIndex+1:currentIndex).getUrl();
    }

    public void setScene() {
        jfxpanel = new JFXPanel();
        jfxpanel.setScene(scene);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public JFXPanel getJfxpanel() {
        return jfxpanel;
    }

    public WebEngine getEngine() {
        return engine;
    }

    public WebHistory getWebHistory() {
        return engine.getHistory();
    }
}
