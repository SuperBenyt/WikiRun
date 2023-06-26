package org.bs;

import org.cef.browser.CefBrowser;
import org.cef.callback.CefStringVisitor;

import java.beans.beancontext.BeanContextChildSupport;
import java.util.Scanner;

public class ConsoleInputs implements Runnable {
    private Thread t;

    private SimpleGUI gui;
    private CefBrowser browser;
    //private final String[] vis = new String[]{""};
    private Backend backend;


    public ConsoleInputs(SimpleGUI gui) {
        this.gui = gui;
        this.browser = gui.getBrowser();
        backend = gui.getBackend();
        t = new Thread(this, "Test Console");
        t.start();
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        String temp;
        final String[] visres = new String[1];
        visres[0] = "";
        CefStringVisitor visitor = new CefStringVisitor() {
            @Override
            public void visit(String string) {
                visres[0] = string;
            }
        };

        while (true) {
            temp = sc.nextLine();
            switch (temp) {
                case "getFrameCount":
                    System.out.println(browser.getFrameCount());
                    break;
                case "isPopup":
                    System.out.println(browser.isPopup());
                    break;
                case "hasDocument":
                    System.out.println(browser.hasDocument());
                    break;
                case "viewSource":
                    browser.viewSource();
                    break;
                case "getSource":
                    browser.getSource(visitor);
                    waitForVisitor();
                    System.out.println(visres[0]);
                    visres[0] = "";
                    break;
                case "getText":
                    browser.getText(visitor);
                    waitForVisitor();
                    System.out.println(visres[0]);
                    visres[0] = "";
                    break;
                case "goBack":
                    browser.goBack();
                    break;
                case "loadURL":
                    System.out.println("URL:");
                    browser.loadURL(sc.nextLine());
                    break;
                case "getURL":
                    System.out.println(browser.getURL());
                    break;
                case "title":
                    CefStringVisitor visi = new CefStringVisitor() {
                        @Override
                        public void visit(String string) {
                            visres[0] = string;
                        }
                    };
                    //VisitorASync a = new VisitorASync(vis, e -> {browser.getSource(e);});
                    browser.getSource(visi);
                    while(visres[0].equals("")) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //System.out.println(".");
                    }
                    //visres[0] = test(visres);
                    //System.out.println(visres[0]);
                    String s = visres[0];
                    System.out.println(s.substring(s.indexOf("<title>") + 7, s.indexOf("</title>")));
                    visres[0] = "";
                    break;
                case "addCurrent":
                    backend.addCurrentWiki();
                    break;
                case "ranRun":
                    System.out.println(backend.createRandomRun());
                    break;
                case "siteLoad":
                    backend.newSiteLoaded();
                    break;
                case "timer":
                    backend.startStopwatch();
                    break;
                case "vis":
                    gui.setBrowserVisible();
                    break;
                case "change":
                    gui.changePanel();
                    break;
                case "startScr":
                    gui.showStartScreen();
                    break;
                case "browserScr":
                    gui.showBrowser();
                    break;
                case "endScr":
                    gui.showEndScreen(backend.getAllRuns().get(0));
                    break;
                case "Rizz":
                    browser.loadURL("https://cdn.discordapp.com/attachments/884752080194125885/1122961447543132241/image.png");
                    break;
                case "High-Rizz":
                    browser.loadURL("https://cdn.discordapp.com/attachments/884752080194125885/1122962270067105872/image.png");
                    break;
                default:
                    System.out.println("Wrong Statement");
            }
        }
    }

    public String test(String[] ray) {
        while(ray[0].equals("")) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(".");
        }
        return ray[0];
    }

    private ThreadDeath waitForVisitor() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ThreadDeath td) {
            return td;
        }
        try {
            return (ThreadDeath) (Object) new BeanContextChildSupport();
        } catch (Exception e) {
            return null;
        }
    }

}
