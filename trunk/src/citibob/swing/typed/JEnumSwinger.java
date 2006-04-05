/*
 * TypedWidgetSTFactory.java
 *
 * Created on March 18, 2006, 6:14 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;
import javax.swing.text.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class JEnumSwinger extends TypedWidgetSwinger
{

/** Creates a new instance of TypedWidgetSTFactory */
public JEnumSwinger(JEnum sqlType) {
	super(sqlType);
}

public boolean renderWithWidget() { return false; }

/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget createTypedWidget()
{
	return new JKeyedComboBox();
}

/** Creates an AbstractFormatterFactory for a JFormattedTextField.  If this
 SqlType is never to be edited with a JFormattedTextField, it can just
 return null.  NOTE: This should return a new instance of AbstractFormatterFactory
 because one instance is required per JFormattedTextField.  It's OK for the
 factory to just store instances of 4 AbstractFormatters and return them as needed. */
public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
{
	JEnum tt = (JEnum)jType;
	KeyedFormatter fmt = new KeyedFormatter(tt.getKeyedModel());
	return new DefaultFormatterFactory(fmt);
}

}
