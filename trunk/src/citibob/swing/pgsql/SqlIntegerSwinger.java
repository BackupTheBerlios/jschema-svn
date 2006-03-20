/*
 * TypedWidgetSTFactory.java
 *
 * Created on March 18, 2006, 6:14 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.pgsql;

import citibob.sql.*;
import javax.swing.text.*;
import java.text.*;
import citibob.swing.typed.*;
import citibob.sql.pgsql.*;

/**
 *
 * @author citibob
 */
public class SqlIntegerSwinger extends TypedWidgetSwinger
{

/** Creates a new instance of TypedWidgetSTFactory */
public SqlIntegerSwinger(SqlInteger sqlType) {
	super(sqlType);
}

public boolean renderWithWidget() { return false; }

/** Create a widget suitable for editing this type of data. */
protected citibob.swing.typed.TypedWidget createTypedWidget()
	{ return new JTypedTextField(); }






	/** Creates an AbstractFormatterFactory for a JFormattedTextField.  If this
	 SqlType is never to be edited with a JFormattedTextField, it can just
	 return null.  NOTE: This should return a new instance of AbstractFormatterFactory
	 because one instance is required per JFormattedTextField.  It's OK for the
	 factory to just store instances of 4 AbstractFormatters and return them as needed. */
	public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
	{
		SqlInteger tt = (SqlInteger)sqlType;
		
		NumberFormat nf = NumberFormat.getIntegerInstance();
		return newFormatterFactory(nf);
	}

	// --------------------------------------------------------------------
	// Not part of interface
	public javax.swing.text.DefaultFormatterFactory newFormatterFactory(NumberFormat nf)
	{
		NumberFormatter nff = new NumberFormatter(nf);
		return new DefaultFormatterFactory(nff);
	}

//	private NumberFormatter formatter(NumberFormat nf)
//	{
//		return (nf == null ? null : new NumberFormatter(nf));
//	}
//	
//	public javax.swing.text.DefaultFormatterFactory newFormatterFactory(
//	NumberFormat defaultNF, NumberFormat displayNF, NumberFormat editNF, NumberFormat nullNF)
//	{
//		return new DefaultFormatterFactory(
//				formatter(defaultNF),
//				formatter(displayNF),
//				formatter(editNF),
//				formatter(nullNF));
//	}





}
