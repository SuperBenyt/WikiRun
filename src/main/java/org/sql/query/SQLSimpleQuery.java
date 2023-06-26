package org.sql.query;



import org.logger.Logger;
import org.sql.SQLAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLSimpleQuery extends SQLQuery {
	private String[] attributes;
	private String table;
	private String appendix;

	public SQLSimpleQuery(String[] attributes, String table, String appendix) {
		this.attributes = attributes;
		this.table = table;
		this.appendix = appendix;
	}

	@Override
	public String generateStatementTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (String a :
			attributes) {
			sb.append(String.format("%s,", a));
		}
		sb.replace(sb.length()-1, sb.length(), "");
		sb.append(String.format(" FROM %s %s", table, appendix));
		return sb.toString();
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
