package pims;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jared
 */
@WebServlet(name="ControllerServlet", urlPatterns="/ControllerServlet")
public class ControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        
             
        String pageName = request.getParameter("page");
        System.out.println(pageName);
        
        //from any jsp page, handle routing
        switch (pageName) {
            case "index.jsp":
                String username = request.getParameter("username");
                String pass = request.getParameter("userpass");   
                
                DBConnect db = new DBConnect();
                db.connect();

                User user = new User(db.getConn());
                user.setUserName(username);
                user.setUserPassword(pass);
                String levelOfAccess = user.validate();
                user.countUsers();
                
                //from index go to which page
                switch (levelOfAccess) {
                    case "Admin":                        
                        request.setAttribute("user", user.getUserName());
                        request.setAttribute("userCount", user.getUserCount());
                        request.getRequestDispatcher("adminpanel.jsp").forward(request, response);
                        break;
                    case "Volunteer":
//                        List<User> patients = new ArrayList();
//                        db = new DBConnect();
//                        db.connect();

                        //Used to get all users, but don't need here
//                        User appUsers = new User(db.getConn());
//                        ResultSet allUsers = appUsers.getAllUsers();
//                        while (allUsers.next()) {
//                            User one = new User();
//                            one.setUserName(allUsers.getString("USER_NAME"));
//
//                            patients.add(one);
//                        }

//                        request.setAttribute("patients", patients);

                        //Patients
//                        List<Patient> patientList = new ArrayList();
//                        Patient patient = new Patient(db.getConn());
//                        ResultSet allPatients = patient.getAllPatients();
//
//                        while (allPatients.next()) {
//                            Patient p = new Patient();
//                            String first = allPatients.getString("FIRST_NAME");
//                            String last = allPatients.getString("LAST_NAME");
//                            p.setDisplayName(first + " " + last);                           
//                            patientList.add(p);
//                        }
//                        request.setAttribute("vol_info", patientList);
                        request.getRequestDispatcher("volunteerpanel.jsp").forward(request, response);
                        break;
                        
                    default:
                        request.setAttribute("pass", levelOfAccess);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                }
//                request.getRequestDispatcher("admin.jsp").forward(request, response);
                break;
            case "admin.jsp":
                String a_user = request.getParameter("username");
                String newPass = request.getParameter("a_pass");
                //New user
                String new_name = request.getParameter("newusername");
                String new_pass = request.getParameter("newpass");
                String new_access = request.getParameter("newaccess");
                //Change access                 
                String b_user = request.getParameter("access_username");
                String access = request.getParameter("access"); 
                
                db = new DBConnect();
                db.connect();
                
                User modifyUser = new User(db.getConn());                
                if (!newPass.isEmpty()) {
                    boolean changed = modifyUser.changePassword(a_user, newPass);

                    if (changed) {
                        request.setAttribute("announcement", "Updated Password");
                        request.getRequestDispatcher("admin.jsp").forward(request, response);
                    } else {
                        request.setAttribute("pass", "failed to change password");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }else if (!new_name.isEmpty()) {
                    boolean changed = modifyUser.addUser(new_name, new_pass,new_access);
                    
                    if (changed) {
                        request.setAttribute("announcement", "User Added");
                        request.getRequestDispatcher("admin.jsp").forward(request, response);
                    } else {
                        request.setAttribute("pass", "failed to add user");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }else if (!access.isEmpty()) {
                    boolean changed = modifyUser.changeAccess(b_user, access);

                    if (changed) {
                        request.setAttribute("announcement", "Updated Access");
                        request.getRequestDispatcher("admin.jsp").forward(request, response);
                    } else {
                        request.setAttribute("pass", "failed to update access");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }
                break;
            case "adminpanel.jsp":
                String dash = request.getParameter("dashboard");
                switch(dash){
                    case "user":
                        List<User> users = new ArrayList();
                        db = new DBConnect();
                        db.connect();

                        User appUsers = new User(db.getConn());
                        ResultSet allUsers = appUsers.getAllUsers();
                        while(allUsers.next()){
                            User one = new User();
                            one.setUserName(allUsers.getString("USER_NAME"));
                            one.setLevel(allUsers.getString("LEVEL"));
                            users.add(one);
                        }
                        
                        request.setAttribute("doctorCount", "8000");
                        request.setAttribute("users", users);
                        request.getRequestDispatcher("userpanel.jsp").forward(request, response);
                        break;
                    default:
                        break;
                }
//                request.setAttribute("pass", "From users page");
//                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "volunteerpanel.jsp":
                List<User> users = new ArrayList();
                db = new DBConnect();
                db.connect();
                String v_first = request.getParameter("v_first_name");
                String v_last = request.getParameter("v_last_name");
                String v_found = request.getParameter("v_display_name");
                //System.out.println(letter);
//                User appUsers = new User(db.getConn());
//                ResultSet allUsers = appUsers.getUserStartWith(letter);
//                while (allUsers.next()) {
//                    User one = new User();
//                    one.setUserName(allUsers.getString("USER_NAME"));                    
//                    users.add(one);
//                }
//                
//                request.setAttribute("patients", users);
                
                //Patients
                List<Patient> patients = new ArrayList();
                Patient patient = new Patient(db.getConn());
                ResultSet allPatients = null;
                if(v_found.isEmpty()){
                    if(!v_first.isEmpty()){
                        allPatients = patient.getVolunteerFirst(v_first);
                    }                
                    if(!v_last.isEmpty()){
                        allPatients = patient.getVolunteerLast(v_last);
                    }
                    while (allPatients.next()) {
                        Patient p = new Patient();
                        String first = allPatients.getString("FIRST_NAME");
                        String last = allPatients.getString("LAST_NAME");
                        p.setDisplayName(first + " " + last);                    
                        patients.add(p);
                    }
                    request.setAttribute("vol_info", patients);
                    request.getRequestDispatcher("volunteerpanel.jsp").forward(request, response);
                }else{
                    Patient found = new Patient(db.getConn());
                    String[] s_array = v_found.split(" ");    
                    
                    Patient foundIt = found.getPatient(s_array[0],s_array[1]);
                    request.setAttribute("v_first_name", foundIt.getFirstName());
                    request.setAttribute("v_last_name", foundIt.getLastName());
                    
                    ResultSet location = found.getLocation(foundIt);
                    String facility = null;
                    String floor = null;
                    String room = null;
                    String bed = null;
                    while (location.next()) {
                        facility = location.getString("FACILITY");
                        floor = location.getString("FLOOR");
                        room = location.getString("ROOM_NUMBER");
                        bed = location.getString("BED_NUMBER");
                    }
                    request.setAttribute("v_facility", facility);
                    request.setAttribute("v_floor", floor);
                    request.setAttribute("v_room", room);
                    request.setAttribute("v_bed", bed);
                    
                    request.getRequestDispatcher("volunteerResult.jsp").forward(request, response);
                }
                
                
                
                
                break;
            default:
                request.setAttribute("pass", "Default Switch");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            
        }
        
        
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {        
            processRequest(request, response);
//        request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        try {        
            processRequest(request, response);
//        request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
