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
/*
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.swing.typed;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import citibob.exception.*;
import citibob.textconverter.TextConverter;

/**
 *
 * @author  citibob
 */
public class JTypedTextField
extends JNullTextField
implements ThrowsException, TypedWidget, KeyListener {

ExceptionHandler handler;
TextConverter converter;
Object val;

// --------------------------------------------------------------
/** For building with NetBeans */
public JTypedTextField()
{
	super();
//	addKeyListener(this);
}


/** For constructing manually */
public JTypedTextField(TextConverter converter)
{
	this();
	setTextConverter(converter);
}

// --------------------------------------------------------------

/** For setting after NetBeans creates it, or in the properties dialog. */
public void setTextConverter(TextConverter converter)
{
	this.converter = converter;
	setInputVerifier(new MyInputVerifier());
}

public TextConverter getTextConverter()
	{ return converter; }

// --------------------------------------------------------------
/** Set by an automatic scanner that finds all objects implementing ThrowsException. */
public void setExceptionHandler(ExceptionHandler handler)
	{ this.handler = handler; }

public void setValue(Object o)
{
//System.out.println("this = " + this.getClass());
//System.out.println("o = " + o);
//System.out.println("converter = " + converter);
//System.out.println("Assigning " + o.getClass() + " to " + converter.getObjClass());
	if (o != null && !converter.getObjClass().isAssignableFrom(o.getClass())) {
		throw new java.lang.ClassCastException("Cannot assign object of class " + getClass() + " to JTypedTextField of class " + converter.getObjClass());
	}
	val = o;
	setText(converter.toString(val));
}

public Object getValue()
	{ return val; }
public Class getObjClass()
	{ return converter.getObjClass(); }
public void resetValue()
{
	setLatestValue();
}
public void setLatestValue()
{
	if (!isValueValid()) {
		setValue(val);	// Sets text in accordance with last good value
	}
}
public boolean isValueValid()
{
	return getInputVerifier().verify(this);
}
// ===================== KeyListener =====================
public void keyTyped(KeyEvent e) {
	if (e.getKeyChar() == '\033') resetValue();
}
public void keyReleased(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) resetValue();
}
public void keyPressed(KeyEvent e) {}
// ====================== Input Verifier ===========
protected static class MyInputVerifier extends javax.swing.InputVerifier {
public boolean verify(javax.swing.JComponent jComponent) {
	JTypedTextField tf = (JTypedTextField) jComponent;
	try {
		String s = tf.getText();
		Object o = tf.converter.parseObject(s);
		tf.setValue(o);
//		tf.val = o;
//		tf.setText(tf.converter.toString(tf.val));

		// Unset any format error conditions
		if (tf.handler != null) tf.handler.throwException(null);
		return true;
	} catch(Exception e) {
		if (tf.handler != null) tf.handler.throwException(e);
		else System.err.println("Format Exception: " + e);
		return false;
	}
}}

}
