/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pims;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jared
 */
public class AuthenticateUser {
    
    private Connection conn;
    
    public AuthenticateUser(Connection connection){
        this.conn = connection;
    }
    
    //validate user is in our users table
    public String validate(String name, String pass) {

        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.USERS "
                    + "WHERE "
                    + "USERS.USER_NAME = '" + name + "' "
                    + "and USERS.USER_PASSWORD = '" + pass + "'";
            
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            String last = "Login Failed, Please try again";
            while (rs.next()) {
                last = rs.getString("LEVEL");
            }
            return last;
        } catch (SQLException ex) {
            return null;
        }

    }

}
