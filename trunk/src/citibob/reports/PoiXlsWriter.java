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

/** @param row0 example row to copy when inserting... 
 Don't do fixup between xrow0 and xrow1, non-inclusive. */
public void insertRowsFixup(HSSFSheet sheet, int rowIx, int n,
int row0Ix, int xcol0, int xcol1, int xrow0, int xrow1)
{
	sheet.shiftRows(rowIx,sheet.getLastRowNum(),n,true,true);
	HSSFRow row0 = sheet.getRow(row0Ix + n);
	if (row0 != null) {
		for (int r=rowIx; r < rowIx+n; ++r) {
			HSSFRow row1 = sheet.createRow(r);
			copyRow(row0, row1, xcol0, xcol1);
		}
	}
	fixupFormulas(sheet, rowIx, n, xrow0, xrow1);
}
/** Don't do fixup between xrow0 and xrow1, non-inclusive. */
public void deleteRowsFixup(HSSFSheet sheet, int rowIx, int n, int xrow0, int xrow1)
{
	sheet.shiftRows(rowIx+n,sheet.getLastRowNum(),-n,true,true);
	fixupFormulas(sheet, rowIx, -n, xrow0, xrow1);
}

/** Don't do fixup between xrow0 and xrow1, non-inclusive. */
public void fixupFormulas(HSSFSheet sheet, int rowIx, int n, int xrow0, int xrow1)
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
					if (r >= xrow0 && r < xrow1) {
						if (pp.getRow() <= r && pp.isRowRelative()) pp.setRow((short)(r + pp.getRow() - rowIx));
					} else if (pp.getRow() >= rowIx) {
						pp.setRow((short)(pp.getRow() + n));
					}
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
public void replaceHolders(java.util.Map<String,Object> models)
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
			
				int n = replaceOneHolder(sheet, r, c, models, rsname);
				if (n > 0) {
					r += n;
					break;		// We just deleted the whole line!
				}
			}
		}
	}
}

void copyCellFormatting(HSSFCell c0, HSSFCell c1)
{
	if (c0.getCellComment() != null) c1.setCellComment(c0.getCellComment());
//	c1.setCellNum(c0.getCellNum());
	if (c0.getCellStyle() != null) c1.setCellStyle(c0.getCellStyle());
}
void copyCell(HSSFCell c0, HSSFCell c1)
{
	copyCellFormatting(c0, c1);
	c1.setCellType(c0.getCellType());
	switch(c0.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING :
			c1.setCellValue(c0.getRichStringCellValue());
		break;
		case HSSFCell.CELL_TYPE_NUMERIC :
			c1.setCellValue(c0.getNumericCellValue());
		break;
		case HSSFCell.CELL_TYPE_FORMULA :
			c1.setCellFormula(c0.getCellFormula());
		break;
		case HSSFCell.CELL_TYPE_BOOLEAN :
			c1.setCellValue(c0.getBooleanCellValue());
		break;
		case HSSFCell.CELL_TYPE_ERROR :
			c1.setCellErrorValue(c0.getErrorCellValue());
		break;
	}
}

/** Only copies formatting from col0 to col1, non-inclusive. */
void copyRow(HSSFRow r0, HSSFRow r1, int col0, int col1)
{
	// Clear r1
	int pcells = r1.getPhysicalNumberOfCells();
	int pc = 0;
	for (int c=0; pc<pcells; ++c) {
		HSSFCell c1  = r1.getCell((short)c);
		if (c1 == null) continue;
		++pc;
		r1.removeCell(c1);
	}
	
	// Copy over cells from r0
	pcells = r0.getPhysicalNumberOfCells();
	pc = 0;
	for (int c=0; pc<pcells; ++c) {
		HSSFCell c0 = r0.getCell((short)c);
		if (c0 == null) continue;
		++pc;
		HSSFCell c1 = r1.createCell((short)c);
		if (c >= col0 && c < col1) copyCellFormatting(c0, c1);
		else copyCell(c0, c1);
	}
}

int replaceOneHolder(HSSFSheet sheet, int row, int col, Map<String,Object> models, String rsname)
{
	// Do the replacement
	Object mod = (models.size() == 1
		? models.values().iterator().next()
		: models.get(rsname));
	if (mod == null) return 0;
	if (mod instanceof TableModel) return replaceOneHolder(sheet, row, col, (TableModel)mod);
	
	// It's just a simple item; put it in
	HSSFRow row0 = sheet.getRow(row);
	HSSFCell c0 = row0.getCell((short)col);
	HSSFComment comment = c0.getCellComment();
	HSSFCellStyle style = c0.getCellStyle();
	row0.removeCell(c0);
	HSSFCell c1 = row0.createCell((short)col);
	if (comment != null) c1.setCellComment(comment);
	if (style != null) c1.setCellStyle(style);
	setValue(c1, mod);
	return 0;
}
/** @returns net number of rows inserted */
int replaceOneHolder(HSSFSheet sheet, int row, int col, TableModel mod)
{
	int n = mod.getRowCount();
	
	// Set up proper number of rows
	insertRowsFixup(sheet, row,n, row, col,
		col + mod.getColumnCount(), row, row + mod.getRowCount());
	HSSFRow r0 = sheet.getRow(row+n);		// Our model row
	
	// Fill in the data, iterating through the model...
	for (int r=0; r<mod.getRowCount(); ++r) {
System.out.println("r=" + r);
		HSSFRow r2 = sheet.getRow(row + r);
		if (r2 == null) r2 = sheet.createRow(row + r);

		for (int c=0; c<mod.getColumnCount(); ++c) {
System.out.println("  c=" + c);
			HSSFCell c2 = r2.getCell((short)(col + c));
			if (c2 == null) c2 = r2.createCell((short)(col + c));
			Object val = mod.getValueAt(r,c);
			setValue(c2, val);
		}
	}
	deleteRowsFixup(sheet, row+n,1,0,0);
	return n-1;
}

void setValue(HSSFCell c2, Object val)
{
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
