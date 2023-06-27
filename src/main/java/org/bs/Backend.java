package org.bs;

import javafx.scene.web.WebEngine;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.cef.browser.CefBrowser;
import org.cef.callback.CefStringVisitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Backend {
    private SimpleGUI gui;
    private DBInteraction dbInt;
    private BrowserFX browserFX;
    //private CefBrowser browser;
    private boolean testMode;

    private WatchStop watch;

    private Run currentRun;

    public Backend(SimpleGUI gui, DBInteraction dbInt, boolean testMode) {
        this.gui = gui;
        this.dbInt = dbInt;
        //this.browser = gui.getBrowser();
        this.browserFX = gui.getBrowserFX();
        this.testMode = testMode;
    }

    public Backend(SimpleGUI gui, DBInteraction dbInt) {
        this.gui = gui;
        this.dbInt = dbInt;
        testMode = false;
    }

    public Run createRandomRun() {
        Wiki start = dbInt.getRandomWiki();
        Wiki goal = dbInt.getRandomWiki();
        while (start.getCode().equals(goal.getCode())) goal = dbInt.getRandomWiki();
        startRun(new Run(start, goal));
        return currentRun;
    }

    public Run createRandomRunWoStart() {
        Wiki start = dbInt.getRandomWiki();
        Wiki goal = dbInt.getRandomWiki();
        while (start.getCode().equals(goal.getCode())) goal = dbInt.getRandomWiki();
        currentRun = new Run(start, goal);
        gui.setCurrentSiteTitle(currentRun.getStart().getName());
        gui.setCurrentRunTitle(currentRun.toString());
        return currentRun;
    }

    /**
     * FX
     * @param run
     */
    public void startRun(Run run) {
        currentRun = run;
        gui.setCurrentSiteTitle(currentRun.getStart().getName());
        gui.setCurrentRunTitle(currentRun.toString());
        browserFX.loadURL(currentRun.getStart().getUrl());
        gui.showBrowser();
        //browser.loadURL(currentRun.getStart().getUrl());
        System.out.println(currentRun.getStart().getUrl());
        startStopwatch();
    }

    public void startFirstRun() {
        gui.showBrowser();
        startStopwatch();
    }

    public void abortRun() {
        currentRun = null;
        stopStopwatch();
        watch = null;
        gui.showStartScreen();
    }

    /**
     * JCEF
     */
//    public void newSiteLoaded() {
//        if (!browser.getURL().contains("https://de.wikipedia.org/wiki/")) {
//            //if (browser.canGoBack()) browser.goBack();
//            System.out.println("Nicht gut");
//        }
//        else if (currentRun.getGoal().getCode().equals(dbInt.getWikiFromUrl(browser.getURL()).getCode())) {
//            gui.showEndScreen(currentRun.finishRun(stopStopwatch(), System.currentTimeMillis()));
//            dbInt.insertRun(currentRun);
//            gui.addToRunTable(currentRun.forTable());
//            currentRun = null;
//
//        }
//        else {
//            gui.setCurrentSiteTitle(getTitleFromSourceCode());
//            addCurrentWiki();
//        }
//    }

    /**
     * FX
     * @param url
     * @param title
     */
    public void newSiteLoaded(String url, String title, String oldURL) {
        if (currentRun == null) return;
        if (!url.contains("https://de.wikipedia.org/wiki/")) {
            browserFX.loadURL(oldURL);
            System.out.println("Nicht gut");
        }
        else if (!dbInt.isInDBUrl(url)) {
            if (title == null) return;
            title = title.replace(" – Wikipedia", "");
            gui.setCurrentSiteTitle(title);
            addCurrentWiki(url, title);
        }
        else if (currentRun.getGoal().getCode().equals(dbInt.getWikiFromUrl(url).getCode())) {
            currentRun = dbInt.insertRun(currentRun.getStart(), currentRun.getGoal(), stopStopwatch());
            gui.showEndScreen(currentRun);
            gui.addToRunTable(currentRun.forTable());
            currentRun = null;

        }
    }

    /**
     * JCEF
     */
//    public void addCurrentWiki() {
//        if (testMode) return;
//        if (browser.getURL().contains("#/media/")) return;
//        dbInt.insertWiki(getTitleFromSourceCode(), browser.getURL());
//        System.out.println("Added Wiki:" + getTitleFromSourceCode());
//    }

    /**
     * FX
     * @param url
     * @param title
     */
    public void addCurrentWiki(String url ,String title) {
        if (testMode) return;
        if (url.contains("#/media/")) return;
        title = title.replace(" – Wikipedia", "");
        dbInt.insertWiki(title, url);
        System.out.println("Added Wiki:" + title);
    }

    public Run getRunFromCode(String code) {
        if (code.length() != 12) return null;
        code = code.toUpperCase();
        String cS = code.substring(0, 6);
        String cG = code.substring(6, 12);
        if (!dbInt.isInDBCode(cS) || !dbInt.isInDBCode(cG) || cS.equals(cG)) return null;
        currentRun = new Run(dbInt.getWikiFromCode(cS), dbInt.getWikiFromCode(cG));
        return currentRun;
    }

    public ArrayList<Run> getAllRuns() {
        return dbInt.getAllRuns();
    }

    public ArrayList<ArrayList<String>> getAllRunsFormat() {
        ArrayList<Run> data = dbInt.getAllRuns();
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        for (Run r : data) {
            res.add(r.forTable());
        }
        return res;
    }

    public void startStopwatch() {
        watch = new WatchStop(gui);
        watch.start();
    }

    private long stopStopwatch() {
        return watch.stop();
    }

    /**
     * JCEF
     * redundant
     * @return Title
     */
//    private String getTitleFromSourceCode() {
//        final String[] sourceCode = new String[]{""};
//        CefStringVisitor visitor = new CefStringVisitor() {
//            @Override
//            public void visit(String string) {
//                sourceCode[0] = string;
//            }
//        };
//        browser.getSource(visitor);
//        while(sourceCode[0].equals("")) {
//            try {
//                Thread.sleep(10);
//            } catch (Exception e) {
//                System.err.println(e);
//            }
//            //System.out.println(".");
//        }
//        String s = sourceCode[0];
//        s = s.substring(s.indexOf("<title>") + 7, s.indexOf("</title>"));
//        s = s.replace(" – Wikipedia", "");
//        return s;
//    }

    /**
     * Very JCEF
     */
//    public void addBrowser() {
//        this.browser = gui.getBrowser();
//    }

    public Run getCurrentRun() {
        return currentRun;
    }

    public void setBrowserFX(BrowserFX browserFX) {
        this.browserFX = browserFX;
    }
}
