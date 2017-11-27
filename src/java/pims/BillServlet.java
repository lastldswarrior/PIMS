/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pims;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jared
 */
@WebServlet(name="BillServlet", urlPatterns="/BillServlet")
public class BillServlet extends HttpServlet {

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
        
        
        export(request.getParameter("bill_patient"));
        performTask(request, response);
        //request.getRequestDispatcher("bill.jsp").forward(request, response);
    }
    
    
     private void export(String input) {        
        
        try {
                        
            String src = "C:/Users/Jared/Desktop/test/emptyBill.pdf";
            String dest2 = "C:/Users/Jared/Desktop/test/result.pdf";
            String proj_dest = "C:/Users/Jared/Documents/NetBeansProjects/PIMS/resources/bill.pdf";

            //remove last one
            File remove = new File(proj_dest);
            remove.delete();            
            
            //from folder
            manipulatePdf2(src, dest2, input);  
           
            //Copy result from desktop to application
            Path source = Paths.get(dest2);
            Path destination = Paths.get(proj_dest);

            Files.copy(source, destination);
            System.out.println("Files copied: ");
            

        } catch (DocumentException ex) {
            Logger.getLogger(BillServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BillServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
    private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {

        String pdfFileName = "bill.pdf";
        String contextPath = "C:/Users/Jared/Documents/NetBeansProjects/PIMS/resources/";
        File pdfFile = new File(contextPath + pdfFileName);

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
        response.setContentLength((int) pdfFile.length());

        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }
        fileInputStream.close();
        responseOutputStream.close();

    }

          
    public void manipulatePdf2(String src, String dest, String input) throws DocumentException, IOException {
        
        try {
            PdfReader reader = new PdfReader(src);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            AcroFields form = stamper.getAcroFields();
            
            Set<String> fldNames = form.getFields().keySet();
            
            for (String fldName : fldNames) {
                System.out.println(fldName + ": " + form.getField(fldName));
            }
            
            //Get database connection
            DBConnect db = new DBConnect();
            db.connect();
            
            //Get Patient Info
            Patient patient = new Patient(db.getConn());
            String[] names = input.split(" ");
            Patient p = patient.getPatient(names[0],names[1]);      
            
            //Data p            
            ResultSet rs = patient.getTreatment(p);
            String date_admitted = "";
            while(rs.next()){
                date_admitted = rs.getString("ADMITTANCE_DATE");                
            }
            System.out.println("Date: "+date_admitted);
            form.setField("pat_name", p.getFirstName()+ " "+p.getLastName());
            form.setField("date_admit", date_admitted);
            form.setField("service_dates", date_admitted);
            
            //get prescriptions
            String p_name = null;
            String p_schedule = null;
            String p_amount = null;
            ResultSet pres = patient.getPrescriptions(p);
            while(pres.next()){
                p_name = pres.getString("NAME");
                p_schedule = pres.getString("SCHEDULE");
                p_amount = pres.getString("AMOUNT");
            }
            form.setField("p_name", p_name);
            form.setField("p_schedule", p_schedule);
            form.setField("p_amount", p_amount);
                        
            //get debts
            List<String> total = new ArrayList();
            List<String> debts = patient.getDebts(p);
            System.out.println(debts.size());
            int k = 0;
            for(int i = 0; i < debts.size()/2; i++){                
                form.setField("charges_"+i, debts.get(k));
                form.setField("amt_"+i, makeMoney(debts.get(k+1)));
                total.add(debts.get(k+1));
                k=k+2;
            }
            
            //get insurance
            String carrier = null;
            String acctNum = null;
            String policy = null;
            String amount = null;
            
            rs = patient.getInsinfo(p);
            
            while(rs.next()){
                carrier = rs.getString("CARRIER"); 
                acctNum = rs.getString("POLICY_ACCOUNT_NUMBER"); 
                policy = rs.getString("POLICY_GROUP_NUMBER"); 
                amount = rs.getString("AMOUNT_PAID_BY_INSURANCE"); 
            }
            form.setField("ins_carrier", carrier);
            form.setField("ins_account", acctNum);
            form.setField("ins_group", policy);
            form.setField("ins_paid", makeMoney(amount));
            
            //total
            long totalAmount = 0;
            for(String count: total){
                totalAmount = totalAmount + Integer.parseInt(count);
            }
            long t = totalAmount - Long.parseLong(amount);
            form.setField("total", makeMoney2(t));
            
            stamper.close();
        } catch (Exception ex) {
            Logger.getLogger(BillServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    private String makeMoney(String value){
        long money = Long.parseLong(value);
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US); 
        String s = n.format(money / 10.0);
        System.out.println(s);
        return s;        
    }

    private String makeMoney2(long money){
        
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US); 
        String s = n.format(money / 10.0);
        System.out.println(s);
        return s;        
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
