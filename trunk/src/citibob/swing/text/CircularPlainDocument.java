/**
 * This is derived from org.jdbase.swing.util.FixedLengthDocument
 * (FixedLengthDocument, Criado em 22/02/2004 is part of JDBase,
 * and licensed under the LGPL)
 * This file is modified by Robert Fischer, 2005
 */
package citibob.swing.text;

import javax.swing.text.*;

/**
 * @author fabio
 *
 *
 */
	public class CircularPlainDocument extends PlainDocument
	{
		private int iMaxLength;

		public CircularPlainDocument(int maxlen) {
			super();
			iMaxLength = maxlen;
		}

//		public void clear()
//		{
//			try {
//				super.getContent().remove(0, super.getContent().length());
//			} catch(BadLocationException e) {
//				// Shouldn't happen (in single-threaded system)
//				e.printStackTrace();
//			}
//		}
		
		public void insertString(int offset, String str, AttributeSet attr)
		throws BadLocationException {
			if (str == null) return;

			if (iMaxLength <= 0) // aceitara qualquer no. de caracteres
			{
				super.insertString(offset, str, attr);
				return;
			}

			int ilen = (getLength() + str.length());
			if (ilen <= iMaxLength) { // se o comprimento final for menor...
				super.insertString(offset, str, attr); // ...aceita str
			} else {
				super.remove(0, str.length());
				offset -= str.length();
				if (offset < 0) offset = 0;
				super.insertString(offset, str, attr);
//				if (getLength() == iMaxLength) return; // nada a fazer
//				String newStr = str.substring(0, (iMaxLength - getLength()));
//
//				super.insertString(offset, newStr, attr);
			}
		}
}

