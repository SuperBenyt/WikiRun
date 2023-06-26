package org.sql;


import javafx.util.Pair;
import org.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUpdate implements SQLOperation {
	private String schema;
	private String table;
	private Pair<String, String>[] updates;
	private String appendix;

	public SQLUpdate(String schema, String table, Pair<String, String>[] updates, String appendix) {
		this.schema = schema;
		this.table = table;
		this.updates = updates;
		this.appendix = appendix;
	}

	@Override
	public String generateStatementTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("UPDATE %s.%s SET", schema, table));
		for (Pair<String, String> u :
			updates) {
			sb.append(String.format(" %s=%s,", u.getKey(), u.getValue()));
		}
		if (updates.length > 0) sb.replace(sb.length()-1, sb.length(), "");
		sb.append(String.format(" %s", appendix));
		return sb.toString();
	}

	@Override
	public PreparedStatement generateStatement(SQLAccess access) {
		PreparedStatement ps;
		try {
			ps = access.getConnection().prepareStatement(generateStatementTemplate());
		} catch (SQLException e) {
			Logger.logError("PreparedStatement could not be created!");
			throw new RuntimeException(e); // return null;
		}
		return ps;
	}
}
