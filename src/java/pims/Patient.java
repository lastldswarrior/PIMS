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
public class Patient {
    
    private Connection conn;
    private String lastName;
    private String firstName;
    private String displayName;
    private String middleName;
    private String street;
    private String city;
    private String state;
    private String zip;
    
    public Patient(){
        
    }
    
    public Patient(Connection connection){
        this.conn = connection;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public ResultSet getAllPatients() {
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.PATIENT "
                    + "WHERE "
                    + "PATIENT.LAST_NAME like 'Paul%'";

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public ResultSet getVolunteerFirst(String first) {
        String first_temp = first.replaceAll("[\\*]", "%");        
        String upper_first = first_temp.toUpperCase();    
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.PATIENT "
                    + "WHERE "
                    + "UPPER(PATIENT.FIRST_NAME) like UPPER('"+upper_first+"')";

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public ResultSet getVolunteerLast(String last) {
        String last_temp = last.replaceAll("[\\*]", "%");
        String upper_last = last_temp.toUpperCase();
        
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.PATIENT "
                    + "WHERE "
                    + "UPPER(PATIENT.LAST_NAME) like UPPER('"+upper_last+"')";

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
}
