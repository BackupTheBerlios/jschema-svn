/*
 * RptConv.java
 *
 * Created on October 26, 2007, 8:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.reports;

import citibob.reports.StringTableModel;
import citibob.swing.typed.*;
import citibob.swing.table.*;
import citibob.jschema.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import citibob.text.*;
import com.Ostermiller.util.*;
import citibob.reports.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import net.sf.jasperreports.engine.util.*;
import net.sf.jooreports.templates.*;
import citibob.sql.*;
import java.sql.*;

public abstract class Reports {

protected abstract InputStream openTemplateFile(String name)
throws IOException;

/** These must be set by subclass constructor */
protected String oofficeExe;
protected citibob.app.App app;


// ===================================================================
public JTypeTableModel toTableModel(java.sql.ResultSet rs)
throws java.sql.SQLException
{
	RSTableModel mod = new citibob.sql.RSTableModel(app.getSqlTypeSet());
	mod.setColHeaders(rs);
	mod.addAllRows(rs);
	return mod;
}
//public JTypeTableModel toTableModel(SqlRunner str, String sql)
//{
//	RSTableModel rsmod = new citibob.sql.RSTableModel(app.getSqlTypeSet());
//	rsmod.executeQuery(str, sql);
//	return rsmod;
//}
// ===================================================================
public StringTableModel format(JTypeTableModel mod)
{
	return new StringTableModel(mod, app.getSFormatterMap());
}
public StringTableModel format(JTypeTableModel mod, String[] scol, SFormatter[] sfmt)
{
	return new StringTableModel(mod, app.getSFormatterMap(), scol, sfmt);
}
// ===================================================================
public JRDataSource toJasper(java.sql.ResultSet rs)
{
	return new JRResultSetDataSource(rs);
}
public JRDataSource toJasper(JTypeTableModel model)
{
	return new JRTableModelDataSource(model);
}
public JRDataSource toJasper(java.util.Collection model)
{
	return new JRMapCollectionDataSource(model);
}
// ===================================================================
/** @param sgcols Columns to group by.
 @param sfmap Default conversions between types and strings.
 @param scols Columns for custom formatters (null if none)
 @param sfmt Custom formatters for those columns
 @returns A model suitable for JodReports.
 */
public List toJodList(JTypeTableModel model,
String[][] sgcols,
String[] scols, SFormatter[] sfmt)
{
	SFormatter[] formatters = app.getSFormatterMap().newSFormatters(model, scols, sfmt);
	StringTableModelGrouper grouper =
		new StringTableModelGrouper(model, sgcols, formatters);
	return grouper.groupRowsList();
}
public List toJodList(java.sql.ResultSet rs,
String[][] sgcols,
String[] scols, SFormatter[] sfmt)
throws SQLException
{
	return toJodList(toTableModel(rs), sgcols, scols, sfmt);
}
public List toJodList(java.sql.ResultSet rs,
String[][] sgcols)
throws SQLException
{
	return toJodList(rs, sgcols, null, null);
}
public Map toJodMap(JTypeTableModel model,
String[][] sgcols,
String[] scols, SFormatter[] sfmt)
{
	List list = toJodList(model, sgcols, scols, sfmt);
	Map map = new TreeMap();
	map.put("g0", list);
	return map;
}

/** Converts a set of named Jod Lists to a map. */
public static Map toJodMap(String[] names, List[] lists)
{
	Map map = new TreeMap();
	for (int i=0; i<names.length; ++i) {
		map.put(names[i], lists[i]);
	}
	return map;
}
// ===================================================================
/** Writes a bunch of JodReports, using template once per item in the list.
 @param fout File for output; null if we should create a temporary file.
 @returns Null if no pages generated, otherwise file they're written in. */
public File writeJodPdfs(List models, String templateName, File fout)
throws IOException, InterruptedException,
net.sf.jooreports.templates.DocumentTemplateException,
com.lowagie.text.DocumentException
{
	// Create a temporary file if needed
	String outExt = "pdf";
	int dot = templateName.lastIndexOf('.');
	String outBase = templateName.substring(0, dot);
	String inExt = templateName.substring(dot+1);
	if (fout == null) {
		fout = File.createTempFile(outBase, "." + outExt);
		fout.deleteOnExit();
	}
	
	JodPdfWriter jout = new JodPdfWriter(oofficeExe,
		new FileOutputStream(fout), outExt);
	try {
		for (Iterator ii=models.iterator(); ii.hasNext();) {
			Map map = (Map)ii.next();
			InputStream in = openTemplateFile(templateName);
			System.out.println("Formatting report " + jout.getNumReports());
			jout.writeReport(in, inExt, map);
			in.close();
		}
	} finally {
		jout.close();
	}
	return (jout.getNumReports() > 0 ? fout : null);
}
/** Writes just one JodReport using one template once. */
public File writeJodPdf(Map map, String templateName, File fout)
throws IOException, InterruptedException,
net.sf.jooreports.templates.DocumentTemplateException,
com.lowagie.text.DocumentException
{
	List list = new LinkedList();
	list.add(map);
	return writeJodPdfs(list, templateName, fout);
}
// ===================================================================
public void viewJodPdfs(List models, String templateName)
throws IOException, InterruptedException,
net.sf.jooreports.templates.DocumentTemplateException,
com.lowagie.text.DocumentException
{
	viewPdf(writeJodPdfs(models, templateName, null));
}
public void viewJodPdf(Map map, String templateName)
throws IOException, InterruptedException,
net.sf.jooreports.templates.DocumentTemplateException,
com.lowagie.text.DocumentException
{
	viewPdf(writeJodPdf(map, templateName, null));
}
// ===================================================================
public File viewPdf(File file)
{
	if (file == null) {
		javax.swing.JOptionPane.showMessageDialog(null,
			"The report has no pages.");
		return null;
	} else {
		citibob.gui.BareBonesPdf.view(file);
		return file;
	}
}
// ===================================================================
/** @param params extra variables sent to Jasper Report. */
public void viewJasper(JRDataSource jrdata, Map params, String templateName)
throws JRException, IOException
{	
	InputStream reportIn = openTemplateFile(templateName);
	try {
		JasperReport jasperReport = (templateName.endsWith(".jrxml") ?
			JasperCompileManager.compileReport(reportIn) :
			(JasperReport)JRLoader.loadObject(reportIn));
		params = (params == null ? new HashMap() : params);
		JasperPrint jprint = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, params, jrdata);
		JasperPrintersTest.checkAvailablePrinters();		// Java/CUPS/JasperReports bug workaround for Mac OS X
		net.sf.jasperreports.view.JasperViewer.viewReport(jprint, false);
	} finally {
		reportIn.close();
	}
}
public void viewJasper(JRDataSource jrdata, String templateName)
throws JRException, IOException
{
	viewJasper(jrdata, new HashMap(), templateName);
}
// ===================================================================
/** @param reportName Name of report to be used in preferences node pathname.
 @param ext Filename extension (WITH the dot) to use on report.
 @param title Title to display in chooser dialog.
 */
public File chooseReportOutput(java.awt.Component parent,
String reportName, String ext, String title)
//throws Exception
{
	java.util.prefs.Preferences pref = app.userRoot().node("reports");
	final String dotExt = ext;
	final String starExt = "*" + ext;
	String dir = pref.get(reportName, null);
	JFileChooser chooser = new JFileChooser(dir);
	chooser.setDialogTitle("Save " + title);
	chooser.addChoosableFileFilter(
		new javax.swing.filechooser.FileFilter() {
		public boolean accept(File file) {
			String filename = file.getName();
			return filename.endsWith(dotExt);
		}
		public String getDescription() {
			return starExt;
		}
	});
	String path = null;
	File file;
	for (;;) {
		chooser.showSaveDialog(parent);

		path = chooser.getCurrentDirectory().getAbsolutePath();
		if (chooser.getSelectedFile() == null) return null;
		String fname = chooser.getSelectedFile().getPath();
		if (!fname.endsWith(dotExt)) fname = fname + dotExt;
		file = new File(fname);
		if (!file.exists()) break;
		if (JOptionPane.showConfirmDialog(
			parent, "The file " + file.getName() + " already exists.\nWould you like to ovewrite it?",
			"Overwrite File?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) break;
	}
	pref.put("saveReportDir", path);
	return file;
}

public void writeCSV(StringTableModel model,
java.awt.Component parent,
String title)
//String reportName)//, String ext, String title)
throws IOException, java.text.ParseException
{
	File f = chooseReportOutput(parent, "csvreport", ".csv", title);
	if (f == null) return;
	writeCSV(model, f);
}
	/** Creates a new instance of CSVReportOutput */
public void writeCSV(StringTableModel model, File f) throws IOException, java.text.ParseException
{
	FileWriter out = null;
	try {
		writeCSV(model, new FileWriter(f));
	} finally {
		try { out.close(); } catch(Exception e) {}
	}
}

/** Creates a new instance of CSVReportOutput */
public void writeCSV(StringTableModel model, Writer out) throws IOException, java.text.ParseException
{
	int ncol = model.getColumnCount();

	CSVPrinter pout = new com.Ostermiller.util.CSVPrinter(out);
	for (int i=0; i<ncol; ++i) {
		pout.print(model.getColumnName(i));
	}
	pout.println();
	
	// Do each row
	for (int j=0; j<model.getRowCount(); ++j) {
		for (int i=0; i<ncol; ++i) {
			String s = (String)model.getValueAt(j,i);
			pout.print(s);
		}
		pout.println();
	}
	pout.flush();
}






}
