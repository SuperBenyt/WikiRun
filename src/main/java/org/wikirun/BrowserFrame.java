package org.wikirun;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.*;
import org.cef.browser.*;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;


public class BrowserFrame {
    private static final long serialVersionUID = -5570653778104813836L;
    private final CefApp cefApp_;
    private final CefClient client_;
    private final CefBrowser browser_;
    private final Component browserUI_;
    private boolean browserFocus_ = true;

    public BrowserFrame(String startURL, boolean useOSR, boolean isTransparent, String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        CefAppBuilder builder = new CefAppBuilder();

        builder.getCefSettings().windowless_rendering_enabled = useOSR;

        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
            }
        });

        if (args.length > 0) {
            builder.addJcefArgs(args);
        }

        try {
            cefApp_ = builder.build();
        } catch (UnsupportedPlatformException e) {
            throw new RuntimeException(e);
        } catch (CefInitializationException e) {
            throw new RuntimeException(e);
        }

        client_ = cefApp_.createClient();

        CefMessageRouter msgRouter = CefMessageRouter.create();
        client_.addMessageRouter(msgRouter);

        browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        browserUI_ = browser_.getUIComponent();

    }

    public JPanel getBrowserUI_() {
        return (JPanel) browserUI_;
    }

    public CefBrowser getBrowser() {
        return browser_;
    }

    public CefClient getClient() {
        return client_;
    }

    public Vector<String> getFrameNames() {return browser_.getFrameNames(); }


}
