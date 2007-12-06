/*
 * PoiTest.java
 *
 * Created on December 5, 2007, 7:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.reports;

import java.io.*;
import java.io.FileOutputStream;
import java.util.*;
import javax.swing.table.TableModel;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.record.formula.*;
import org.apache.poi.hssf.model.*;

/**
 *
 * @author citibob
 */
public class PoiXlsWriter
{

HSSFWorkbookExt wb;
ExcelSerialDate xlserial;

/** @param displayTZ TimeZone to use when converting dates to Excel serial dates. */
public PoiXlsWriter(InputStream templateIn, TimeZone displayTZ) throws IOException
{
	POIFSFileSystem fs = new POIFSFileSystem(templateIn);
	wb = new HSSFWorkbookExt(fs);
	xlserial = new ExcelSerialDate(displayTZ);
	templateIn.close();
}
	
// ----------------------------------------------------------
// Utility functions to insert and delete rows

public void insertRowsFixup(HSSFSheet sheet, int rowIx, int n)
{
	sheet.shiftRows(rowIx,sheet.getLastRowNum(),n,true,true);
	fixupFormulas(sheet, rowIx, n);
}
public void deleteRowsFixup(HSSFSheet sheet, int rowIx, int n)
{
	sheet.shiftRows(rowIx+n,sheet.getLastRowNum(),-n,true,true);
	fixupFormulas(sheet, rowIx, -n);
}

public void fixupFormulas(HSSFSheet sheet, int rowIx, int n)
{
//System.out.println("--------- fixupFormulas(" + rowIx + "," + n + ")");
	int prows = sheet.getPhysicalNumberOfRows();
	int pr=0;
	for (int r = 0; pr<prows; r++) {
		HSSFRow row = sheet.getRow(r);
		if (row == null) continue;
		++pr;
		
		int pcells = row.getPhysicalNumberOfCells();
		int pc = 0;
		for (int c=0; pc<pcells; ++c) {
			HSSFCell cell  = row.getCell((short)c);
			if (cell == null) continue;
			++pc;

			// Fixup the formula
			if (cell.getCellType() != HSSFCell.CELL_TYPE_FORMULA) continue;
//System.out.println("Formula cell: " + cell.getCellFormula());
//System.out.println("    ncells = " + row.getLastCellNum());
			FormulaParser fp = new FormulaParser(cell.getCellFormula(), wb.getWorkbook());
			fp.parse();
			Ptg[] ptg = fp.getRPNPtg();
			for (int i=0; i<ptg.length; ++i) {
				Ptg pi = ptg[i];
//					if (pi.getPtgClass() != Ptg.CLASS_REF) continue;
				if (pi instanceof AreaPtg) {
//System.out.println("Fixing area: " + pi);
					AreaPtg pp = (AreaPtg)pi;
					if (pp.getFirstRow() >= rowIx) pp.setFirstRow((short)(pp.getFirstRow() + n));
					if (pp.getLastRow() >= rowIx) {
						pp.setLastRow((short)(pp.getLastRow() + n));
					}
				} else if (pi instanceof ReferencePtg) {
					ReferencePtg pp = (ReferencePtg)pi;
					if (pp.getRow() >= rowIx) pp.setRow((short)(pp.getRow() + n));
				}
			}
			
			// Done fixing the formula; set it back
			String fstr = fp.toFormulaString(wb.getWorkbook(), ptg);
//System.out.println("replacing formula string (" + r + "," + c + "): " + fstr);
			cell.setCellFormula(fstr);
		}
	}
}
// ----------------------------------------------------------------


/** Creates a new instance of PoiTest */
public void replaceHolders(java.util.Map<String,TableModel> models)
//throws Exception
{
	for (int k = 0; k < wb.getNumberOfSheets(); k++) {
		HSSFSheet sheet = wb.getSheetAt(k);

		// Iterate through all rows and cols of the sheet
		int prows = sheet.getPhysicalNumberOfRows();
		int pr=0;
		for (int r = 0; pr<prows; r++) {
System.out.println(r + ", " + pr + ", " + prows);
			HSSFRow row = sheet.getRow(r);
			if (row == null) continue;
			++pr;

			int pcells = row.getPhysicalNumberOfCells();
			int pc = 0;
			for (int c=0; pc<pcells; ++c) {
				HSSFCell cell  = row.getCell((short)c);
				if (cell == null) continue;
				++pc;

			
				// Look for cells like ${var}
				if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING) continue;

				String value = cell.getRichStringCellValue().getString().trim();
				if (!value.startsWith("${")) continue;
				String rsname = value.substring(2,value.length()-1);
			
				// Do the replacement
				TableModel mod = (models.size() == 1
					? models.values().iterator().next()
					: models.get(rsname));
				r += replaceOneHolder(sheet, r, c, mod);
				break;		// We just deleted the whole line!
			}
		}
	}
}

/** @returns net number of rows inserted */
int replaceOneHolder(HSSFSheet sheet, int row, int col, TableModel mod)
{
	int n = mod.getRowCount();
	
	// Set up proper number of rows
	insertRowsFixup(sheet, row,n);
	deleteRowsFixup(sheet, row+n,1);
	
	// Fill in the data, iterating through the model...
	for (int r=0; r<mod.getRowCount(); ++r) {
System.out.println("r=" + r);
		HSSFRow r2 = sheet.createRow(row + r);

		for (int c=0; c<mod.getColumnCount(); ++c) {
System.out.println("  c=" + c);
			HSSFCell c2 = r2.createCell((short)(col + c));
			Object val = mod.getValueAt(r,c);
			
			// Poor man's convert to Excel data types
			if (val instanceof java.util.Date) {
				c2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				c2.setCellValue(xlserial.getSerial((java.util.Date)val));
			} else if (val instanceof Number) {
				c2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				c2.setCellValue(((Number)val).doubleValue());
			} else if (val instanceof Boolean) {
				c2.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
				c2.setCellValue(((Boolean)val).booleanValue());
			} else {
				// Assume a String
				if (val == null) {
					c2.setCellType(HSSFCell.CELL_TYPE_BLANK);
				} else {
					String sval = val.toString();
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellValue(new HSSFRichTextString(sval));
				}
			}
		}
	}
	return n-1;
}


public void writeSheet(File f) throws IOException
{
	wb.write(new FileOutputStream(f));	
}


//public static void main(String[] args) throws Exception
//{
//	PoiXlsWriter pt = new PoiXlsWriter();
//	pt.showFormulas();
//	pt.insertRows();
//	System.out.println("======================");
//	pt.showFormulas();
//	pt.writeSheet(new File("x2.xls"));
//}
//
///** Creates a new instance of PoiTest */
//public void showFormulas() throws Exception
//{
//	for (int k = 0; k < wb.getNumberOfSheets(); k++) {
//		System.out.println("Sheet " + k);
//		
//		HSSFSheet sheet = wb.getSheetAt(k);
//		HSSFFormulaEvaluator feval = new HSSFFormulaEvaluator(sheet, wb);
//		int rows  = sheet.getPhysicalNumberOfRows();
//
//		// Search for the row we'll replace
//		int pr=0;		// physical rows
//		rows = sheet.getLastRowNum()+1;
//		for (int r = 0; r < rows; r++) {
//			HSSFRow row   = sheet.getRow(r);
//			if (row == null) continue;
//			++pr;
//			int cells = row.getLastCellNum();
//			if (cells == 0) continue;
//
//			for (int c=0; c<cells; ++c) {
//				HSSFCell cell  = row.getCell((short)c);
//				if (cell == null) continue;
//				if (cell.getCellType() != HSSFCell.CELL_TYPE_FORMULA) break;
//
//				System.out.println("Formula: " + cell.getCellFormula());
//				FormulaParser fp = new FormulaParser(cell.getCellFormula(), wb.getWorkbook());
//				fp.parse();
//				Ptg[] ptg = fp.getRPNPtg();
//				for (int i=0; i<ptg.length; ++i) {
//					System.out.println("     |" + ptg[i]);
//				}
//			}
//		}
//
//
//	}
//}

}
