package org.sql;

import com.fasterxml.jackson.core.PrettyPrinter;
import org.logger.Logger;
import org.sql.attributes.SQLAttributeType;
import org.sql.query.SQLQuery;


import java.sql.*;
import java.util.ArrayList;
import java.util.function.Consumer;


public class SQLAccess {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public boolean initSQLBackend(SQLInfo data)
	{
		Logger.logInfo("Establishing connection to MySQL server...");
		try
		{
			 this.connection = DriverManager.getConnection(data.getUrl());
		} catch (SQLTimeoutException e)
		{
			Logger.logError("Connection timed out!");
			throw new RuntimeException(e); //return false;
		} catch (SQLException e)
		{
			Logger.logError("Connection attempt failed!");
			throw new RuntimeException(e); //return false;
		}

		Logger.logInfo("Connection attempt successful!");

		try {
			this.statement = this.connection.createStatement();
		} catch (SQLException e) {
			Logger.logError("Statement could not be created!");
			throw new RuntimeException(e); //return false;
		}

		return true;
	}

	public Connection getConnection() {
		return connection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public boolean insert(SQLInsertion insertion)
	{
		PreparedStatement ps = insertion.generateStatement(this);
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(String.format("Insertion statement failed! Template: %s", insertion.generateStatementTemplate()));
			throw new RuntimeException(e); // return false;
		}

		return true;
	}

	public boolean delete(SQLDeletion deletion)
	{
		PreparedStatement ps = deletion.generateStatement(this);
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(String.format("Query statement failed! Template: %s", deletion.generateStatementTemplate()));
			throw new RuntimeException(e); // return false;
		}

		return true;
	}

	public boolean query(SQLQuery query)
	{
		PreparedStatement ps = query.generateStatement(this);
		try {
			this.resultSet = ps.executeQuery();
		} catch (SQLException e) {
			Logger.logError(String.format("Query statement failed! Template: %s", query.generateStatementTemplate()));
			throw new RuntimeException(e); //return false;
		}

		return true;
	}

	public boolean update(SQLUpdate update)
	{
		PreparedStatement ps = update.generateStatement(this);
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(String.format("Update statement failed! Template: %s", update.generateStatementTemplate()));
			throw new RuntimeException(e); // return false;
		}

		return true;
	}

	public boolean acuteQuery(String prompt)
	{
		try {
			this.resultSet = statement.executeQuery(prompt);
		} catch (SQLException e) {
			Logger.logError(String.format("Query statement failed! Prompt: %s", prompt));
			throw new RuntimeException(e); // return false;
		}
		return true;
	}

	public void printResultSet(ResultSet resultSet, SQLTableLayout layout, Consumer<String> printer)
	{
		try {
			while (resultSet.next())
			{
				for (int i = 0; i < layout.size(); ++i)
				{
					SQLAttributeType type = layout.getTypeAt(i);
					printer.accept(String.format("%s: %s", layout.getAliasAt(i), type.toString(type.getObjFromResultSet(resultSet, i + 1))));
				}
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e); // return;
		}
	}

	public ArrayList<String> getResultSetAsStringArraylist(ResultSet resultSet, SQLTableLayout layout) {
		ArrayList<String> res = new ArrayList<>();
		try {
			while (resultSet.next())
			{
				for (int i = 0; i < layout.size(); i++)
				{
					SQLAttributeType type = layout.getTypeAt(i);
					res.add(type.toString(type.getObjFromResultSet(resultSet, i + 1)));
				}
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e);
		}
		return res;
	}

	public ArrayList<ArrayList<String>> getResultSetAsStringArraylistArraylist(ResultSet resultSet, SQLTableLayout layout) {
		ArrayList<ArrayList<String>>  res = new ArrayList<>();
		try {
			while (resultSet.next())
			{
				ArrayList<String> temp = new ArrayList<>();
				for (int i = 0; i < layout.size(); i++)
				{
					SQLAttributeType type = layout.getTypeAt(i);
					temp.add(type.toString(type.getObjFromResultSet(resultSet, i + 1)));
				}
				res.add(temp);
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e);
		}
		return res;
	}
}
