package org.sql;


import kotlin.Pair;
import org.sql.attributes.SQLAttributeType;

public class SQLTableLayout {
	private SQLAttributeType[] layout;
	private String[] alias;

	public SQLTableLayout(Pair<SQLAttributeType, String>[] layout) {
		this.layout = new SQLAttributeType[layout.length];
		this.alias = new String[layout.length];

		for (int i = 0; i < layout.length; ++i)
		{
			this.layout[i] = layout[i].getFirst();
			this.alias[i] = layout[i].getSecond();
		}
	}

	public SQLAttributeType getTypeAt(int pos)
	{
		if (pos > layout.length) throw new IndexOutOfBoundsException();
		return layout[pos];
	}

	public String getAliasAt(int pos)
	{
		if (pos > alias.length) throw new IndexOutOfBoundsException();
		return alias[pos];
	}

	public int size()
	{
		return this.layout.length;
	}
}
