/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jared
 */
public class User {
    
    private Connection conn;
    private String userName;
    private String userPassword;
    private String level;
        
    public User(Connection connection){
         this.conn = connection;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
    public boolean changePassword(String name, String newPassWord) {

        try {
            String changePassword = "UPDATE PATIENTDB.USERS "
                    + "SET "
                        + "USER_PASSWORD = '"+newPassWord+"' "
                    + "WHERE "
                        + "USERS.USER_NAME = '"+name+"'";
                        
            PreparedStatement ps = conn.prepareStatement(changePassword);
            ps.execute();
            
            return true;
            
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
