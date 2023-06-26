package org.sql;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.logger.Logger;
import org.sql.attributes.*;

public class SQLInsertion implements SQLOperation {
	//private String schema;
	private String table;

	private SQLAttributeBase[] attributes;

	public SQLInsertion(String table, SQLAttributeBase[] attributes) {
		//this.schema = schema;
		this.table = table;
		this.attributes = attributes;
	}

	@Override
	public String generateStatementTemplate()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("insert into %s values (", this.table));
		for (SQLAttributeBase a :
			attributes) {
			String wildcard = "?";
			if (a instanceof SQLDefaultAttribute) wildcard = "default";
			sb.append(String.format("%s, ", wildcard));
		}
		sb.replace(sb.length()-2, sb.length(), "");
		sb.append(")");
		return sb.toString();
	}

	@Override
	public PreparedStatement generateStatement(SQLAccess access)
	{
		PreparedStatement ps;
		try {
			ps = access.getConnection().prepareStatement(generateStatementTemplate());
		} catch (SQLException e) {
			Logger.logError("PreparedStatement could not be generated!");
			throw new RuntimeException(e); // return null;
		}

		int ioffset = 0;
		for (int i = 0; i < attributes.length; ++i) {
			SQLAttributeBase a = attributes[i];
			if (a instanceof SQLIntAttribute)
				SQLAttributeType.INT.setPreparedStatement(ps, i+1-ioffset, ((SQLIntAttribute) a).getData());
			else if (a instanceof SQLLongAttribute)
				SQLAttributeType.LONG.setPreparedStatement(ps, i+1-ioffset, ((SQLLongAttribute) a).getData());
			else if (a instanceof SQLStringAttribute)
				SQLAttributeType.STRING.setPreparedStatement(ps, i+1-ioffset, ((SQLStringAttribute) a).getData());
			else if (a instanceof SQLDateTimeAttribute)
				SQLAttributeType.DATETIME.setPreparedStatement(ps, i+1-ioffset, ((SQLDateTimeAttribute) a).getData());
			else if (a instanceof SQLDefaultAttribute)
				++ioffset;
			else
			{
				Logger.logError("Attribute type not recognized!");
				throw new RuntimeException();
			}
		}

		return ps;
	}
}
