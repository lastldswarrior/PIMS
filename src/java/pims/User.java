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
        String temp = letter.replaceAll("[\\*]", "%");
        System.out.println(temp); 
        String upper = temp.toUpperCase();        
        
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.USERS "
                    + "WHERE "
                    + "UPPER(USER_NAME) like UPPER('"+upper+"')";
                    

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            return null;
        }
    }

    public boolean addUser(String user, String pass, String level, String email){
        try {
            String add = "INSERT INTO PATIENTDB.USERS (USER_NAME, USER_PASSWORD, LEVEL, EMAIL) "
                    + "VALUES ('"+user+"','"+pass+"','"+level+"','"+email+"')";
                   
                        
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
    
    public int getCount(String year){
        int count = 0;
        try {
            String statement
                    = "SELECT "
                    + "COUNT(*) "
                    + "FROM "
                    + "PATIENTDB.LOCATION "
                    + "WHERE "
                    + "DISCHARGED_DATE like '%"+year+"'";
                    
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                count = Integer.parseInt(rs.getString(1));
            }
            
            return count;
            
        } catch (SQLException ex) {
            return -1;
        }
    }

    public boolean purge(String year) {
        try {
            String thinning = "DELETE FROM PATIENTDB.PATIENT "
                    + "WHERE ID = ("
                    + "select id "
                    + "from location "
                    + "where discharged_date like '%"+year+"' and id = 3507)";//TODO: remove id so we can purge all 2012
                   
            System.out.println(thinning);
            PreparedStatement ps = conn.prepareStatement(thinning);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

}
