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
                    case "Doctor":
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
            default:
                request.setAttribute("pass", "Default Switch");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            
        }
        //request.setAttribute("pass", pageName);
        
        //CALL THE DB CONNECTION ONE TIME, THEN PASS IT AROUND
//        DBConnect db = new DBConnect();
//        db.connect();
//        
//        AuthenticateUser au = new AuthenticateUser(db.getConn());
//        String levelOfAccess = au.validate(user, pass);
//        
//        switch(levelOfAccess){
//            case "Admin":
//                request.getRequestDispatcher("admin.jsp").forward(request, response);
//                break;
//                
//            default:
//                request.setAttribute("pass", levelOfAccess);
//                request.getRequestDispatcher("index.jsp").forward(request, response);
//                break;
//        }    
//        
        //String returnThis = db.returnThis("epic");
//        String level = db.validate(user, pass);
        
//        request.setAttribute("pass", levelOfAccess);
        
//        if(level.length() > 1){
            
//        }
        
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
