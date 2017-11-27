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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String id;

    public Patient() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient(Connection connection) {
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
                    + "UPPER(PATIENT.FIRST_NAME) like UPPER('" + upper_first + "')";

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
                    + "UPPER(PATIENT.LAST_NAME) like UPPER('" + upper_last + "')";

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public Patient getPatient(String first, String last) {
        String upper_first = first.toUpperCase();
        String upper_last = last.toUpperCase();

        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.PATIENT "
                    + "WHERE "
                    + "UPPER(PATIENT.LAST_NAME) like UPPER('" + upper_last + "') "
                    + "and UPPER(PATIENT.FIRST_NAME) like UPPER('" + upper_first + "')";

            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            Patient one = new Patient();
            while (rs.next()) {
                one.setId(rs.getString("ID"));
                one.setFirstName(rs.getString("FIRST_NAME"));
                one.setLastName(rs.getString("LAST_NAME"));
                one.setMiddleName(rs.getString("MIDDLE_NAME"));
                
                one.setStreet(rs.getString("STREET"));
                one.setCity(rs.getString("CITY"));
                one.setState(rs.getString("STATE"));
                one.setZip(rs.getString("ZIP"));
            }
            return one;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ResultSet getLocation(Patient p) {

        try {
            String statement
                    = "SELECT "
                    + "loc.facility, "
                    + "loc.floor, "
                    + "loc.room_number, "
                    + "loc.bed_number, "
                    + "loc.discharged_date, "
                    + "loc.discharged_time, "
                    + "loc.visitor_count, "
                    + "loc.visitors "
                    + "FROM "
                    + "PATIENTDB.PATIENT p, "
                    + "PATIENTDB.LOCATION loc "
                    + "WHERE "
                    + "p.id = " + p.getId() + " "
                    + "and p.id = loc.id";

            System.out.println(statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ResultSet getContact(Patient p) {
        String upper_first = p.getFirstName().toUpperCase();
        String upper_last = p.getLastName().toUpperCase();
        System.out.println("contact function called");
        try {
            String statement
                    = "SELECT "
                    + "c.home_phone, "
                    + "c.work_phone, "
                    + "c.cell_phone, "
                    + "c.emergency_contact_name_01, "
                    + "c.emergency_contact_number_01, "
                    + "c.emergency_contact_name_02, "
                    + "c.emergency_contact_number_02 "
                    + "FROM "
                    + "PATIENTDB.PATIENT p, "
                    + "PATIENTDB.CONTACT c "
                    + "WHERE "
                    + "UPPER(p.LAST_NAME) like UPPER('" + upper_last + "') "
                    + "and UPPER(p.FIRST_NAME) like UPPER('" + upper_first + "') "
                    + "and p.id = c.id";
            System.out.println("contact = " + statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();

            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ResultSet getTreatment(Patient p) {
        String upper_first = p.getFirstName().toUpperCase();
        String upper_last = p.getLastName().toUpperCase();
        System.out.println("Treatment function called");
        try {
            String statement
                    = "SELECT "
                    + "t.admittance_date, "
                    + "t.family_doctor, "
                    + "t.admittance_time, "
                    + "t.doctors_treatment_notes, "
                    + "t.nurses_treatment_notes, "
                    + "t.scheduled_procedure, "
                    + "t.admittance_reason "
                    + "FROM "
                    + "PATIENTDB.PATIENT p, "
                    + "PATIENTDB.TREATMENT t "
                    + "WHERE "
                    + "UPPER(p.LAST_NAME) like UPPER('" + upper_last + "') "
                    + "and UPPER(p.FIRST_NAME) like UPPER('" + upper_first + "') "
                    + "and p.id = t.id";
            System.out.println(statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();

            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ResultSet getInsurance(Patient p) {
        String upper_first = p.getFirstName().toUpperCase();
        String upper_last = p.getLastName().toUpperCase();
        
        try {
            String statement
                    = "SELECT "
                    + "i.carrier, "
                    + "i.policy_account_number, "
                    + "i.policy_group_number, "
                    + "i.amount_paid_by_insurance "
                    + "FROM "
                    + "PATIENTDB.PATIENT p, "
                    + "PATIENTDB.INSURANCE i "
                    + "WHERE "
                    + "UPPER(p.LAST_NAME) like UPPER('" + upper_last + "') "
                    + "and UPPER(p.FIRST_NAME) like UPPER('" + upper_first + "') "
                    + "and p.id = i.id";
            System.out.println(statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();

            return rs;

        } catch (SQLException ex) {
            
            return null;
        }
    }

    public ResultSet getPrescriptions(Patient p) {
        String upper_first = p.getFirstName().toUpperCase();
        String upper_last = p.getLastName().toUpperCase();
        try {
            String statement
                    = "SELECT "
                    + "ps.name, "
                    + "ps.schedule, "
                    + "ps.amount "
                    + "FROM "
                    + "PATIENTDB.PATIENT p, "
                    + "PATIENTDB.PRESCRIPTIONS ps "
                    + "WHERE "
                    + "UPPER(p.LAST_NAME) like UPPER('" + upper_last + "') "
                    + "and UPPER(p.FIRST_NAME) like UPPER('" + upper_first + "') "
                    + "and p.id = ps.id";
            System.out.println("Prescriptions called = " + statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();

            return rs;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public boolean changeAmountPaidByInsurance(String id, String amount) {
        try {
            String access = "UPDATE PATIENTDB.INSURANCE "
                    + "SET "
                    + "AMOUNT_PAID_BY_INSURANCE = "+ amount +" "
                    + "WHERE "
                    + "INSURANCE.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeNursesTreatmentNotes(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.TREATMENT "
                    + "SET "
                    + "NURSES_TREATMENT_NOTES = '" + phone_number + "' "
                    + "WHERE "
                    + "TREATMENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeScheduledProcedure(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.TREATMENT "
                    + "SET "
                    + "SCHEDULED_PROCEDURE = '" + phone_number + "' "
                    + "WHERE "
                    + "TREATMENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeDoctorsTreatmentNotes(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.TREATMENT "
                    + "SET "
                    + "DOCTORS_TREATMENT_NOTES = '" + phone_number + "' "
                    + "WHERE "
                    + "TREATMENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeAdmissionReason(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.TREATMENT "
                    + "SET "
                    + "ADMITTANCE_REASON = '" + phone_number + "' "
                    + "WHERE "
                    + "TREATMENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changePerscriptionName(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.PRESCRIPTIONS "
                    + "SET "
                    + "NAME = '" + phone_number + "' "
                    + "WHERE "
                    + "PRESCRIPTIONS.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changePerscriptionSchedule(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.PRESCRIPTIONS "
                    + "SET "
                    + "SCHEDULE = '" + phone_number + "' "
                    + "WHERE "
                    + "PRESCRIPTIONS.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changePerscriptionAmount(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.PRESCRIPTIONS "
                    + "SET "
                    + "AMOUNT = '" + phone_number + "' "
                    + "WHERE "
                    + "PRESCRIPTIONS.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changePolicyGroupNumber(String id, String groupNum) {
        try {
            String access = "UPDATE PATIENTDB.INSURANCE "
                    + "SET "
                    + "POLICY_GROUP_NUMBER = "+ groupNum +" "
                    + "WHERE "
                    + "INSURANCE.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changePolicyAccountNumber(String id, String acctNum) {
        try {
            String access = "UPDATE PATIENTDB.INSURANCE "
                    + "SET "
                    + "POLICY_ACCOUNT_NUMBER = "+ acctNum +" "
                    + "WHERE "
                    + "INSURANCE.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeInsuranceCarrier(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.INSURANCE "
                    + "SET "
                    + "CARRIER = '" + phone_number + "' "
                    + "WHERE "
                    + "INSURANCE.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeBedNumber(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "BED_NUMBER = '" + phone_number + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeRoomNumber(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "ROOM_NUMBER = '" + phone_number + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeFloor(String id, String floor) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "FLOOR = '" + floor + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeFacility(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "FACILITY = '" + phone_number + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeAddmittanceTime(String id, String time) {
        try {
            String access = "UPDATE PATIENTDB.TREATMENT "
                    + "SET "
                    + "ADMITTANCE_TIME = '" + time + "' "
                    + "WHERE "
                    + "TREATMENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeFamilyDoctor(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.TREATMENT "
                    + "SET "
                    + "FAMILY_DOCTOR = '" + phone_number + "' "
                    + "WHERE "
                    + "TREATMENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeDischargeTime(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "DISCHAGRE_TIME = '" + phone_number + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeDischargeDate(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "DISCHARGE_DATE = '" + phone_number + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeAdmittanceDate(String id, String date) {
        try {
            String access = "UPDATE PATIENTDB.TREATMENT "
                    + "SET "
                    + "ADMITTANCE_DATE = DATE('" + date + "') "
                    + "WHERE "
                    + "TREATMENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeEmgContNum2(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.CONTACT "
                    + "SET "
                    + "EMERGENCY_CONTACT_NUMBER_02 = '" + phone_number + "' "
                    + "WHERE "
                    + "CONTACT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeEmgContNum1(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.CONTACT "
                    + "SET "
                    + "EMERGENCY_CONTACT_NUMBER_01 = '" + phone_number + "' "
                    + "WHERE "
                    + "CONTACT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeEmgContName2(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.CONTACT "
                    + "SET "
                    + "EMERGENCY_CONTACT_NAME_02 = '" + phone_number + "' "
                    + "WHERE "
                    + "CONTACT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeEmgContName1(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.CONTACT "
                    + "SET "
                    + "EMERGENCY_CONTACT_NAME_01 = '" + phone_number + "' "
                    + "WHERE "
                    + "CONTACT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeWorkPhone(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.CONTACT "
                    + "SET "
                    + "WORK_PHONE = '" + phone_number + "' "
                    + "WHERE "
                    + "CONTACT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeHomePhone(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.CONTACT "
                    + "SET "
                    + "HOME_PHONE = '" + phone_number + "' "
                    + "WHERE "
                    + "CONTACT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeCellPhone(String id, String phone_number) {
        try {
            String access = "UPDATE PATIENTDB.CONTACT "
                    + "SET "
                    + "CELL_PHONE = '" + phone_number + "' "
                    + "WHERE "
                    + "CONTACT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeRoom(String id, String room) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "ROOM_NUMBER = '" + room + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeCity(String id, String city) {
        try {
            String access = "UPDATE PATIENTDB.PATIENT "
                    + "SET "
                    + "CITY = '" + city + "' "
                    + "WHERE "
                    + "PATIENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeVisitors(String id, String room) {
        try {
            String access = "UPDATE PATIENTDB.LOCATION "
                    + "SET "
                    + "ROOM_NUMBER = '" + room + "' "
                    + "WHERE "
                    + "LOCATION.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeStreet(String id, String street) {
        try {
            String access = "UPDATE PATIENTDB.PATIENT "
                    + "SET "
                    + "STREET = '" + street + "' "
                    + "WHERE "
                    + "PATIENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeZip(String id, String Zip) {
        try {
            String access = "UPDATE PATIENTDB.PATIENT "
                    + "SET "
                    + "ZIP = "+ Zip +" "
                    + "WHERE "
                    + "PATIENT.ID = " + id + "";
            
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean changeState(String id, String state) {
        try {
            String access = "UPDATE PATIENTDB.PATIENT "
                    + "SET "
                    + "STATE = '" + state + "' "
                    + "WHERE "
                    + "PATIENT.ID = " + id + "";

            System.out.println(access);
            PreparedStatement ps = conn.prepareStatement(access);
            ps.execute();

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }
    
    public List<String> getDebts(Patient p){
        List<String> list = new ArrayList();
        
        try {
            String statement
                    = "SELECT "
                        + "billing_id "
                    + "FROM "
                        + "PATIENTDB.PATIENT "
                    + "WHERE "
                        + "id = "+p.id+"";
            System.out.println(statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            String results = null;
            while(rs.next()){
                results = rs.getString("BILLING_ID");
            }
            
            //get the bills now
            String statement2
                    = "SELECT * "
                    + "FROM "
                        + "PATIENTDB.BILLING "
                    + "WHERE "
                        + "id IN ("+results+")";
            System.out.println(statement2);
            pst = conn.prepareStatement(statement2);
            rs = pst.executeQuery();
            
            while(rs.next()){
                list.add(rs.getString("SERVICE_NAME"));
                list.add(rs.getString("SERVICE_CHARGE"));
            }

            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }        
    }
    
    public ResultSet getInsinfo(Patient p){
        try {
            String statement
                    = "SELECT "
                    + "* "
                    + "FROM "
                    + "PATIENTDB.INSURANCE "
                    + "WHERE "
                    + "id = "+p.id+"";
            
            System.out.println(statement);
            PreparedStatement pst = conn.prepareStatement(statement);
            ResultSet rs = pst.executeQuery();
            return rs;
            
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
}
