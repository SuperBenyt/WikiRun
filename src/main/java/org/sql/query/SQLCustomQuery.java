package org.sql.query;



import org.logger.Logger;
import org.sql.SQLAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCustomQuery extends SQLQuery {
	private String prompt;

	public SQLCustomQuery(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public String generateStatementTemplate() {
		return prompt;
	}

	@Override
	public PreparedStatement generateStatement(SQLAccess access) {
		PreparedStatement ps;
		try {
			ps = access.getConnection().prepareStatement(generateStatementTemplate());
		} catch (SQLException e) {
			Logger.logError("PreparedStatement could not be generated!");
			throw new RuntimeException(e); // return null;
		}
		return ps;
	}
}
