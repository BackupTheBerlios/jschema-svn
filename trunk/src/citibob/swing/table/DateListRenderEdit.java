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
 * KeyedItemCellRenderer.java
 *
 * Created on March 20, 2005, 6:46 PM
 */

package citibob.swing.table;

import javax.swing.table.*;
import citibob.swing.typed.*;
import citibob.util.KeyedModel;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;

/** Provides renderer and editor appropriate for an enumerated type field
 * (according to a KeyedModel). */
public class DateListRenderEdit extends KeyedRenderEdit
{
	
public DateListRenderEdit(List dates, String sfmt)
{
	this(dates, new SimpleDateFormat(sfmt));
}

static KeyedModel getKmodel(List dates, DateFormat fmt)
{
	// Set up list of dropdown choices & labels for editor
	KeyedModel kmodel = new KeyedModel();
	for (Iterator ii=dates.iterator(); ii.hasNext(); ) {
		java.util.Date dt = (java.util.Date)ii.next();
		String lab = fmt.format(dt);
		kmodel.addItem(dt,lab);
//System.out.println("Adding: " + lab + " (key = " + dt);
	}	
	
	return kmodel;
}

public DateListRenderEdit(List dates, DateFormat fmt)
{
	super(getKmodel(dates, fmt));
}



}
