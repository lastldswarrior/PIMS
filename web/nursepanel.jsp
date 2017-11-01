<%-- 
    Document   : Doctor
    Created on : Sep 9, 2017, 7:10:02 PM
    Author     : Tanner
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Medical Professional Panel</title>
        <style>
            body, html {
                height: 100%;
                margin: 0;
            }

            .bg {

                background-image: url("loginScreen.jpg");

                height: 60%; 

                background-position: center;
                background-repeat: no-repeat;
                background-size: cover;
            }
        </style>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
        </style>
        
        <script>
            function CollapseFunction()
            {
                if(document.getElementById('collapsible').style.display == 'none')
                {
                document.getElementById('collapsible').style.display = 'block';
                document.getElementById('maincontainer').style.height = '60%';
                document.getElementById('fieldContainer').style.height = '490px';
                document.getElementById('fieldContainer2').style.height = '490px'
                document.getElementById('linktext').innerHTML = "Patients >";
                
                
                }
                else
                {
                document.getElementById('collapsible').style.display = 'none';
                document.getElementById('maincontainer').style.height = '96%';
                document.getElementById('fieldContainer').style.height = '825px';
                document.getElementById('fieldContainer2').style.height = '825px';
                document.getElementById('linktext').innerHTML = "Patients ^";
                }
            }
            function additionalInfo()
            {
                document.getElementById('trcity').style.display = 'inline-block';
            }
            
            function DrNoteWindow()
            {
                alert(" --- Doctor's Treatment Notes ---\n" + document.getElementById("DoctorsTreatmentNotes").value);
            }
            function NurseNotes()
            {
                alert(" --- Nurse Notes ---\n" + document.getElementById("nurseNotes").value);
            }
            </script>
        
    </head>
    <body>
        <div class="bg" id='maincontainer'>
            <a href="adminpanel.jsp" class="w3-btn w3-blue w3-round-xlarge">Return to Dashboard</a>
           <form name="Volunteer" id="vPage" action="" method="post"> 
                <input type="hidden" name="page" value='Medical Professional.jsp' /><%-- This one works --%>
                <br><br>
                <div class="w3-row-padding">
                    <div class="w3-col s1 w3-center" style="width:100px">&zwnj;</div>
                    <div class="w3-col s4" style="">
                    <table style="">
                        <tr>
                            <td colspan="3">
                                <div class="w3-display-container" style="width:300px;"></div>
                                <div class="w3-round-large w3-border w3-black w3-center w3-block" style="font-family:Arial, FontAwesome;width:100%;" >
                                    <h2>Patient Search</h2>
                                </div>
                                </div>
                            </td>

                        </tr>                      
                        <tr>
                            <td colspan="2">
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="v_first_name" type="text" placeholder="&#xf007; First Name">
                            </td>
                       
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-blue" 
                                    style="font-family:Arial, FontAwesome" type="submit">Search                  
                            </button>
                        </td>   
                        </tr>
                        
                        <tr>
                            <td colspan="2">
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="v_last_name" type="text" placeholder="&#xf007; Last Name">
                            </td>
                        
                        <td>
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-red" 
                                    style="font-family:Arial, FontAwesome" type="submit">Search                  
                            </button>
                        </td>
                        </tr>
                    </table>   
                </div>
                
                
            <div class="w3-col s2 w3-center" style="width:15%;">&zwnj;></div>    
            <div class="w3-col s4 w3-center" style=" margin-left:0px; position:absolute; margin-left:500px;" >
                <div style=" height:490px;overflow-y:auto; width:520px;" id='fieldContainer'>
                <table>
                    <tr>
                        <td colspan="3">
                            <div class="w3-round-large w3-border w3-black w3-center w3-block" style="font-family:Arial, FontAwesome;width:100%">
                                <h2>Patient Found</h2>
                            </div>
                        </td> 
                    </tr>                      
                    <tr>
                        <td colspan="2">
                            
                            <!-- <button HREF= "#" class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome" onClick="window.open('admin.jsp','toolbar=no,width=190,height=190,left=500,top=200,status=no,scrollbars=no,resize=no')">Full Name:                
                            </button> -->
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" Href="#" onclick="DisplayUpdateFieldName()">Full Name:                
                            </button>
                        </td> 
                        <td>
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_name" id="temp" type="text" value="${v_display_name}">
                        </td> 
                    </tr>
                    
                   <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Reason for admission:                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_AdmissionReason" type="text" value="${v_display_AdmissionReason}" disabled>
                        </td>
                    </tr>
                  <tr>
                        <td colspan="2">
                            <button onclick="DrNoteWindow()" class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Doctor’s Treatment Notes                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_DoctorsTreatmentNotes" type="text" value="${v_display_DoctorsTreatmentNotes}" id="DoctorsTreatmentNotes" disabled>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button onclick="NurseNotes()" class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Nurse's Treatment Notes              
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_NursesTreatmentNotes" type="text" value="${v_display_NursesTreatmentNotes}" id="nurseNotes" disabled>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Scheduled Procedures              
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_Scheduled_Procedure" type="text" value="${v_display_Scheduled_Procedure}" disabled>
                        </td>
                    </tr>
                         
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Prescription Name:             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_PerscriptionName" type="text" value="${v_display_PerscriptionName}" disabled>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Prescription Schedule           
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_PerscriptionSchedule" type="text" value="${v_display_PerscriptionSchedule}" disabled>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Prescription Amount           
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_PerscriptionAmount" type="text" value="${v_display_PerscriptionAmount}" disabled>
                        </td>
                    </tr>
                   <!-- <tr style="text-align: center;" id="additionalInfoLink">
                    <td colspan="4" ><a onclick='additionalInfo()' href="#" id='linktext' style="color:white; text-decoration: Bold;">---- Additional Patient Information ----</a></td>

                </tr> --> 
                    <tr style="display: none;" id="trcity">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome;">city             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome; font-color:black;" 
                                   name="v_display_city" type="text" value="${v_display_city}" disabled>
                        </td>
                    </tr>
                    <tr style="display:none;" id="trzip">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Zip                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome; font-color:black;" 
                                   name="v_display_Zip" type="text" value="${v_display_Zip}" disabled>
                        </td>
                    </tr>
                     <tr style="display:none;" id="trstate">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">State               
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_State" type="text" value="${v_display_State}" disabled>
                        </td>
                    </tr>
                     <tr style="display:none;" id="trHomePhone">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Home Phone Number               
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_HomePhone" type="text" value="${v_display_HomePhone}" disabled>
                        </td>
                    </tr>
                     <tr style="display:none;" id="trWorkPhone">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Work Phone Number               
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_WorkPhone" type="text" value="${v_display_WorkPhone}" disabled>
                        </td>
                     <tr style="display:none;" id="trMobilePhone">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Mobile Number               
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_CellPhone" type="text" value="${v_display_CellPhone}" disabled>
                        </td>
                     <tr style="display:none;" id="trEmergContName1">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Name 1             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_EmergContName1" type="text" value="${v_display_EmergContName1}" disabled>
                        </td>
                        </tr>
                        <tr style="display:none;" id="trEmergContNum1">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Number 1             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_EmergContNum1" type="text" value="${v_display_EmergContNum1}" disabled>
                        </td>
                                            </tr>
                        <tr style="display:none;" id="trEmergContName2">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Name 2             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_EmergContName2" type="text" value="${v_display_EmergContName2}" disabled>
                        </td>
                        </tr>
                        <tr style="display:none;" id="trEmergContNum2">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Number 2             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_EmergContNum2" type="text" value="${v_display_EmergContNum2}" disabled>
                        </td>
                                            </tr>
                        <tr style="display:none;" id="trAdmittanceDate">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Admittance Date            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_AdmittanceDate" type="text" value="${v_display_AdmittanceDate}" disabled>
                        </td>
                        </tr>
                        <tr style="display:none;" id="trAdmittanceTime">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Admittance Time            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_AdmittanceTime" type="text" value="${v_display_AdmittanceTime}" disabled>
                        </td>
                                            </tr>
                        <tr style="display:none;" id="trDischargeDate">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Discharge Date            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_DischargeDate" type="text" value="${v_display_DischargeDate}" disabled>
                        </td>
                                            </tr>
                        <tr style="display:none;" id="trDischargeTime">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Discharge Time            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_DischargeTime" type="text" value="${v_display_DischargeTime}" disabled>
                        </td>
                                            </tr>
                        <tr style="display:none;" id="trFamilyDoctor">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Family Doctor         
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_FamilyDoctor" type="text" value="${v_display_FamilyDoctor}" disabled>
                        </td>
                        </tr>
                        <tr style="display:none;" id="trFacility">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Facility:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_Facility" type="text" value="${v_display_Facility}" disabled>
                        </td>
                    </tr>
                     <tr style="display:none;" id="trFloor">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Floor                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_Floor" type="text" value="${v_display_Floor}" disabled>
                        </td>
                    </tr>
                     <tr style="display:none;" id="trroom">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Room Number:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_room" type="text" value="${v_display_room}" disabled>
                        </td>
                    </tr>
                    <tr style="display:none;" id="trBedNumber">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Bed Number:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_BedNumber" type="text" value="${v_display_BedNumber}" disabled>
                        </td>
                    </tr>
                    <tr style="display:none;" id="trInsuranceCarrier">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Insurance Carrier                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_InsuranceCarrier" type="text" value="${v_display_InsuranceCarrier}" disabled>
                        </td>
                    </tr>
                    <tr style="display:none;" id="trPolicyAccountNumber">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Policy Account Number:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_PolicyAccountNumber" type="text" value="${v_display_PolicyAccountNumber}" disabled>
                        </td>
                    </tr>
                    <tr style="display:none;" id="trPolicyGroupNumber">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Policy Group Number:               
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_PolicyGroupNumber" type="text" value="${v_display_PolicyGroupNumber}" disabled>
                        </td>
                    </tr>
                    <tr style="display:none;" id="trAmountPaidByInsurance">
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Amount Paid By Insurance               
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_display_AmountPaidByInsurance" type="text" value="${v_display_AmountPaidByInsurance}" disabled>
                        </td>
                    </tr>
                </table>
                </div>
            </div>
          
<!--       ********************************Update Panel********************************************************************8 -->
                        
                        
                        <div class="w3-col s2 w3-center" style="width:15%">&zwnj;></div>    
            <div class="w3-col s4 w3-center" style="margin-left:0px; position:absolute; margin-left:1100px;">
                <div style=" height:490px;overflow-y:auto; width:520px;" id='fieldContainer2'>
                <table>
                    <tr>
                        <td colspan="3">
                            <div class="w3-round-large w3-border w3-black w3-center w3-block" style="font-family:Arial, FontAwesome;width:100%">
                                <h2>Update Patient Info</h2>
                            </div>
                        </td> 
                    </tr>                      
                    
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Reason for admission:                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_AdmissionReason" type="text" value="${new_AdmissionReason}" >
                        </td>
                    </tr>
                  <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome;">Nurse Treatment Notes                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_NurseTreatmentNotes" type="text" value="${new_NurseTreatmentNotes}">
                        </td>
                    </tr>
                    
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Scheduled Procedures              
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_Scheduled_Procedures" type="text" value="${new_Scheduled_Procedures}">
                        </td>
                    </tr>
                         
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Prescription Name:             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_PerscriptionName" type="text" value="${new_PerscriptionName}" >
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Prescription Schedule           
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_PerscriptionSchedule" type="text" value="${new_PerscriptionSchedule}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Prescription Amount           
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_PerscriptionAmount" type="text" value="${new_PerscriptionAmount}">
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">City:                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_City" type="text" value="${new_City}">
                        </td>
                      </tr>
                      
                      
                      <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Street                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_Street" type="text" value="${new_Street}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Zip                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_Zip" type="text" value="${new_Zip}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">State                
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_State" type="text" value="${new_State}">
                        </td>
                    </tr>
                     
                     <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Home Phone Number               
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_HomePhone" type="text" value="${New_HomePhone}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Work Phone Number               
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_WorkPhone" type="text" value="${New_WorkPhone}">
                        </td>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Mobile Number               
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_CellPhone" type="text" value="${New_CellPhone}">
                        </td>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Name 1             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_EmergContName1" type="text" value="${New_EmergContName1}">
                        </td>
                                            </tr>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Number 1             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_EmergContNum1" type="text" value="${New_EmergContNum1}">
                        </td>
                                            </tr>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Name 2             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_EmergContName2" type="text" value="${New_EmergContName2}">
                        </td>
                                            </tr>
                                             <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Emergency Contact Number 2             
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_EmergContNum2" type="text" value="${New_EmergContNum2}">
                        </td>
                                            </tr>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Admittance Date            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_AdmittanceDate" type="text" value="${New_AdmittanceDate}">
                        </td>
                                            </tr>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Admittance Time            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_AdmittanceTime" type="text" value="${New_AdmittanceTime}">
                        </td>
                                            </tr>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Discharge Date            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_DischargeDate" type="text" value="${New_DischargeDate}">
                        </td>
                                            </tr>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Discharge Time            
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_DischargeTime" type="text" value="${New_DischargeTime}">
                        </td>
                                            </tr>
                                            <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Family Doctor         
                            </button>
                        </td>
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_FamilyDoctor" type="text" value="${New_FamilyDoctor}">
                        </td>
                                            </tr>
                         <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Facility:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_Facility" type="text" value="${New_Facility}">
                        </td>
                    </tr>
                     <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Floor                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_Floor" type="text" value="${New_Floor}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Room Number:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="new_RoomNumber" type="text" value="${new_RoomNumber}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Bed Number:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_BedNumber" type="text" value="${New_BedNumber}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Insurance Carrier                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_InsuranceCarrier" type="text" value="${New_InsuranceCarrier}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Policy Account Number:                
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_PolicyAccountNumber" type="text" value="${New_PolicyAccountNumber}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Policy Group Number:               
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_PolicyGroupNumber" type="text" value="${New_PolicyGroupNumber}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome">Amount Paid By Insurance               
                            </button>
                        </td> 
                        <td colspan="2">
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="New_AmountPaidByInsurance" type="text" value="${New_AmountPaidByInsurance}">
                        </td>
                    </tr>
                    <tr>
                      -->
                        <td colspan="3">
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                    style="font-family:Arial, FontAwesome; text-decoration: underline;">Submit Changes!              
                            </button>
                        </td>
                     </tr>
                  
                    
                </table>
                </div>
            </div>
            <div class="w3-col s1 w3-center"></div>            
                        </div>
                        </div>
        </div>
        


        <div class="w3-container" id='CollapsableContainer' style='background-color:black; padding:0px;'>
            <input type="hidden" name="selected" value="none">
            <table class="w3-table w3-bordered w3-border w3-black">
                <tr>
                    <td><a onclick='CollapseFunction()' href="#" id='linktext'>Patients ∨ </a></td>

                </tr>   
            </table>                  
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
            <table class="w3-table-all w3-striped w3-bordered w3-border w3-hoverable w3-white" id='collapsible' style='width:100%;'>
                <c:forEach items="${vol_info}" var="pa">
                    <tr id='trrow'>
                        
                        <td class="myrow" id="${pa.displayName}"><c:out value="${pa.displayName}"/></td>

                    </tr>
                </c:forEach>
            </table>

        </div>
        </form>
        <script>
            $('.myrow').click(function () {                
                var chosenPatient = $(this).attr('id');                
                document.getElementById("temp").value=chosenPatient;
                document.getElementById("vPage").submit();
            })
        </script>
</body>
</html>