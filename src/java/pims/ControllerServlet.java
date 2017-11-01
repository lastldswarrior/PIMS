package pims;

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
        String currentUser = request.getParameter("username");
        System.out.println("Top: "+currentUser);
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
                        //request.setAttribute("user", currentUser);
                        request.getRequestDispatcher("volunteerpanel.jsp").forward(request, response);
                        break;
                    case "Office": 
                        
                        request.getRequestDispatcher("officepanel.jsp").forward(request, response);                        
                        break;
                    case "Nurse":                         
                        request.setAttribute("user", currentUser);
                        request.getRequestDispatcher("nursepanel.jsp").forward(request, response);
                        break; 
                    case "Doctor":                         
                        request.setAttribute("user", currentUser);
                        request.getRequestDispatcher("doctorpanel.jsp").forward(request, response);
                        break;    
                    default:
                        request.setAttribute("pass", levelOfAccess);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                }
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
                    boolean changed = modifyUser.addUser(new_name, new_pass,new_access,"myEmail@uah.edu");
                    
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
                List<User> users2 = new ArrayList();
                db = new DBConnect();
                db.connect();

                User appUsers = new User(db.getConn());
                ResultSet allUsers = appUsers.getAllUsers();
                while (allUsers.next()) {
                    User one = new User();
                    one.setUserName(allUsers.getString("USER_NAME"));
                    one.setLevel(allUsers.getString("LEVEL"));
                    users2.add(one);
                }

                request.setAttribute("doctorCount", "8000");
                request.setAttribute("users", users2);
                request.getRequestDispatcher("userpanel.jsp").forward(request, response);

                break;
            case "volunteerpanel.jsp":
                currentUser = request.getParameter("user");
                
                request.setAttribute("user", currentUser);
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
                    String visitors = null;
                    while (location.next()) {
                        facility = location.getString("FACILITY");
                        floor = location.getString("FLOOR");
                        room = location.getString("ROOM_NUMBER");
                        bed = location.getString("BED_NUMBER");
                        count = location.getString("VISITOR_COUNT");
                        visitors = location.getString("VISITORS");
                        
                    }
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
                workOffice(request);
                request.getRequestDispatcher("officepanel.jsp").forward(request, response);
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private boolean workOffice(HttpServletRequest request) throws Exception {
        
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
            if (!v_first2.isEmpty()) {
                allPatients2 = patient2.getVolunteerFirst(v_first2);
            }
            if (!v_last2.isEmpty()) {
                allPatients2 = patient2.getVolunteerLast(v_last2);
            }
            while (allPatients2.next()) {
                Patient p = new Patient();
                String first = allPatients2.getString("FIRST_NAME");
                String last = allPatients2.getString("LAST_NAME");
                String street = allPatients2.getString("STREET");
                String id = allPatients2.getString("ID");
                p.setDisplayName(first + " " + last);
                p.setStreet(street);
                //   p.setID(id);
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
            System.out.println("Street: "+street);
            String zip = dbPatient2.getZip();
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
            request.setAttribute("v_display_Floor", Floor);
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
