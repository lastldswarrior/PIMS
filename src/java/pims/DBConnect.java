package pims;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jared
 */
public class DBConnect {

    public DBConnect() {

    }
    
    public String returnThis(String string){
        return string;
    }

    public String validate(String name, String pass) {
        try {
            PreparedStatement pst = null;
            ResultSet rs = null;
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/localDB", "PatientDB", "ninjas");
            
            if (!conn.isClosed()) {
                System.out.println("Connected to: " + conn.getSchema());
            }
            String statement = "select * from PATIENTDB.USERS where USERS.USER_NAME='"+name+"' and USERS.USER_PASSWORD='"+pass+"'";
            
            pst = conn.prepareStatement(statement);
            rs = pst.executeQuery();
            String last = null;
            while (rs.next()) {
                last = rs.getString("LEVEL");
            }
            return last;
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null,ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}


