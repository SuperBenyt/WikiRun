package org.sql.attributes;


import org.sql.SQLInfo;

public class SQLStringAttribute extends SQLAttribute<String> {
	private final int MAX_LENGTH;

	public SQLStringAttribute(SQLInfo info, String data) {
		super();
		this.MAX_LENGTH = info.getMaxStringLength();
		setData(data);
	}

	@Override
	public void setData(String data) {
		if (data == null) data = "";
		if (data.length() > this.MAX_LENGTH)
			throw new IllegalArgumentException(String.format("Maximum String size is cannot be over %d", this.MAX_LENGTH));
		super.setData(data);
	}
}
