package org.bs;

public class DBTest {

    public static void main(String[] args) {
        DBInteraction inter = new DBInteraction("jdbc:sqlite:wikis.db");

        System.out.println(inter.getWikiFromCode("AAAAAA"));
        System.out.println(inter.getWikiFromCode("AAAAAB"));
        inter.insertWiki("Adolf Hitler", "https://de.wikipedia.org/wiki/Adolf_Hitler");
        System.out.println(inter.getWikiFromUrl("https://de.wikipedia.org/wiki/Adolf_Hitler"));
        //inter.insertRun(inter.getWikiFromCode("AAAAAC"), inter.getWikiFromCode("AAAAAA"), 27);
        System.out.println(inter.getRun(1_687_207_498_878L).forDisplay());
    }
}
