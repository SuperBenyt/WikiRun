package org.sql.attributes.data;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;

public class SQLDateTime {
	private java.sql.Date date;

	private java.sql.Time time;

	public SQLDateTime(Date date, Time time) {
		this.date = date;
		this.time = time;
	}

	public SQLDateTime(String datetime)
	{
		String[] strings = datetime.split(" ");
		this.date = Date.valueOf(strings[0]);
		this.time = Time.valueOf(strings[1]);
	}

	public SQLDateTime()
	{
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		String[] strings = currentTime.split(" ");
		this.date = Date.valueOf(strings[0]);
		this.time = Time.valueOf(strings[1]);
	}

	public long getDifference(SQLDateTime to)
	{
		long datediff = Math.abs(getDate().getTime() - to.getDate().getTime());
		long timediff = Math.abs(getTime().getTime() - to.getTime().getTime());
		return datediff+timediff;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getDateTime()
	{
		return String.format("%s %s", date.toString(), time.toString());
	}
}
