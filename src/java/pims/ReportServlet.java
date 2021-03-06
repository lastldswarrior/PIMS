/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pims;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Jared
 */
@WebServlet(name="ReportServlet", urlPatterns="/ReportServlet")
public class ReportServlet extends HttpServlet implements javax.servlet.Servlet {

    private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TABLES
        String tableName = request.getParameter("table");
        List<String> tables = new ArrayList();
        tables.add("BILLING");
        tables.add("CONTACT");
        tables.add("INSURANCE");
        tables.add("LOCATION");
        tables.add("PRESCRIPTIONS");
        tables.add("TREATMENT");        
        
        request.setAttribute("tableList", tables);
        request.setAttribute("selectedTable",tableName);
        
        //COLUMNS
        if (tableName != null) {
            String colName = request.getParameter("columns");

            List<String> cols = getColumns(tableName);
            cols.remove("ID");

            request.setAttribute("colList", cols);
            request.setAttribute("selectedCol", colName);

            //CONDITIONS
            String conditionName = request.getParameter("condition");
            List<String> allConditions = new ArrayList();
            allConditions.add("=");
            allConditions.add("LIKE");
            allConditions.add("GREATER THAN");
            allConditions.add("LESS THAN");
            allConditions.add("STARTS WITH");
            allConditions.add("CONTAINS");

            request.setAttribute("conditionList", allConditions);
            request.setAttribute("selectedCondition", conditionName);

            //INPUT
            String input = request.getParameter("input");
            request.setAttribute("inputValue", input);

            //POPULATE TABLE
            if (!input.isEmpty()) {
                List<Rows> rows = buildQuery(tableName, colName, conditionName, input);
                request.setAttribute("rows", rows);
                
                boolean download = Boolean.parseBoolean(request.getParameter("download"));
                if(download){
                    String query = "All Patients where "+tableName+"."+colName+" "+conditionName+" "+input;
                    export(request, response, rows, tableName,query);
                }
                
            }
        }
        
        request.getRequestDispatcher("report.jsp").forward(request, response);
        
    }
    
    private void export(HttpServletRequest request, HttpServletResponse response, List<Rows> rows, String input, String query) {        
        
        try {
            response.setContentType("application/pdf");
            response.setHeader("content-disposition", "attachment; filename=" + "downloadedReport.pdf");
            
            Document doc = new Document();
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();
            
            //Write to the pdf            
            addTitlePage(doc, query);            
            doc.add(createDataTable(rows, input));
            doc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Rows> buildQuery(String table, String col, String cond, String input){
        List<Rows> results = new ArrayList();        
        String compare = getInput(cond, input);        
        String condition = getCondition(cond);
        
        try {
            DBConnect db = new DBConnect();
            db.connect();
            Connection conn = db.getConn();
            String column = "t."+col;
//            + "UPPER(PATIENT.LAST_NAME) like UPPER('"+upper_last+"')";
            String statement
                    = "SELECT "
                        + "p.first_name, "
                        + "p.last_name, "
                        + "t."+col+" "
                    + "FROM "
                        + "PATIENTDB.PATIENT p, "
                        + "PATIENTDB."+table+" t "
                    + "WHERE "
                        + "UPPER("+column+") "+condition+" UPPER('"+compare+"') "
//                        + column+" "+condition+" LOWER('"+compare+"') "
                        + "and p.id = t.id";            
            System.out.println(statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                Rows row = new Rows();
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");
                String temp = rs.getString(col);
                row.add(first + " " + last);
                row.add(temp);
                results.add(row);
            }
            
            return results;
            
        } catch (Exception ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private String getInput(String condition, String input){
        String result = null;
        switch(condition){
            case "STARTS WITH":
                result = input + "%";
                return result;
            case "LIKE":
                result = input.replaceAll("[//*]", "%");
                return result;
            case "=":
                return input;
            case "CONTAINS":
                result = "%"+input+"%";
                return result;                
            default:
                return null;
        }
    }
    
    private String getCondition(String condition){
        
        switch(condition){
            case "STARTS WITH":                
                return "like";
            case "LIKE":                
                return "like";
            case "=":
                return "=";
            case "CONTAINS":
                return "like";  
            case "GREATER THAN":
                return ">";
            default:
                return null;
        }
    }
    
    private List<String> getColumns(String table){
        List<String> cols = new ArrayList();
        try {
            DBConnect db = new DBConnect();
            db.connect();
            Connection conn = db.getConn();
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + table;

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                cols.add(name);
            }
            return cols;
            
        } catch (Exception ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private void addTitlePage(Document document, String query) throws DocumentException {

        try {
            Paragraph preface = new Paragraph();
            creteEmptyLine(preface, 1);            
            Image img = Image.getInstance("C:/Users/Jared/Documents/NetBeansProjects/PIMS/web/arkham2.png");
            preface.add(img);
            
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            preface.add(new Paragraph("Report created on "
                    + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
            
            preface.add(new Paragraph("Script Executed: "+query, TIME_ROMAN_SMALL));
            creteEmptyLine(preface, 1);
            document.add(preface);
        } catch (BadElementException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
     private void addImage(Document document){
        try { 
            Image img = Image.getInstance("C:/Users/Jared/Documents/NetBeansProjects/PIMS/web/arkham.png");
            document.add(img);
            
        } catch (BadElementException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
    public PdfPTable createDataTable(List<Rows> rows, String input) {
    	// a table with 2 columns
        PdfPTable table = new PdfPTable(2);
        // the cell object
        PdfPCell cell;
        // we add a cell
        cell = new PdfPCell(new Phrase("Patients"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(input));
        table.addCell(cell);
//        
//        // now we add a cell with rowspan 2
//        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
//        cell.setRowspan(2);
//        table.addCell(cell);
        
        // we add the four remaining cells with addCell()
        for(Rows r:rows){
            table.addCell(r.getIndex(0));
            table.addCell(r.getIndex(1));
        }
        
        
        return table;
    } 

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
