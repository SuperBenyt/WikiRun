package org.bs;

import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.wikirun.BrowserFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SimpleGUI {

    private BrowserFrame browser;
    private Backend backend;

    private boolean first = true;

    private JFrame frame;
    private JPanel menuBar;
    private JTextField currentSite;
    private JTextField currentRun;
    private JTextField timer;
    private JButton stop;

    private JPanel startScreen;
    private JTextArea runCodeText;
    private JTextField runCodeInput;
    private JButton runCodeButton;
    private JTextArea ranRunText;
    private JButton ranRunButton;
    private JTextArea lastRunsText;
    private JScrollPane lastRunsScroll;
    private JTable lastRunsScrollData;
    private DefaultTableModel lastRunTM;

    private JPanel endScreen;
    private JLabel trophy;
    private JTextPane congrats;
    private JTextPane runInfo;
    private JPanel endBar;
    private JTextArea endRanRunText;
    private JButton endRanRunButton;
    private JTextArea endRunCodeText;
    private JTextField endRunCodeInput;
    private JButton endRunCodeButton;

    public SimpleGUI(String[] args) {
        frame = new JFrame();
        Font font = new Font(null, Font.PLAIN, 20);

        menuBar = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;


        currentSite = new JTextField("Demokratie");
        currentSite.setEditable(false);
        currentSite.setFont(font);
        c.gridx = 0;
        c.gridwidth = 5;
        c.weightx = 0.5;
        menuBar.add(currentSite, c);

        currentRun = new JTextField("Frankfurt Hauptbahnhof ⇒ Deutschland");
        currentRun.setEditable(false);
        currentRun.setFont(font);
        c.gridx = 5;
        c.gridwidth = 5;
        c.weightx = 1;
        menuBar.add(currentRun, c);

        timer = new JTextField("00:00:00");
        timer.setEditable(false);
        timer.setFont(font);
        c.gridx = 10;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0;
        menuBar.add(timer, c);

        stop = new JButton("Aufgeben");
        stop.setFont(font);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backend.abortRun();
            }
        });
        c.gridx = 11;
        menuBar.add(stop, c);

        ImageIcon icon = new ImageIcon("src/main/resources/wikipedia.png");
        frame.setIconImage(icon.getImage());



        startScreen = new JPanel(new GridBagLayout());
        startScreen.setBackground(Color.WHITE);
        startScreen.setBorder(new EmptyBorder(10, 10, 10, 10));
        c = new GridBagConstraints();
        c.gridheight = 1;
        c.weightx = 0;

        runCodeText = new JTextArea("Run Code eingeben");
        runCodeText.setEditable(false);
        runCodeText.setFont(font);
        //runCodeText.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.fill = GridBagConstraints.LINE_START;
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        startScreen.add(runCodeText, c);

        JTextArea xspacer = new JTextArea();
        xspacer.setEditable(false);
        //xspacer.setBorder(new LineBorder(Color.RED));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridwidth = 6;
        c.weightx = 1;
        startScreen.add(xspacer, c);

        runCodeInput = new JTextField();
        runCodeInput.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        c.gridy = 3;
        startScreen.add(runCodeInput, c);

        runCodeButton = new JButton("Go");
        runCodeButton.setFont(font);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridwidth  = 1;
        //c.weightx = 0;
        startScreen.add(runCodeButton, c);

        runCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backend.startRun(backend.getRunFromCode(runCodeInput.getText()));
                runCodeInput.setText("");
            }
        });

        xspacer = new JTextArea();
        xspacer.setEditable(false);
        //xspacer.setBorder(new LineBorder(Color.RED));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridwidth = 5;
        c.weightx = 1;
        startScreen.add(xspacer, c);

        JTextArea yspacer = new JTextArea();
        yspacer.setEditable(false);
        //yspacer.setBorder(new LineBorder(Color.GREEN));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridwidth = 10;
        c.gridy = 4;
        startScreen.add(yspacer, c);

        ranRunText = new JTextArea("Random Run");
        ranRunText.setEditable(false);
        ranRunText.setFont(font);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        c.gridy = 5;
        startScreen.add(ranRunText, c);

        ranRunButton = new JButton("Go");
        ranRunButton.setFont(font);
        c.gridx = 2;
        c.gridwidth = 1;
        startScreen.add(ranRunButton, c);

        ranRunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changePanel();
                if (first) {
                    backend.startFirstRun();
                    first = false;
                }
                else {
                    backend.createRandomRun();
                }
            }
        });

        xspacer = new JTextArea();
        xspacer.setEditable(false);
        //xspacer.setBorder(new LineBorder(Color.RED));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridwidth = 5;
        c.weightx = 1;
        startScreen.add(xspacer, c);

        yspacer = new JTextArea();
        yspacer.setEditable(false);
        //yspacer.setBorder(new LineBorder(Color.GREEN));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridwidth = 10;
        c.gridy = 6;
        startScreen.add(yspacer, c);

        lastRunsText = new JTextArea("Letzte Runs");
        lastRunsText.setEditable(false);
        lastRunsText.setFont(font);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        c.gridy = 7;
        startScreen.add(lastRunsText, c);

        lastRunTM = new DefaultTableModel(new String[] {"Start", "Ziel", "Zeit", "Datum"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lastRunsScrollData = new JTable(lastRunTM);
        lastRunsScrollData.setRowHeight(lastRunsScrollData.getRowHeight() + 5);
        lastRunsScrollData.setFont(font);
        lastRunsScrollData.getTableHeader().setFont(font);
        lastRunsScrollData.setBackground(Color.WHITE);
        lastRunsScrollData.getTableHeader().setBackground(Color.WHITE);
        lastRunsScroll = new JScrollPane(lastRunsScrollData, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        lastRunsScroll.setBackground(Color.WHITE);
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 10;
        c.gridy = 8;
        c.gridheight = 10;
        c.weighty = 1;
        startScreen.add(lastRunsScroll, c);
        lastRunsScroll.setPreferredSize(new Dimension(frame.getPreferredSize().width, 200));



        endScreen = new JPanel(new GridBagLayout());
        endScreen.setBackground(Color.WHITE);
        endScreen.setBorder(new EmptyBorder(10, 10, 10, 10));
        c = new GridBagConstraints();
        c.gridheight = 1;

        trophy = new JLabel(new ImageIcon("src/main/resources/mario.png"));
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridwidth = 1;
        c.weightx = 1;
        c.gridy = 1;
        c.weighty = 1;
        endScreen.add(trophy, c);

        congrats = new JTextPane();
        congrats.setFont(new Font("", Font.ITALIC, 30));
        congrats.setForeground(Color.ORANGE);
        congrats.setEditable(false);
        //runInfo.setBorder(new LineBorder(Color.RED));
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
        congrats.setParagraphAttributes(attr, false);
        congrats.setText("Congratulations");
        c.weightx = 0;
        c.gridy = 2;
        c.weighty = 0.5;
        endScreen.add(congrats, c);

        runInfo = new JTextPane();
        runInfo.setFont(new Font("", Font.PLAIN, 28));
        //runInfo.setForeground(Color.ORANGE);
        runInfo.setEditable(false);
        //runInfo.setBorder(new LineBorder(Color.RED));
        attr = new SimpleAttributeSet();
        StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
        runInfo.setParagraphAttributes(attr, false);
        c.weightx = 0;
        c.gridy = 3;
        c.weighty = 0.5;
        endScreen.add(runInfo, c);


        endBar = new JPanel(new GridBagLayout());
        endBar.setBackground(Color.WHITE);
        c = new GridBagConstraints();
        c.gridheight = 1;

        endRanRunText = new JTextArea("Random Run");
        endRanRunText.setFont(font);
        endRanRunText.setEditable(false);
        c.gridx = 0;
        //c.gridy = 3;
        //c.weighty = 0;
        endBar.add(endRanRunText, c);

        endRanRunButton = new JButton("Go");
        endRanRunButton.setFont(font);
        c.gridx = 1;
        endBar.add(endRanRunButton, c);

        endRanRunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changePanel();
                backend.createRandomRun();
            }
        });

        xspacer = new JTextArea();
        c.gridx = 2;
        c.weightx = 0.5;
        endBar.add(xspacer, c);

        endRunCodeText = new JTextArea("Run Code eingeben:");
        endRunCodeText.setFont(font);
        endRunCodeText.setEditable(false);
        c.gridx = 3;
        c.weightx = 0;
        endBar.add(endRunCodeText, c);

        endRunCodeInput = new JTextField();
        endRunCodeInput.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.weightx = 0.5;
        endBar.add(endRunCodeInput, c);

        endRunCodeButton = new JButton("Go");
        endRunCodeButton.setFont(font);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 5;
        c.weightx = 0;
        endBar.add(endRunCodeButton, c);

        endRunCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backend.startRun(backend.getRunFromCode(endRunCodeInput.getText()));
                endRunCodeInput.setText("");
            }
        });

        //frame.add(endBar, BorderLayout.SOUTH);

        backend = new Backend(this, new DBInteraction("jdbc:sqlite:wikis.db"));
        try {
            browser = new BrowserFrame(backend.createRandomRunWoStart().getStart().getUrl(), false, false, args);
        } catch (UnsupportedPlatformException e) {
            throw new RuntimeException(e);
        } catch (CefInitializationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        browser.getClient().addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
                //backend.newSiteLoaded();
                System.out.println("Display");
            }
        });
        //browser.getBrowserUI_().setVisible(false);


//        JLayeredPane layer = new JLayeredPane();
//        layer.add(browser.getBrowserUI_(), 0);
//        browser.getBrowserUI_().setVisible(true);
//        layer.add(startScreen, 1);
//        startScreen.setVisible(true);
//        frame.add(layer, BorderLayout.CENTER);

        frame.add(startScreen, BorderLayout.CENTER);
        //startScreen.setVisible(false);
        //frame.add(browser.getBrowserUI_(), BorderLayout.CENTER);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Wikirun");
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                frame.dispose();
                System.out.println("Yeet");
            }
        });


        //backend = new Backend(this, new DBInteraction("jdbc:sqlite:wikis.db"), false);
        backend.addBrowser();
        new ConsoleInputs(this);
        //backend.createRandomRun();
        //showStartScreen();
        setRunTable(backend.getAllRunsFormat());
    }

    public static void main(String[] args) {
        new SimpleGUI(args);
    }


    public void changePanel() {
        if (startScreen.isVisible()) {
            startScreen.setVisible(false);
            frame.remove(startScreen);
            frame.add(browser.getBrowserUI_(), BorderLayout.CENTER);
            frame.pack();frame.remove(startScreen);
            frame.remove(browser.getBrowserUI_());
        }
        else {
            startScreen.setVisible(true);

            frame.add(startScreen, BorderLayout.CENTER);
            frame.pack();
        }
    }


    public void showBrowser() {
        Dimension temp = frame.getSize();
        frame.remove(startScreen);
        frame.remove(browser.getBrowserUI_());
        frame.remove(endScreen);
        frame.remove(endBar);
        frame.add(browser.getBrowserUI_());
        frame.add(menuBar, BorderLayout.NORTH);
        frame.pack();
        frame.setSize(temp);
    }

    public void showStartScreen() {
        Dimension temp = frame.getSize();
        frame.remove(startScreen);
        frame.remove(browser.getBrowserUI_());
        frame.remove(endScreen);
        frame.remove(endBar);
        frame.remove(menuBar);
        frame.add(startScreen);
        frame.pack();
        frame.setSize(temp);
    }

    public void showEndScreen(Run finishedRun) {
        Dimension temp = frame.getSize();
        frame.remove(startScreen);
        frame.remove(browser.getBrowserUI_());
        frame.remove(endScreen);
        frame.remove(endBar);
        frame.remove(menuBar);
        runInfo.setText(finishedRun.forDisplay());
        frame.add(endScreen);
        frame.add(endBar, BorderLayout.SOUTH);
        frame.pack();
        frame.setSize(temp);
    }

    public void setRunTable(ArrayList<ArrayList<String>> data) {
        for (ArrayList<String> d : data) {
            lastRunTM.addRow(d.toArray());
        }
    }

    public void addToRunTable(ArrayList<String> data) {
        lastRunTM.insertRow(0, data.toArray());
    }


    public void setCurrentSiteTitle(String title) {
        currentSite.setText(title);
    }

    public void setCurrentRunTitle(String title) {
        currentRun.setText(title);
    }

    public void setBrowserVisible() {
        browser.getBrowserUI_().setVisible(false);
    }

    public void setTimer(String time) {
        timer.setText(time);
    }

    public CefBrowser getBrowser() {
        return browser.getBrowser();
    }

    public Backend getBackend() {
        return backend;
    }
}
