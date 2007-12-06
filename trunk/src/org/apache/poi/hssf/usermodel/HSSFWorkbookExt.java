/*
 * HSSFWorkbookExt.java
 *
 * Created on December 5, 2007, 8:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author citibob
 * Hack to support citibob.reports.PoiXlsWriter
 */
public class HSSFWorkbookExt extends HSSFWorkbook
{

public HSSFWorkbookExt(POIFSFileSystem fs) throws java.io.IOException
{ super(fs); }
public Workbook getWorkbook() { return super.getWorkbook(); }
	
}
