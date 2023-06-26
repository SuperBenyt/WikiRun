package org.sql;


import kotlin.Pair;
import org.logger.Logger;
import org.sql.attributes.SQLAttributeType;
import org.sql.query.SQLSimpleQuery;

public class SQLTest {
	public static void main(String[] args) {
		SQLInfo info = new SQLInfo("jdbc:sqlite:wikis.db");
		SQLAccess access = new SQLAccess();
		access.initSQLBackend(info);
//		access.insert(new SQLInsertion("triolingo", "card", new SQLAttributeBase[] {
//			new SQLStringAttribute(info, "Bottle"),
//			new SQLStringAttribute(info, "Flasche"),
//			new SQLIntAttribute(0)
//		}));
		//access.acuteQuery("SELECT * FROM triolingo.card");
		access.query(new SQLSimpleQuery(new String[] {"*"}, "wiki", ""));
		access.printResultSet(access.getResultSet(), new SQLTableLayout(new Pair[] {
			//new Pair(SQLAttributeType.INT, "cardId"),
			new Pair(SQLAttributeType.STRING, "code"),
			new Pair(SQLAttributeType.STRING, "name"),
				new Pair(SQLAttributeType.STRING, "url")
			//new Pair(SQLAttributeType.INT, "CardSetId"),
		}), Logger::logInfo);
	}
}
