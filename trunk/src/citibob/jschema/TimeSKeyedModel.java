/*
 * TimeSKeyedModel.java
 *
 * Created on June 8, 2007, 11:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

//import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.*;
import citibob.util.KeyedModel;

/**
 *
 * @author citibob
 */
public class TimeSKeyedModel extends KeyedModel
{

static NumberFormat n00 = new DecimalFormat("00");
		
/** Creates a new instance of TimeSKeyedModel */
public TimeSKeyedModel(int firstHr, int firstMin, int lastHr, int lastMin, int periodS)
{
	int firstS = firstHr * 3600 + firstMin * 60;
	int lastS = lastHr * 3600 + lastMin * 60;

	int dt = firstS;
	while (dt < lastS) {
		int hr = dt / 3600;
		int min = (dt - hr*3600) / 60;
		super.addItem(dt, n00.format(hr) + ":" + n00.format(min));
		dt += periodS;
	}	
}

}
