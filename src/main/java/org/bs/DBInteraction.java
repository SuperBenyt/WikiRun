package org.bs;

import kotlin.Pair;
import org.sql.SQLAccess;
import org.sql.SQLInfo;
import org.sql.SQLInsertion;
import org.sql.SQLTableLayout;
import org.sql.attributes.*;
import org.sql.query.SQLSimpleQuery;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBInteraction {
    private SQLInfo info;
    private SQLAccess access = new SQLAccess();

    private SQLTableLayout layoutWikis = new SQLTableLayout(new Pair[]{
            new Pair(SQLAttributeType.STRING, "code"),
            new Pair(SQLAttributeType.STRING, "name"),
            new Pair(SQLAttributeType.STRING, "url")
    });
    private SQLTableLayout layoutRun = new SQLTableLayout(new Pair[]{
            new Pair(SQLAttributeType.STRING, "start"),
            new Pair(SQLAttributeType.STRING, "goal"),
            new Pair(SQLAttributeType.LONG, "time"),
            new Pair(SQLAttributeType.LONG, "date")
    });

    public DBInteraction(String url) {
        info = new SQLInfo(url);
        access.initSQLBackend(info);
        Wiki.setDbInt(this);
        Run.setDbInt(this);
    }

    public void insertWiki(String name, String url) {
        if (isInDBUrl(url)) return;
        access.insert(new SQLInsertion("wiki", new SQLAttributeBase[]{
                new SQLStringAttribute(info, getNextCode()),
                new SQLStringAttribute(info, name),
                new SQLStringAttribute(info, url)
        }));
    }

    public void insertRun(Wiki start, Wiki goal, long time) {
        access.insert(new SQLInsertion("run", new SQLAttributeBase[]{
                new SQLStringAttribute(info, start.getCode()),
                new SQLStringAttribute(info, goal.getCode()),
                new SQLLongAttribute(time),
                new SQLLongAttribute(System.currentTimeMillis())
        }));
    }

    public void insertRun(Run run) {
        access.insert(new SQLInsertion("run", new SQLAttributeBase[]{
                new SQLStringAttribute(info, run.getStart().getCode()),
                new SQLStringAttribute(info, run.getGoal().getCode()),
                new SQLLongAttribute(run.getTime()),
                new SQLLongAttribute(run.getDate())
        }));
    }

    public boolean isInDBCode(String code) {
        access.query(new SQLSimpleQuery(new String[] {"*"}, "wiki", String.format("where code = '%s'", code)));
        try {
            return access.getResultSet().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInDBUrl(String url) {
        access.query(new SQLSimpleQuery(new String[] {"*"}, "wiki", String.format("where url = '%s'",url)));
        try {
            return access.getResultSet().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunInDB(long date) {
        access.query(new SQLSimpleQuery(new String[] {"*"}, "run", String.format("where date = '%d'",date)));
        try {
            return access.getResultSet().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private String getNextCode() {
        access.query(new SQLSimpleQuery(new String[]{"MAX(code)"}, "wiki", ""));
        String temp;
        SQLAttributeType type = SQLAttributeType.STRING;
        temp = type.toString(type.getObjFromResultSet(access.getResultSet(), 1));

        char[] chars = temp.toCharArray();
        //for (int i = chars.length; i > 0; i--) {
        int i = chars.length;
        while (i --> 0) {
            if (chars[i] == 'Z') {
                chars[i] = 'A';
                continue;
            }
            chars[i]++;
            break;
        }
        return String.valueOf(chars);
    }

    public Wiki getWikiFromCode(String code) {
        if (!isInDBCode(code)) return null;
        access.query(new SQLSimpleQuery(new String[]{"*"}, "wiki", String.format("where code = '%s'",code)));
        return new Wiki(access.getResultSetAsStringArraylist(access.getResultSet(), layoutWikis));
    }

    public Wiki getWikiFromUrl(String url) {
        if (!isInDBUrl(url)) return null;
        access.query(new SQLSimpleQuery(new String[]{"*"}, "wiki", String.format("where url = '%s'",url)));
        return new Wiki(access.getResultSetAsStringArraylist(access.getResultSet(), layoutWikis));
    }

    public Run getRun(long date) {
        if (!isRunInDB(date)) return null;
        access.query(new SQLSimpleQuery(new String[] {"*"}, "run", String.format("where date = '%d'",date)));
        return new Run(access.getResultSetAsStringArraylist(access.getResultSet(), layoutRun));
    }

    public ArrayList<Run> getAllRuns() {
        ArrayList<Run> res = new ArrayList<>();
        access.query(new SQLSimpleQuery(new String[]{"*"}, "run", "order by date DESC"));
        ArrayList<ArrayList<String>> temp = access.getResultSetAsStringArraylistArraylist(access.getResultSet(), layoutRun);
        for (ArrayList<String> alS : temp) {
            res.add(new Run(alS));
        }
        return res;
    }

//    public ArrayList<ArrayList<String>> getAllRuns() {
//        access.query(new SQLSimpleQuery(new String[]{"*"}, "run", "order by date DESC"));
//        return access.getResultSetAsStringArraylistArraylist(access.getResultSet(), layoutRun);
//    }

    public Wiki getRandomWiki() {
        access.query(new SQLSimpleQuery(new String[]{"code"}, "wiki", "order by random() limit 1"));
        return getWikiFromCode(access.getResultSetAsStringArraylist(access.getResultSet(), new SQLTableLayout(new Pair[]{
                new Pair<>(SQLAttributeType.STRING, "code")
        })).get(0));
    }
}
