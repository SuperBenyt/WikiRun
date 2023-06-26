package org.bs;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Run {
    private Wiki start;
    private Wiki goal;
    private long time;
    private long date;
    private boolean finished;
    private static DBInteraction dbInt;

    public Run(Wiki start, Wiki goal, long time, long date) {
        if (!dbInt.isRunInDB(date)) throw new IllegalArgumentException("Run nicht in DB vorhanden");
        this.start = start;
        this.goal = goal;
        this.time = time;
        this.date = date;
        finished = true;
    }

    public Run(ArrayList<String> resultSetArray) {
        this.start = dbInt.getWikiFromCode(resultSetArray.get(0));
        this.goal = dbInt.getWikiFromCode(resultSetArray.get(1));
        this.time = Long.parseLong(resultSetArray.get(2));
        this.date = Long.parseLong(resultSetArray.get(3));
        finished = true;
        if (!dbInt.isRunInDB(date)) throw new IllegalArgumentException("Run nicht in DB vorhanden");
    }

    public Run(Wiki start, Wiki goal) {
        this.start = start;
        this.goal = goal;
    }

    public Run finishRun(long time, long date) {
        if (!dbInt.isRunInDB(date)) throw new IllegalArgumentException("Run nicht in DB vorhanden");
        this.time = time;
        this.date = date;
        finished = true;
        return this;
    }

    public static void setDbInt(DBInteraction dbInt) {
        Run.dbInt = dbInt;
    }

    public Wiki getStart() {
        return start;
    }

    public Wiki getGoal() {
        return goal;
    }

    public long getTime() {
        return time;
    }

    public long getDate() {
        return date;
    }

    public boolean isFinished() {
        return finished;
    }

    public String forDisplay() {
        String t = DurationFormatUtils.formatDurationHMS(time);
        String d = new SimpleDateFormat("dd.MM.yyyy  HH:mm").format(new Date(date));
        //return String.format("%50s ⇒ %50s %10s %20s", start.getName(), goal.getName(), t.substring(0, t.length() - 4), d);
        return String.format("%s ⇒ %s %s %s", start.getName(), goal.getName(), t.substring(0, t.length() - 4), d);
    }

    public ArrayList<String> forTable() {
        String t = DurationFormatUtils.formatDurationHMS(time);
        String d = new SimpleDateFormat("dd.MM.yyyy  HH:mm").format(new Date(date));
        ArrayList<String> res = new ArrayList<>();
        res.add(start.getName());
        res.add(goal.getName());
        res.add(t.substring(0, t.length() - 4));
        res.add(d);
        return res;
    }

    @Override
    public String toString() {
        if (finished) {
            return "Run{" +
                    "start=" + start.getName() +
                    ", goal=" + goal.getName() +
                    ", time=" + time +
                    ", date=" + new Date(date) +
                    ", finished=" + finished +
                    '}';
        }
        return start.getName() + " ⇒ " + goal.getName();
    }
}
