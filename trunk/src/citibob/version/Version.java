/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * VersionNo.java
 *
 * Created on April 16, 2006, 3:17 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.version;

import java.util.prefs.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author citibob
 */
public class Version implements Comparable
{


 int major;
 int minor;
 int rev;

public int getMajor() { return major; }
public int getMinor() { return minor; }
public int getRev() { return rev; }

public String toString() { return ""+major+"."+minor+"."+rev; }

/** Gets version number out of a database table. */
public Version(java.sql.Statement st, String sqlTable)
throws SQLException
{
	ResultSet rs = st.executeQuery("select major,minor,rev from " + sqlTable);
	rs.next();
	major = rs.getInt("major");
	minor = rs.getInt("minor");
	rev = rs.getInt("rev");
	rs.close();
}
/** Creates a new instance of VersionNo */
public Version(String s) throws java.text.ParseException
{
	String[] parts = s.split("[.]");
	if (parts.length != 3) throw new java.text.ParseException("Bad version number: " + s, 0);
	try {
		major = Integer.parseInt(parts[0]);
		minor = Integer.parseInt(parts[1]);
		rev = Integer.parseInt(parts[2]);
	} catch(NumberFormatException e) {
		throw new java.text.ParseException("Bad version number: " + s, 0);
	}
}
public Version(int major, int minor, int rev)
{
	this.major = major;
	this.minor = minor;
	this.rev = rev;
}
// ----------------------------------------------------------
public int compareTo(Object o)
{
	Version v = (Version)o;
	if (major == v.major) {
		if (minor == v.minor) {
			return rev - v.rev;
		}
		return minor - v.minor;
	}
	return major - v.major;
}
public boolean equals(Object o)
{
	return (compareTo(o) == 0);
}
// --------------------------------------------------------------
// --------------------------------------------------------------
public static Version[] getAvailablePrefVersions(Preferences prefs)
throws BackingStoreException
{
	String[] sver = prefs.childrenNames();
	ArrayList l = new ArrayList(sver.length);
	for (int i=0; i<sver.length; ++i) {
		try {
			l.add(new Version(sver[i]));
		} catch(Exception e) {}
	}

	int n = 0;
	Version[] ret = new Version[l.size()];
	for (Iterator ii=l.iterator(); ii.hasNext(); ) {
		Version v = (Version)ii.next();
		ret[n++] = v;
	}
	return ret;
}

// ========================================================
public static class Range
{
	Version min, max;
	public Range(Version min, Version max)
	{
		this.min = min;
		this.max = max;
	}
	public Range(Version v)
	{
		this.min = v;
		this.max = v;
	}
	public Range(String min, String max) throws java.text.ParseException
	{
		this(new Version(min), new Version(max));
	}
	public Range(String sv)
	throws java.text.ParseException
	{
		this(new Version(sv));
	}
	public Range(int major, int minor, int rev) { this(new Version(major,minor,rev)); }
	public boolean inRange(Version v)
	{
		if (min != null && v.compareTo(min) < 0) return false;
		if (max != null && v.compareTo(max) > 0) return false;
		return true;
	}
}
// ========================================================
public static class Entry
{
	public final Range range;
	public Object converter;
	public Entry(Range r, Object c) {
		range = r;
		converter = c;
	}
}
}
