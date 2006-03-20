/*
 * NewMain.java
 *
 * Created on March 19, 2006, 6:26 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.calendar;

import java.util.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class NewMain {
	
	/** Creates a new instance of NewMain */
	public NewMain() {
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		Calendar cal = Calendar.getInstance();
		cal.setTime(null);
		System.out.println(cal.getTime());
	}
	
}
