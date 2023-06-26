package org.sql.attributes;


import org.sql.attributes.data.SQLDateTime;

public class SQLDateTimeAttribute extends SQLAttribute<SQLDateTime>{
	public String parseToString()
	{
		return super.getData().getDateTime();
	}
}
