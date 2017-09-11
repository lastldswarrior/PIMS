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
    private int userCount;
        
    public User(){
        
    }
    
    public User(Connection connection){
         this.conn = connection;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
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
    
    //validate user is in our users table
    public String validate() {        
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.USERS "
                    + "WHERE "
                    + "USERS.USER_NAME = '" + this.userName + "' "
                    + "and USERS.USER_PASSWORD = '" + this.userPassword + "'";
            
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            String last = "Login Failed, Please try again";
            while (rs.next()) {
                last = rs.getString("LEVEL");
            }
            this.level = last;
            
            return last;
        } catch (SQLException ex) {
            this.level = "Unvalidated";
            return null;
        }
    }
    
    public void countUsers() {        
        try {
            String statement
                    = "SELECT "
                    + "COUNT(*) "
                    + "FROM "
                    + "PATIENTDB.USERS";
            
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            int result = -1;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            this.userCount = result;
            
            
        } catch (SQLException ex) {
            this.userCount = -1;
            
        }
    }
    
    public ResultSet getAllUsers() {
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.USERS";

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            return null;
        }
    }
    
    public ResultSet getUserStartWith(String letter) {
        String lower = letter.toLowerCase();
        String upper = letter.toUpperCase();
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.USERS "
                    + "WHERE "
                    + "USER_NAME like '"+upper+"%' "
                    + "or USER_NAME like '"+lower+"%'";

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            return null;
        }
    }

    public boolean addUser(String user, String pass, String level){
        try {
            String add = "INSERT INTO PATIENTDB.USERS (USER_NAME, USER_PASSWORD, LEVEL) "
                    + "VALUES ('"+user+"','"+pass+"','"+level+"')";
                   
                        
            PreparedStatement ps = conn.prepareStatement(add);
            ps.execute();
            
            return true;
            
        } catch (SQLException ex) {
            return false;
        }
    }
    public boolean changeAccess(String user, String level){
        try {
             String access = "UPDATE PATIENTDB.USERS "
                    + "SET "
                        + "LEVEL = '"+level+"' "
                    + "WHERE "
                        + "USERS.USER_NAME = '"+user+"'";
                   
                        
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();
            
            return true;
            
        } catch (SQLException ex) {
            return false;
        }
    }
}
