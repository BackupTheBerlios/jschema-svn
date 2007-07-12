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
 * BaseSchemaSet.java
 *
 * Created on January 19, 2007, 9:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import java.util.*;

/**
 *
 * @author citibob
 */
public class BaseSchemaSet implements SchemaSet
{

protected TreeMap map = new TreeMap();

public Schema get(String name) {
	Schema sc = (Schema)map.get(name);
	if (sc == null) throw new NullPointerException("Schema " + name + " not in SchemaSet");
	return sc;
}

}
