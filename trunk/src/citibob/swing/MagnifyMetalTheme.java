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
package citibob.swing;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/** MetalTheme that magnifies fonts (over standard sizes). */
public class MagnifyMetalTheme extends DefaultMetalTheme {

private double magnifyFactor = 1.0;
public String getName () { return "SmartMetalTheme"; }
	
private FontUIResource dialog10;
private FontUIResource dialog12;
private FontUIResource dialog12Bold;

private static double dotsPerPoint;

public MagnifyMetalTheme(double magnifyFactor)
{
	this.magnifyFactor = magnifyFactor;

	dotsPerPoint = Toolkit.getDefaultToolkit().getScreenResolution() / 72.0;
	dialog10
		= new FontUIResource ("dialog", Font.PLAIN, pointsToDPI (10));
	dialog12
		= new FontUIResource ("dialog", Font.PLAIN, pointsToDPI (11));
	dialog12Bold
			= new FontUIResource ("dialog", Font.BOLD, pointsToDPI (11));
}	   
	
private int pointsToDPI (int points) {
	return (int) Math.round (magnifyFactor * points * dotsPerPoint);
}


public FontUIResource getControlTextFont () { return dialog12; }
public FontUIResource getMenuTextFont () { return dialog12Bold; }
public FontUIResource getWindowTitleFont () { return dialog12Bold; }
public FontUIResource getSystemTextFont () { return dialog12; }
public FontUIResource getUserTextFont () { return dialog12; }
public FontUIResource getSubTextFont () { return dialog10; }

}
