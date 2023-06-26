package org.sql.attributes;

public class SQLAttribute <T> implements SQLAttributeBase {
	private T data;

	public SQLAttribute() {
	}

	public SQLAttribute(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
