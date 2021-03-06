package pims;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    private DBConnect db;

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
            case "forgot.jsp":                
                String forgotEmail = request.getParameter("f_email");
                db = new DBConnect();
                db.connect();                 
                
                Email email = new Email(db.getConn(),forgotEmail);
                email.send();
                
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "register.jsp":
                String registerUsername = request.getParameter("rname");
                String registerEmail = request.getParameter("email");
                String registerPassword = request.getParameter("userpass");
                String registerPassword2 = request.getParameter("userpass2");
                String registerLevel = request.getParameter("level");
                
                db = new DBConnect();
                db.connect();                
                
                if(registerPassword.equals(registerPassword2)){
                    User myUser = new User(db.getConn());                    
                    myUser.addUser(registerUsername, registerPassword, registerLevel, registerEmail);                    
                    request.setAttribute("newUser", registerUsername);
                }               
                
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;                
            case "index.jsp":
                String username = request.getParameter("username");
                String pass = request.getParameter("userpass");   
                
                db = new DBConnect();
                db.connect();

                User user = new User(db.getConn());
                user.setUserName(username);
                user.setUserPassword(pass);
                String levelOfAccess = user.validate();
                user.countUsers();
                
                //from index go to which page
                switch (levelOfAccess) {
                    case "Admin":  
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
                        request.setAttribute("user", user.getUserName());                        
                        request.setAttribute("users", users);
                        request.setAttribute("userCount", user.getUserCount());
                        request.getRequestDispatcher("adminpanel.jsp").forward(request, response);
                        break;
                    case "Volunteer": 
                        request.getRequestDispatcher("volunteerpanel.jsp").forward(request, response);
                        break;
                    case "Office":                         
                        request.getRequestDispatcher("officepanel.jsp").forward(request, response);                        
                        break;
                    case "Nurse": 
                        request.getRequestDispatcher("nursepanel.jsp").forward(request, response);
                        break; 
                    case "Doctor":                         
                        request.getRequestDispatcher("doctorpanel.jsp").forward(request, response);
                        break;    
                    default:
                        request.setAttribute("pass", levelOfAccess);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                }
                break;
            case "adminpanel.jsp":
                String a_user = request.getParameter("username");
                String new_name = request.getParameter("newusername");
                String b_user = request.getParameter("access_username");
                System.out.println(a_user + " -- "+new_name+" -- "+b_user);
                if(a_user == "null"  || new_name == "null" || b_user == "null"){
                    db = new DBConnect();
                    db.connect();
                    
                    //get the patient counts of each year                   
                    User u = new User(db.getConn());
                    int count_12 = u.getCount("2012");
                    int count_13 = u.getCount("2013");
                    int count_14 = u.getCount("2014");
                    int count_15 = u.getCount("2015");
                    int count_16 = u.getCount("2016");
                    int count_17 = u.getCount("2017");
                    
                    //set the variables
                    request.setAttribute("num_2012", count_12);
                    request.setAttribute("num_2013", count_13);
                    request.setAttribute("num_2014", count_14);
                    request.setAttribute("num_2015", count_15);
                    request.setAttribute("num_2016", count_16);
                    request.setAttribute("num_2017", count_17);                    
                    
                    //go to databasepanel   
                    request.getRequestDispatcher("databasepanel.jsp").forward(request, response);
                }else{
                    workAdmin(request, response);
                }                
                break;
            case "databasepanel.jsp":                
                //Get database connection
                db = new DBConnect();
                db.connect();                             
                User u = new User(db.getConn());
                
                //Purge the database
                u.purge("2012");
                request.setAttribute("announcement", "Purged Patients");       
                
                //get the updated patient counts of each year      
                int count_12 = u.getCount("2012");
                int count_13 = u.getCount("2013");
                int count_14 = u.getCount("2014");
                int count_15 = u.getCount("2015");
                int count_16 = u.getCount("2016");
                int count_17 = u.getCount("2017");

                //set the variables
                request.setAttribute("num_2012", count_12);
                request.setAttribute("num_2013", count_13);
                request.setAttribute("num_2014", count_14);
                request.setAttribute("num_2015", count_15);
                request.setAttribute("num_2016", count_16);
                request.setAttribute("num_2017", count_17);
                
                //go to databasepanel   
                request.getRequestDispatcher("databasepanel.jsp").forward(request, response);
                break;
            case "volunteerpanel.jsp":
                
                List<User> users = new ArrayList();
                db = new DBConnect();
                db.connect();
                String v_first = request.getParameter("v_first_name");
                String v_last = request.getParameter("v_last_name");
                String v_found = request.getParameter("v_display_name");
                               
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
                    String count = null;
                    String s_visitor = null;
                    List<String> visitors = new ArrayList();
                    while (location.next()) {
                        facility = location.getString("FACILITY");
                        floor = location.getString("FLOOR");
                        room = location.getString("ROOM_NUMBER");
                        bed = location.getString("BED_NUMBER");
                        count = location.getString("VISITOR_COUNT");
                        s_visitor = location.getString("VISITORS");
                        
                    }
                    //store visitors into list
                    String[] split = s_visitor.split(",");
                    visitors = Arrays.asList(split);
                    
                    request.setAttribute("v_facility", facility);
                    request.setAttribute("v_floor", floor);
                    request.setAttribute("v_room", room);
                    request.setAttribute("v_bed", bed);
                    request.setAttribute("v_count", count);
                    request.setAttribute("v_list", visitors);
                    
                    request.getRequestDispatcher("volunteerResult.jsp").forward(request, response);
                }
                break;
            case "officepanel.jsp":
                workOffice(request, response);
                request.getRequestDispatcher("officepanel.jsp").forward(request, response);
                break;
            case "doctorpanel.jsp":
                workDoctor(request);
                request.getRequestDispatcher("doctorpanel.jsp").forward(request, response);
                break;    
            case "nursepanel.jsp":
                workNurse(request);
                request.getRequestDispatcher("nursepanel.jsp").forward(request, response);
                break;   
            default:
                request.setAttribute("pass", "Default Switch");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;            
        }//end of switch                
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
     * Returns a short description of the servlet.     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private boolean workAdmin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        
                
        String a_user = request.getParameter("username");
        String newPass = request.getParameter("a_pass");
        //New user
        String new_name = request.getParameter("newusername");
        String new_pass = request.getParameter("newpass");
        String new_email = request.getParameter("email");
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
            } else {
                request.setAttribute("pass", "failed to change password");                
            }
            request.getRequestDispatcher("adminpanel.jsp").forward(request, response);
        } else if (!new_name.isEmpty()) {
            boolean changed = modifyUser.addUser(new_name, new_pass, new_access, new_email);

            if (changed) {
                request.setAttribute("announcement", "User Added");                
            } else {
                request.setAttribute("pass", "failed to add user");                
            }
            request.getRequestDispatcher("adminpanel.jsp").forward(request, response);
        } else if (!access.isEmpty()) {
            boolean changed = modifyUser.changeAccess(b_user, access);

            if (changed) {
                request.setAttribute("announcement", "Updated Access");                
            } else {
                request.setAttribute("pass", "failed to update access");                
            }
            request.getRequestDispatcher("adminpanel.jsp").forward(request, response);
        }
        return true;
    }
    
    private boolean workNurse(HttpServletRequest request) throws Exception{       

        String v_found4 = request.getParameter("v_display_name");
        String off_display3 = request.getParameter("v_display_name");
        String v_first4 = request.getParameter("v_first_name");
        String v_last4 = request.getParameter("v_last_name");

        db = new DBConnect();
        db.connect();

        String n_AdmissionReason2 = request.getParameter("new_AdmissionReason");
        String n_NurseTreatmentNotes2 = request.getParameter("new_NurseTreatmentNotes");
        String n_PerscriptionName2 = request.getParameter("new_PerscriptionName");
        String n_PerscriptionSchedule2 = request.getParameter("new_PerscriptionSchedule");
        String n_PerscriptionAmount2 = request.getParameter("new_PerscriptionAmount");
        String n_ScheduledProcedure2 = request.getParameter("new_Scheduled_Procedures");

        if (!n_ScheduledProcedure2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display3.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changeScheduledProcedure = myPatient.changeScheduledProcedure(dbPatient.getId(), n_ScheduledProcedure2);
        }
        if (!n_AdmissionReason2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display3.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changeAdmissionReason = myPatient.changeAdmissionReason(dbPatient.getId(), n_AdmissionReason2);
        }
        if (!n_NurseTreatmentNotes2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display3.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changeNurseTreatmentNotes = myPatient.changeNursesTreatmentNotes(dbPatient.getId(), n_NurseTreatmentNotes2);
        }
        if (!n_PerscriptionName2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display3.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changePerscriptionName = myPatient.changePerscriptionName(dbPatient.getId(), n_PerscriptionName2);
        }
        if (!n_PerscriptionSchedule2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display3.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changePerscriptionSchedule = myPatient.changePerscriptionSchedule(dbPatient.getId(), n_PerscriptionSchedule2);
        }
        if (!n_PerscriptionAmount2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display3.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changePerscriptionAmount = myPatient.changePerscriptionAmount(dbPatient.getId(), n_PerscriptionAmount2);
        }

        List<Patient> patients4 = new ArrayList();
        Patient patient5 = new Patient(db.getConn());
        ResultSet allPatients4 = null;
        if (v_found4.isEmpty()) {
            if (!v_first4.isEmpty()) {
                allPatients4 = patient5.getVolunteerFirst(v_first4);
            }
            if (!v_last4.isEmpty()) {
                allPatients4 = patient5.getVolunteerLast(v_last4);
            }
            while (allPatients4.next()) {
                Patient p = new Patient();
                String first = allPatients4.getString("FIRST_NAME");
                String last = allPatients4.getString("LAST_NAME");
                String street = allPatients4.getString("STREET");
                String id = allPatients4.getString("ID");
                p.setDisplayName(first + " " + last);
                p.setStreet(street);
                //   p.setID(id);
                patients4.add(p);
            }
        } else {

            Patient patient4 = new Patient(db.getConn());
            String[] s_array = off_display3.split(" ");

            Patient dbPatient2 = patient4.getPatient(s_array[0], s_array[1]);
            ResultSet location = patient4.getLocation(dbPatient2);
            ResultSet contact = patient4.getContact(dbPatient2);
            ResultSet treatment = patient4.getTreatment(dbPatient2);
            ResultSet Insurance = patient4.getInsurance(dbPatient2);
            ResultSet Prescriptions = patient4.getPrescriptions(dbPatient2);

            String AdmittanceDate = "";
            String AdmittanceTime = "";
            String DischargeDate = "";
            String DischargeTime = "";

            String FamilyDoctor = "";
            String homePhone = "";
            String workPhone = "";
            String cellPhone = "";
            String EmergencyName1 = "";
            String EmergencyNumber1 = "";
            String EmergencyName2 = "";
            String EmergencyNumber2 = "";
            String Facility = "";
            String Floor = "";
            String room = "";
            String BedNumber = "";
            String city = "";
            String floorNumber = "";

            String Carrier = "";
            String Policy_Account_Number = "";
            String Policy_Group_Number = "";
            String Amount_Paid_By_Insurance = "";
            String Prescription_Name = "";
            String Prescription_Schedule = "";
            String Prescription_Amount = "";
            String Reason_For_Admission = "";
            String Doctors_Treatment_Notes = "";
            String Nurses_Treatment_Notes = "";
            String Scheduled_Procedure = "";

            String street = dbPatient2.getStreet();
            String zip = dbPatient2.getZip();
            String state = dbPatient2.getState();
            city = dbPatient2.getCity();

            while (Prescriptions.next()) {
                Prescription_Name = Prescriptions.getString("NAME");
                Prescription_Schedule = Prescriptions.getString("SCHEDULE");
                Prescription_Amount = Prescriptions.getString("AMOUNT");
            }

            while (treatment.next()) {
                AdmittanceDate = treatment.getString("ADMITTANCE_DATE");
                AdmittanceTime = treatment.getString("ADMITTANCE_TIME");
                FamilyDoctor = treatment.getString("FAMILY_DOCTOR");
                Reason_For_Admission = treatment.getString("ADMITTANCE_REASON");
                Doctors_Treatment_Notes = treatment.getString("DOCTORS_TREATMENT_NOTES");
                Nurses_Treatment_Notes = treatment.getString("NURSES_TREATMENT_NOTES");
                Scheduled_Procedure = treatment.getString("SCHEDULED_PROCEDURE");
            }
            while (Insurance.next()) {
                Carrier = Insurance.getString("CARRIER");
                Policy_Account_Number = Insurance.getString("POLICY_ACCOUNT_NUMBER");
                Policy_Group_Number = Insurance.getString("POLICY_GROUP_NUMBER");
                Amount_Paid_By_Insurance = Insurance.getString("AMOUNT_PAID_BY_INSURANCE");
            }

            while (location.next()) {
                Facility = location.getString("FACILITY");
                Floor = location.getString("FLOOR");
                room = location.getString("ROOM_NUMBER");
                floorNumber = location.getString("FLOOR");
                BedNumber = location.getString("BED_NUMBER");
                DischargeDate = location.getString("DISCHARGED_DATE");
                DischargeTime = location.getString("DISCHARGED_TIME");
            }
            while (contact.next()) {
                homePhone = contact.getString("HOME_PHONE");
                workPhone = contact.getString("WORK_PHONE");
                cellPhone = contact.getString("CELL_PHONE");
                EmergencyName1 = contact.getString("EMERGENCY_CONTACT_NAME_01");
                EmergencyNumber1 = contact.getString("EMERGENCY_CONTACT_NUMBER_01");
                EmergencyName2 = contact.getString("EMERGENCY_CONTACT_NAME_02");
                EmergencyNumber2 = contact.getString("EMERGENCY_CONTACT_NUMBER_02");
            }

            request.setAttribute("v_display_name", v_found4);
            request.setAttribute("v_display_city", city);
            request.setAttribute("v_display_room", room);
            request.setAttribute("v_display_Street", street);
            request.setAttribute("v_display_Zip", zip);
            request.setAttribute("v_display_State", state);
            request.setAttribute("v_display_HomePhone", homePhone);
            request.setAttribute("v_display_WorkPhone", workPhone);
            request.setAttribute("v_display_CellPhone", cellPhone);
            request.setAttribute("v_display_EmergContName1", EmergencyName1);
            request.setAttribute("v_display_EmergContNum1", EmergencyNumber1);
            request.setAttribute("v_display_EmergContName2", EmergencyName2);
            request.setAttribute("v_display_EmergContNum2", EmergencyNumber2);
            request.setAttribute("v_display_AdmittanceDate", AdmittanceDate);
            request.setAttribute("v_display_AdmittanceTime", AdmittanceTime);
            request.setAttribute("v_display_FamilyDoctor", FamilyDoctor);
            request.setAttribute("v_display_Facility", Facility);
            request.setAttribute("v_display_Floor", Floor);
            request.setAttribute("v_display_BedNumber", BedNumber);
            request.setAttribute("v_display_DischargeDate", DischargeDate);
            request.setAttribute("v_display_DischargeTime", DischargeTime);
            request.setAttribute("v_display_InsuranceCarrier", Carrier);
            request.setAttribute("v_display_PolicyAccountNumber", Policy_Account_Number);
            request.setAttribute("v_display_PolicyGroupNumber", Policy_Group_Number);
            request.setAttribute("v_display_AmountPaidByInsurance", Amount_Paid_By_Insurance);
            request.setAttribute("v_display_PerscriptionName", Prescription_Name);
            request.setAttribute("v_display_PerscriptionSchedule", Prescription_Schedule);
            request.setAttribute("v_display_PerscriptionAmount", Prescription_Amount);
            request.setAttribute("v_display_AdmissionReason", Reason_For_Admission);
            request.setAttribute("v_display_DoctorsTreatmentNotes", Doctors_Treatment_Notes);
            request.setAttribute("v_display_NursesTreatmentNotes", Nurses_Treatment_Notes);
            request.setAttribute("v_display_Scheduled_Procedure", Scheduled_Procedure);
        }
        request.setAttribute("vol_info", patients4);
        

        return true;
    }
    
    private boolean workDoctor(HttpServletRequest request) throws Exception{
        
        String v_found3 = request.getParameter("v_display_name");
        String off_display2 = request.getParameter("v_display_name");
        String v_first3 = request.getParameter("v_first_name");
        String v_last3 = request.getParameter("v_last_name");

        db = new DBConnect();
        db.connect();

        String n_AdmissionReason = request.getParameter("new_AdmissionReason");
        String n_DoctorsTreatmentNotes = request.getParameter("new_DoctorsTreatmentNotes");
        String n_PerscriptionName = request.getParameter("new_PerscriptionName");
        String n_PerscriptionSchedule = request.getParameter("new_PerscriptionSchedule");
        String n_PerscriptionAmount = request.getParameter("new_PerscriptionAmount");
        String n_ScheduledProcedure = request.getParameter("new_Scheduled_Procedures");

        if (!n_ScheduledProcedure.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display2.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changeScheduledProcedure = myPatient.changeScheduledProcedure(dbPatient.getId(), n_ScheduledProcedure);
        }
        if (!n_AdmissionReason.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display2.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changeAdmissionReason = myPatient.changeAdmissionReason(dbPatient.getId(), n_AdmissionReason);
        }
        if (!n_DoctorsTreatmentNotes.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display2.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changeDoctorsTreatmentNotes = myPatient.changeDoctorsTreatmentNotes(dbPatient.getId(), n_DoctorsTreatmentNotes);
        }
        if (!n_PerscriptionName.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display2.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changePerscriptionName = myPatient.changePerscriptionName(dbPatient.getId(), n_PerscriptionName);
        }
        if (!n_PerscriptionSchedule.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display2.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changePerscriptionSchedule = myPatient.changePerscriptionSchedule(dbPatient.getId(), n_PerscriptionSchedule);
        }
        if (!n_PerscriptionAmount.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display2.split(" ");
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            boolean changePerscriptionAmount = myPatient.changePerscriptionAmount(dbPatient.getId(), n_PerscriptionAmount);
        }
        
        List<Patient> patients3 = new ArrayList();
        Patient patient4 = new Patient(db.getConn());
        ResultSet allPatients3 = null;
        if (v_found3.isEmpty()) {
            if (!v_first3.isEmpty()) {
                allPatients3 = patient4.getVolunteerFirst(v_first3);
            }
            if (!v_last3.isEmpty()) {
                allPatients3 = patient4.getVolunteerLast(v_last3);
            }
            while (allPatients3.next()) {
                Patient p = new Patient();
                String first = allPatients3.getString("FIRST_NAME");
                String last = allPatients3.getString("LAST_NAME");
                String street = allPatients3.getString("STREET");
                String id = allPatients3.getString("ID");
                p.setDisplayName(first + " " + last);
                p.setStreet(street);
                
                patients3.add(p);
            }
        } else {

            patient4 = new Patient(db.getConn());
            String[] s_array = off_display2.split(" ");

            Patient dbPatient2 = patient4.getPatient(s_array[0], s_array[1]);
            ResultSet location = patient4.getLocation(dbPatient2);
            ResultSet contact = patient4.getContact(dbPatient2);
            ResultSet treatment = patient4.getTreatment(dbPatient2);
            ResultSet Insurance = patient4.getInsurance(dbPatient2);
            ResultSet Prescriptions = patient4.getPrescriptions(dbPatient2);

            String AdmittanceDate = "";
            String AdmittanceTime = "";
            String DischargeDate = "";
            String DischargeTime = "";

            String FamilyDoctor = "";
            String homePhone = "";
            String workPhone = "";
            String cellPhone = "";
            String EmergencyName1 = "";
            String EmergencyNumber1 = "";
            String EmergencyName2 = "";
            String EmergencyNumber2 = "";
            String Facility = "";
            String Floor = "";
            String room = "";
            String BedNumber = "";
            String city = "";
            String floorNumber = "";

            String Carrier = "";
            String Policy_Account_Number = "";
            String Policy_Group_Number = "";
            String Amount_Paid_By_Insurance = "";
            String Prescription_Name = "";
            String Prescription_Schedule = "";
            String Prescription_Amount = "";
            String Reason_For_Admission = "";
            String Doctors_Treatment_Notes = "";
            String Nurses_Treatment_Notes = "";
            String Scheduled_Procedure = "";

            String street = dbPatient2.getStreet();
            String zip = dbPatient2.getZip();
            String state = dbPatient2.getState();
            city = dbPatient2.getCity();

            while (Prescriptions.next()) {
                Prescription_Name = Prescriptions.getString("NAME");
                Prescription_Schedule = Prescriptions.getString("SCHEDULE");
                Prescription_Amount = Prescriptions.getString("AMOUNT");
            }

            while (treatment.next()) {
                AdmittanceDate = treatment.getString("ADMITTANCE_DATE");
                AdmittanceTime = treatment.getString("ADMITTANCE_TIME");
                FamilyDoctor = treatment.getString("FAMILY_DOCTOR");
                Reason_For_Admission = treatment.getString("ADMITTANCE_REASON");
                Doctors_Treatment_Notes = treatment.getString("DOCTORS_TREATMENT_NOTES");
                Nurses_Treatment_Notes = treatment.getString("NURSES_TREATMENT_NOTES");
                Scheduled_Procedure = treatment.getString("SCHEDULED_PROCEDURE");
            }
            while (Insurance.next()) {
                Carrier = Insurance.getString("CARRIER");
                Policy_Account_Number = Insurance.getString("POLICY_ACCOUNT_NUMBER");
                Policy_Group_Number = Insurance.getString("POLICY_GROUP_NUMBER");
                Amount_Paid_By_Insurance = Insurance.getString("AMOUNT_PAID_BY_INSURANCE");
            }

            while (location.next()) {
                Facility = location.getString("FACILITY");
                Floor = location.getString("FLOOR");
                room = location.getString("ROOM_NUMBER");
                floorNumber = location.getString("FLOOR");
                BedNumber = location.getString("BED_NUMBER");
                DischargeDate = location.getString("DISCHARGED_DATE");
                DischargeTime = location.getString("DISCHARGED_TIME");
            }
            while (contact.next()) {
                homePhone = contact.getString("HOME_PHONE");
                workPhone = contact.getString("WORK_PHONE");
                cellPhone = contact.getString("CELL_PHONE");
                EmergencyName1 = contact.getString("EMERGENCY_CONTACT_NAME_01");
                EmergencyNumber1 = contact.getString("EMERGENCY_CONTACT_NUMBER_01");
                EmergencyName2 = contact.getString("EMERGENCY_CONTACT_NAME_02");
                EmergencyNumber2 = contact.getString("EMERGENCY_CONTACT_NUMBER_02");
            }

            request.setAttribute("v_display_name", v_found3);
            request.setAttribute("v_display_city", city);
            request.setAttribute("v_display_room", room);
            request.setAttribute("v_display_Street", street);
            request.setAttribute("v_display_Zip", zip);
            request.setAttribute("v_display_State", state);
            request.setAttribute("v_display_HomePhone", homePhone);
            request.setAttribute("v_display_WorkPhone", workPhone);
            request.setAttribute("v_display_CellPhone", cellPhone);
            request.setAttribute("v_display_EmergContName1", EmergencyName1);
            request.setAttribute("v_display_EmergContNum1", EmergencyNumber1);
            request.setAttribute("v_display_EmergContName2", EmergencyName2);
            request.setAttribute("v_display_EmergContNum2", EmergencyNumber2);
            request.setAttribute("v_display_AdmittanceDate", AdmittanceDate);
            request.setAttribute("v_display_AdmittanceTime", AdmittanceTime);
            request.setAttribute("v_display_FamilyDoctor", FamilyDoctor);
            request.setAttribute("v_display_Facility", Facility);
            request.setAttribute("v_display_Floor", Floor);
            request.setAttribute("v_display_BedNumber", BedNumber);
            request.setAttribute("v_display_DischargeDate", DischargeDate);
            request.setAttribute("v_display_DischargeTime", DischargeTime);
            request.setAttribute("v_display_InsuranceCarrier", Carrier);
            request.setAttribute("v_display_PolicyAccountNumber", Policy_Account_Number);
            request.setAttribute("v_display_PolicyGroupNumber", Policy_Group_Number);
            request.setAttribute("v_display_AmountPaidByInsurance", Amount_Paid_By_Insurance);
            request.setAttribute("v_display_PerscriptionName", Prescription_Name);
            request.setAttribute("v_display_PerscriptionSchedule", Prescription_Schedule);
            request.setAttribute("v_display_PerscriptionAmount", Prescription_Amount);
            request.setAttribute("v_display_AdmissionReason", Reason_For_Admission);
            request.setAttribute("v_display_DoctorsTreatmentNotes", Doctors_Treatment_Notes);
            request.setAttribute("v_display_NursesTreatmentNotes", Nurses_Treatment_Notes);
            request.setAttribute("v_display_Scheduled_Procedure", Scheduled_Procedure);                     

        }
        request.setAttribute("vol_info", patients3);
        
        
        return true;
    }
    
    private boolean workOffice(HttpServletRequest request,  HttpServletResponse response) throws Exception {
        
        db = new DBConnect();
        db.connect();
        
        String n_room = request.getParameter("new_RoomNumber");
        String n_City = request.getParameter("new_City");
        String n_Street = request.getParameter("new_Street");
        String n_Zip = request.getParameter("new_Zip");
        String n_State = request.getParameter("new_State");
        String n_HomePhone = request.getParameter("New_HomePhone");
        String n_CellPhone = request.getParameter("New_CellPhone");
        String n_workPhone = request.getParameter("New_WorkPhone");
        String n_emgContName1 = request.getParameter("New_EmergContName1");
        String n_emgContName2 = request.getParameter("New_EmergContName2");
        String n_emgContNum1 = request.getParameter("New_EmergContNum1");
        String n_emgContNum2 = request.getParameter("New_EmergContNum2");

        String n_AdmittanceDate = request.getParameter("New_AdmittanceDate");
        String n_AddmittanceTime = request.getParameter("New_AdmittanceTime");
        String n_DischargeDate = request.getParameter("New_DischargeDate");
        String n_DischargeTime = request.getParameter("New_DischargeTime");
        String n_FamilyDoctor = request.getParameter("New_FamilyDoctor");
        String n_Facility = request.getParameter("New_Facility");
        String n_Floor = request.getParameter("New_Floor");
        String n_RoomNumber = request.getParameter("new_RoomNumber");
        String n_BedNumber = request.getParameter("New_BedNumber");
        String n_InsuranceCarrier = request.getParameter("New_InsuranceCarrier");
        String n_PolicyAccountNumber = request.getParameter("New_PolicyAccountNumber");
        String n_PolicyGroupNumber = request.getParameter("New_PolicyGroupNumber");
        String n_AmountPaidByInsurance = request.getParameter("New_AmountPaidByInsurance");

        String off_display = request.getParameter("v_display_name");
        String v_first2 = request.getParameter("v_first_name");
        String v_last2 = request.getParameter("v_last_name");

        if (!n_AdmittanceDate.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeAdmittanceDate = myPatient.changeAdmittanceDate(dbPatient.getId(), n_AdmittanceDate);
        }

        if (!n_AddmittanceTime.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeAddmittanceTime = myPatient.changeAddmittanceTime(dbPatient.getId(), n_AddmittanceTime);
        }

        if (!n_DischargeDate.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeDischargeDate = myPatient.changeDischargeDate(dbPatient.getId(), n_DischargeTime);
        }

        if (!n_DischargeTime.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeDischargeTime = myPatient.changeDischargeTime(dbPatient.getId(), n_DischargeTime);
        }

        if (!n_FamilyDoctor.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeFamilyDoctor = myPatient.changeFamilyDoctor(dbPatient.getId(), n_FamilyDoctor);
        }

        if (!n_Facility.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeFacility = myPatient.changeFacility(dbPatient.getId(), n_Facility);
        }

        if (!n_RoomNumber.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeRoomNumber = myPatient.changeBedNumber(dbPatient.getId(), n_RoomNumber);
        }

        if (!n_Floor.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeFloor = myPatient.changeFloor(dbPatient.getId(), n_Floor);
        }

        if (!n_BedNumber.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeBedNumber = myPatient.changeBedNumber(dbPatient.getId(), n_BedNumber);
        }

        if (!n_InsuranceCarrier.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeInsuranceCarrier = myPatient.changeInsuranceCarrier(dbPatient.getId(), n_InsuranceCarrier);
        }

        if (!n_PolicyAccountNumber.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changePolicyAccountNumber = myPatient.changePolicyAccountNumber(dbPatient.getId(), n_PolicyAccountNumber);
        }

        if (!n_PolicyGroupNumber.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changePolicyGroupNumber = myPatient.changePolicyGroupNumber(dbPatient.getId(), n_PolicyGroupNumber);
        }
        if (!n_AmountPaidByInsurance.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changenAmountPaidByInsurance = myPatient.changeAmountPaidByInsurance(dbPatient.getId(), n_AmountPaidByInsurance);
        }

        if (!n_emgContNum2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeemgContNum2 = myPatient.changeEmgContNum2(dbPatient.getId(), n_emgContNum2);
        }

        if (!n_emgContNum1.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeemgContNum1 = myPatient.changeEmgContNum1(dbPatient.getId(), n_emgContNum1);
        }

        if (!n_emgContName2.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeEmgContName2 = myPatient.changeEmgContName2(dbPatient.getId(), n_emgContName2);
        }

        if (!n_emgContName1.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeEmgContName1 = myPatient.changeEmgContName1(dbPatient.getId(), n_emgContName1);
        }

        if (!n_workPhone.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeWorkPhone = myPatient.changeWorkPhone(dbPatient.getId(), n_workPhone);
        }

        if (!n_CellPhone.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeCellPhone = myPatient.changeCellPhone(dbPatient.getId(), n_CellPhone);
        }

        if (!n_HomePhone.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_HomePhone + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeHomePhone = myPatient.changeHomePhone(dbPatient.getId(), n_HomePhone);
        }

        if (!n_room.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_room + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeRoom = myPatient.changeRoom(dbPatient.getId(), n_room);
        }
        if (!n_City.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_room + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeCity = myPatient.changeCity(dbPatient.getId(), n_City);
        }

        if (!n_Street.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_room + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeStreet = myPatient.changeStreet(dbPatient.getId(), n_Street);
        }
        if (!n_Zip.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_room + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeZip = myPatient.changeZip(dbPatient.getId(), n_Zip);
            System.out.println("1024_CtrServlet_Zip: "+changeZip);
        }
        if (!n_State.isEmpty()) {
            Patient myPatient = new Patient(db.getConn());
            String[] array = off_display.split(" ");
            System.out.println(n_room + " :: " + array[0] + " :: " + array[1]);
            Patient dbPatient = myPatient.getPatient(array[0], array[1]);
            System.out.println(dbPatient.getDisplayName());
            boolean changeState = myPatient.changeState(dbPatient.getId(), n_State);
        }

        
        String v_found2 = request.getParameter("v_display_name");
        List<Patient> patients2 = new ArrayList();
        Patient patient2 = new Patient(db.getConn());
        ResultSet allPatients2 = null;
       
        if (v_found2.isEmpty()) {
            //get all patients by first name
            if (!v_first2.isEmpty()) {
                allPatients2 = patient2.getVolunteerFirst(v_first2);
            }
            //get all patients by last name
            if (!v_last2.isEmpty()) {
                allPatients2 = patient2.getVolunteerLast(v_last2);
            }
            
            //Now that I have patients, populate the table
            while (allPatients2.next()) {
                Patient p = new Patient();
                String first = allPatients2.getString("FIRST_NAME");
                String last = allPatients2.getString("LAST_NAME");
                String street = allPatients2.getString("STREET");
                String id = allPatients2.getString("ID");
                p.setDisplayName(first + " " + last);
                p.setStreet(street);                
                patients2.add(p);
            }
            request.setAttribute("patients", patients2);
            
        } else {                        
            Patient patient3 = new Patient(db.getConn());
            String[] s_array = off_display.split(" ");
            Patient dbPatient2 = patient3.getPatient(s_array[0], s_array[1]);//get patient but not stats
            
            ResultSet location = patient3.getLocation(dbPatient2);
            ResultSet contact = patient3.getContact(dbPatient2);
            ResultSet treatment = patient3.getTreatment(dbPatient2);
            ResultSet Insurance = patient3.getInsurance(dbPatient2);

            String AdmittanceDate = "";
            String AdmittanceTime = "";
            String DischargeDate = "";
            String DischargeTime = "";

            String FamilyDoctor = "";
            String homePhone = "";
            String workPhone = "";
            String cellPhone = "";
            String EmergencyName1 = "";
            String EmergencyNumber1 = "";
            String EmergencyName2 = "";
            String EmergencyNumber2 = "";
            String Facility = "";
            String Floor = "";
            String room = "";
            String BedNumber = "";
            String city = "";
            String floorNumber = "";

            String Carrier = "";
            String Policy_Account_Number = "";
            String Policy_Group_Number = "";
            String Amount_Paid_By_Insurance = "";

            String street = dbPatient2.getStreet();            
            String zip = dbPatient2.getZip();
            System.out.println("Line1101_CtrServlet_ZIP: "+zip);
            String state = dbPatient2.getState();

            city = dbPatient2.getCity();

            while (treatment.next()) {
                AdmittanceDate = treatment.getString("ADMITTANCE_DATE");
                AdmittanceTime = treatment.getString("ADMITTANCE_TIME");
                FamilyDoctor = treatment.getString("FAMILY_DOCTOR");
            }
            while (Insurance.next()) {
                Carrier = Insurance.getString("CARRIER");
                Policy_Account_Number = Insurance.getString("POLICY_ACCOUNT_NUMBER");
                Policy_Group_Number = Insurance.getString("POLICY_GROUP_NUMBER");
                Amount_Paid_By_Insurance = Insurance.getString("AMOUNT_PAID_BY_INSURANCE");
            }

            while (location.next()) {
                Facility = location.getString("FACILITY");
                Floor = location.getString("FLOOR");
                room = location.getString("ROOM_NUMBER");
                floorNumber = location.getString("FLOOR");
                BedNumber = location.getString("BED_NUMBER");
                DischargeDate = location.getString("DISCHARGED_DATE");
                DischargeTime = location.getString("DISCHARGED_TIME");
            }
            while (contact.next()) {
                homePhone = contact.getString("HOME_PHONE");
                workPhone = contact.getString("WORK_PHONE");
                cellPhone = contact.getString("CELL_PHONE");
                EmergencyName1 = contact.getString("EMERGENCY_CONTACT_NAME_01");
                EmergencyNumber1 = contact.getString("EMERGENCY_CONTACT_NUMBER_01");
                EmergencyName2 = contact.getString("EMERGENCY_CONTACT_NAME_02");
                EmergencyNumber2 = contact.getString("EMERGENCY_CONTACT_NUMBER_02");
            }

            request.setAttribute("v_display_name", v_found2);
            request.setAttribute("v_display_city", city);
            request.setAttribute("v_display_room", room);
            request.setAttribute("v_display_Street", street);
            request.setAttribute("v_display_Zip", zip);
            request.setAttribute("v_display_State", state);
            request.setAttribute("v_display_HomePhone", homePhone);
            request.setAttribute("v_display_WorkPhone", workPhone);
            request.setAttribute("v_display_CellPhone", cellPhone);
            request.setAttribute("v_display_EmergContName1", EmergencyName1);
            request.setAttribute("v_display_EmergContNum1", EmergencyNumber1);
            request.setAttribute("v_display_EmergContName2", EmergencyName2);
            request.setAttribute("v_display_EmergContNum2", EmergencyNumber2);
            request.setAttribute("v_display_AdmittanceDate", AdmittanceDate);
            request.setAttribute("v_display_AdmittanceTime", AdmittanceTime);
            request.setAttribute("v_display_FamilyDoctor", FamilyDoctor);
            request.setAttribute("v_display_Facility", Facility);
            request.setAttribute("v_display_Floor", floorNumber);
            request.setAttribute("v_display_BedNumber", BedNumber);
            request.setAttribute("v_display_DischargeDate", DischargeDate);
            request.setAttribute("v_display_DischargeTime", DischargeTime);

            request.setAttribute("v_display_InsuranceCarrier", Carrier);
            request.setAttribute("v_display_PolicyAccountNumber", Policy_Account_Number);
            request.setAttribute("v_display_PolicyGroupNumber", Policy_Group_Number);
            request.setAttribute("v_display_AmountPaidByInsurance", Amount_Paid_By_Insurance);

            //v_display_AdmittanceDate
            
        }
        return true;
    }
    
    
}
