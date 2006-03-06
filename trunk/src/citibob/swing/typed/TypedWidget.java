/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006 by Robert Fischer

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package citibob.swing.typed;

/** A widget that edits a Java object.  Usually implemented in conjunction
with subclassing java.awt.Component */
public interface TypedWidget
{

/** Returns current value in the widget. */
Object getValue();

/** Sets the value.  Returns a ClassCastException */
void setValue(Object o);

/** Sets value back to some "original" value
(eg, last edited value, or original value from a DB record). */
void resetValue();

/** Sets the value to the last legal edited value.
Allows focus to leave the widget*/
void setLatestValue();

/** If false, then user is in middle of editing something
and doesn't have a valid value yet. */
boolean isValueValid();

/** Returns type of object this widget edits. */
Class getObjClass();

/** Returns the underlying ObjModel; used to combine this widget's data model
 * into another model.  So far, Objmodel is not able to take listeners,
 * but that could change... */
public ObjModel getObjModel();
public void setObjModel(ObjModel m);

}
