package org.bs;

import org.apache.commons.lang3.time.StopWatch;

public class WatchStop implements Runnable{

    private Thread t;
    private SimpleGUI gui;
    private StopWatch watch;
    private boolean running;

    public WatchStop(SimpleGUI gui) {
        this.gui = gui;
        watch = new StopWatch();
        t = new Thread(this, "Stopwatch");
    }

    public void start() {
        running = true;
        t.start();
    }

    public long stop() {
        if (!running) return 0;
        running = false;
        return getTime();
    }

    private void setTimeGUI() {
        String s = watch.formatTime();
        gui.setTimer(s.substring(0, s.length() - 4));
    }

    public long getTime() {
        return watch.getTime();
    }

    @Override
    public void run() {
        watch.start();
        while(running) {
            setTimeGUI();
        }
        watch.stop();
        setTimeGUI();
    }
}
