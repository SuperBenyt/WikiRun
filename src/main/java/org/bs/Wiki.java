package org.bs;

import java.util.ArrayList;

public class Wiki {
    private String code;
    private String name;
    private String url;

    private static DBInteraction dbInt;

    public Wiki(String code, String name, String url) {
        if (!dbInt.isInDBUrl(url)) throw new IllegalArgumentException("Wiki nicht in DB vorhanden");
        this.code = code;
        this.name = name;
        this.url = url;
    }

    public Wiki(ArrayList<String> resultSetArray) {
        if (!dbInt.isInDBUrl(resultSetArray.get(2))) throw new IllegalArgumentException("Wiki nicht in DB vorhanden");
        this.code = resultSetArray.get(0);
        this.name = resultSetArray.get(1);
        this.url = resultSetArray.get(2);
    }

    public String makeRunCode(Wiki goal) {
        return makeRunCode(this, goal);
    }

    public static String makeRunCode(Wiki start, Wiki goal) {
        return start.getCode() + goal.getCode();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public static void setDbInt(DBInteraction dbInt) {
        Wiki.dbInt = dbInt;
    }

    @Override
    public String toString() {
        return "Wiki{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
