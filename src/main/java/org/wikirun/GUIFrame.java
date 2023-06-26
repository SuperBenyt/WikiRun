package org.wikirun;

import javax.swing.*;

public class GUIFrame {
    private JPanel browser;
    private JPanel rain;
    private JTextField currentSide;
    private JTextField route;
    private JTextField time;

    private void createUIComponents() {
        BrowserFrame browserFrame = null;
        try {
            browserFrame = new BrowserFrame("https://wikipedia.org/", false, false, new String[] {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        browser = (JPanel) browserFrame.getBrowserUI_();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUIFrame");
        frame.setContentPane(new GUIFrame().rain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
