package org.sql.query;



import org.sql.SQLAccess;
import org.sql.SQLOperation;

import java.sql.PreparedStatement;

public class SQLQuery implements SQLOperation {

	protected SQLQuery() {}

	@Override
	public String generateStatementTemplate() {
		return null;
	}

	@Override
	public PreparedStatement generateStatement(SQLAccess access) {
		return null;
	}
}
