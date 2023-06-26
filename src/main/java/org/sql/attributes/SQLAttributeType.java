package org.sql.attributes;


import javafx.util.Pair;
import org.sql.attributes.data.SQLDateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum SQLAttributeType {
	INT(e -> Integer.toString((Integer)e), (resultSet, columnIndex) -> {
		try {
			return resultSet.getInt(columnIndex);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}, (preparedStatement, integerObjectPair) -> {
		try {
			preparedStatement.setInt(integerObjectPair.getKey(), (Integer) integerObjectPair.getValue());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}),

	LONG(e -> Long.toString((Long)e), (resultSet, columnIndex) -> {
		try {
			return resultSet.getLong(columnIndex);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}, (preparedStatement, integerObjectPair) -> {
		try {
			preparedStatement.setLong(integerObjectPair.getKey(), (Long) integerObjectPair.getValue());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}),

	STRING(e -> (String)e, (resultSet, columnIndex) -> {
		try {
			return resultSet.getString(columnIndex);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}, (preparedStatement, integerObjectPair) -> {
		try {
			preparedStatement.setString(integerObjectPair.getKey(), (String) integerObjectPair.getValue());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}),

	DATETIME(e -> ((SQLDateTime)e).getDateTime(), (resultSet, columnIndex) -> {
		try {
			return resultSet.getString(columnIndex);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}, (preparedStatement, integerObjectPair) -> {
		try {
			preparedStatement.setString(integerObjectPair.getKey(), (String) integerObjectPair.getValue());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	});

	private Function<Object, String> printer;
	BiFunction<ResultSet, Integer, Object> reader;
	BiConsumer<PreparedStatement, Pair<Integer, Object>> preparer;

	SQLAttributeType(
		Function<Object, String> printer,
		BiFunction<ResultSet, Integer, Object> reader,
		BiConsumer<PreparedStatement, Pair<Integer, Object>> preparer)
	{
		this.printer = printer;
		this.reader = reader;
		this.preparer = preparer;
	}

	public String toString(Object o)
	{
		return printer.apply(o);
	}

	public Object getObjFromResultSet(ResultSet resultSet, int idx)
	{
		return reader.apply(resultSet, idx);
	}

	public void setPreparedStatement(PreparedStatement ps, int idx, Object value)
	{
		preparer.accept(ps, new Pair<>(idx, value));
	}
}
