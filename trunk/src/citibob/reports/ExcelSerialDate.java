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
 * ExcelSerialDate.java
 *
 * Created on December 5, 2007, 10:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.reports;

import java.util.*;

public class ExcelSerialDate
{

//TimeZone tz;
Calendar cal;

/** @param tz The TimeZone we'll use when converting to and from Java dates */
public ExcelSerialDate(TimeZone tz)
{
	cal = Calendar.getInstance(tz);
}
	
public java.util.Date getDt(double serialDTime)
{
	int nSerialDate = (int)serialDTime;
	double time = serialDTime - nSerialDate;
	
	// Figure out the date
	int nDay, nMonth, nYear;
    if (nSerialDate == 60) {
	    // Excel/Lotus 123 have a bug with 29-02-1900. 1900 is not a
	    // leap year, but Excel/Lotus 123 think it is...
        nDay    = 29;
        nMonth    = 2;
        nYear    = 1900;
    } else {
		if (nSerialDate < 60) {
			// Because of the 29-02-1900 bug, any serial date 
			// under 60 is one off... Compensate.
			nSerialDate++;
	    }

		// Modified Julian to DMY calculation with an addition of 2415019
		int l = nSerialDate + 68569 + 2415019;
		int n = (int)(( 4 * l ) / 146097);
				l = l - (int)(( 146097 * n + 3 ) / 4);
		int i = (int)(( 4000 * ( l + 1 ) ) / 1461001);
			l = l - (int)(( 1461 * i ) / 4) + 31;
		int j = (int)(( 80 * l ) / 2447);
		 nDay = l - (int)(( 2447 * j ) / 80);
			l = (int)(j / 11);
			nMonth = j + 2 - ( 12 * l );
		nYear = 100 * ( n - 49 ) + i + l;
	}
	
	// Figure out the time
	time *= 24;
	int hour = (int)time;
	time -= hour;
	time *= 60;
	int minute = (int)time;
	time -= minute;
	time *= 60;
	int second = (int)time;
	time -= second;
	time *= 1000;
	int ms = (int)time;
	
	// Convert to Java...
	cal.set(Calendar.YEAR, nYear);
	cal.set(Calendar.MONTH, nMonth-1);
	cal.set(Calendar.DAY_OF_MONTH, nDay);
	cal.set(Calendar.HOUR_OF_DAY, hour);
	cal.set(Calendar.MINUTE, minute);
	cal.set(Calendar.SECOND, second);
	cal.set(Calendar.MILLISECOND, ms);
	return cal.getTime();
}

static int DMYToExcelSerialDate(int nDay, int nMonth, int nYear)
{
    // Excel/Lotus 123 have a bug with 29-02-1900. 1900 is not a
    // leap year, but Excel/Lotus 123 think it is...
    if (nDay == 29 && nMonth == 02 && nYear==1900)
        return 60;

    // DMY to Modified Julian calculatie with an extra substraction of 2415019.
    long nSerialDate = 
            (int)(( 1461 * ( nYear + 4800 + (int)(( nMonth - 14 ) / 12) ) ) / 4) +
            (int)(( 367 * ( nMonth - 2 - 12 * ( ( nMonth - 14 ) / 12 ) ) ) / 12) -
            (int)(( 3 * ( (int)(( nYear + 4900 + (int)(( nMonth - 14 ) / 12) ) / 100) ) ) / 4) +
            nDay - 2415019 - 32075;

    if (nSerialDate < 60) {
        // Because of the 29-02-1900 bug, any serial date 
        // under 60 is one off... Compensate.
        nSerialDate--;
    }

    return (int)nSerialDate;
}

public double getSerial(java.util.Date dt)
{
	cal.setTime(dt);
	int serialDate = DMYToExcelSerialDate(
		cal.get(Calendar.DAY_OF_MONTH),
		cal.get(Calendar.MONTH) + 1,
		cal.get(Calendar.YEAR));
	int timeMS =
		cal.get(Calendar.MILLISECOND) +
		cal.get(Calendar.SECOND) * 1000 +
		cal.get(Calendar.MINUTE) * 60*1000 +
		cal.get(Calendar.HOUR_OF_DAY) * 3600*1000;
	return (double)serialDate + (double)timeMS / (86400.0D * 1000.0D);
}

}
