package org.sql;


import org.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDeletion implements SQLOperation {
	private String table;
	private String condition;

	public SQLDeletion(String table, String condition) {
		this.table = table;
		this.condition = condition;
	}

	@Override
	public String generateStatementTemplate() {
		return String.format("DELETE FROM %s WHERE %s", table, condition);
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
