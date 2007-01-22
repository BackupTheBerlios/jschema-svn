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

public Schema get(String name) { return (Schema)map.get(name); }

}
