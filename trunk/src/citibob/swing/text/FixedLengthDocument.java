

/**
 * Criado em 22/02/2004
 * Originally: org.jdbase.swing.util.FixedLengthDocument
 * (FixedLengthDocument, Criado em 22/02/2004 is part of JDBase,
 *
 */
package citibob.swing.text;

import javax.swing.text.*;

/**
 * @author fabio
 *
 *
 */
	public class FixedLengthDocument extends PlainDocument
	{
		private int iMaxLength;

		public FixedLengthDocument(int maxlen) {
			super();
			iMaxLength = maxlen;
		}

		public void insertString(int offset, String str, AttributeSet attr)
		throws BadLocationException {
			if (str == null) return;

			if (iMaxLength <= 0) // aceitara qualquer no. de caracteres
			{
				super.insertString(offset, str, attr);
				return;
			}

			int ilen = (getLength() + str.length());
			if (ilen <= iMaxLength) // se o comprimento final for menor...
				super.insertString(offset, str, attr); // ...aceita str
			else
			{
				if (getLength() == iMaxLength) return; // nada a fazer
				String newStr = str.substring(0, (iMaxLength - getLength()));

				super.insertString(offset, newStr, attr);
			}
		}
}

