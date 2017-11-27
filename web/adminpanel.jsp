<%-- 
    Document   : AdminPanel
    Created on : Sep 6, 2017, 5:02:10 PM
    Author     : Jared

--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
        <title>Admin Panel</title>
        <style>
            body, html {
                height: 100%;
                margin: 0;
                background-color: black;
            }

            .bg {
                background-image: url("admin.jpg");                
                height: 100%; 
                background-position: center;
                background-repeat: no-repeat;
                background-size: 100% 100%;
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
    </head>
    
    <body>
        <form action="ControllerServlet" method="post">
        <input type="hidden" name="page" value='databasepanel.jsp' /></td><%-- This one works --%>  
        
        <%--<input type="hidden" name="myuser" value='${user}'/></td><%-- This one works --%> 
        <!-- Navbar (sit on top) -->
        <div class="w3-top">
            <div class="w3-bar w3-black w3-card-2" id="myNavbar">
                <div class="w3-bar-item w3-wide"><i class="fa fa-h-square"></i> PIMS</div>
                <div class="w3-bar-item "><i class="fa fa-bullhorn"></i> Announcement: ${announcement}</div>
                <!-- Right-sided navbar links -->
                <div class="w3-right w3-hide-small">                    
                    <button class="w3-bar-item w3-button w3-block fa-database" style="font-family:Arial, FontAwesome" type="submit">
                        DB Management                 
                    </button>
                    <a class="w3-bar-item w3-button" 
                       onclick='javascript:window.open("help.pdf", "_doc", "scrollbars=1,resizable=1,height=500,width=750,centerscreen");'
                       title='Pop Up'><i class="fa fa-info-circle"></i> HELP</a>   
                    <a href="index.jsp" class="w3-bar-item w3-button"><i class="fa fa-arrow-left"></i> BACK TO LOGIN</a>                    
                </div>                
            </div>
        </div>
        </form>            
        <!-- !PAGE CONTENT! -->            
        <div class="bg">
            <form action="ControllerServlet" method="post">
            <input type="hidden" name="page" value='adminpanel.jsp' /><%-- This one works &zwnj;--%>    
            <div class="w3-row-padding" style='margin-left: 80px;'>
                <br><br>         
                    
                    <div class="w3-col s3 w3-opacity-min w3-white w3-round-large">                    
                        <table>
                            <tr>
                                <div class="w3-container w3-center">
                                    <h2>Change User Password</h2>
                                </div>
                            </tr>
                            <tr>
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="username" type="text" placeholder="&#xf007; User Name">
                            </tr>
                            <br>
                            <tr>
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="a_pass" type="password" placeholder="&#xf0e0; New Password" >
                            </tr>
                            <br>
                            <tr>
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-blue" 
                                    style="font-family:Arial, FontAwesome" type="submit">Change Password                 
                            </button>
                            </tr>
                        </table>   
                    </div>
                
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    <div class="w3-col s3 w3-opacity-min w3-white w3-round-large">                    
                        <table>
                            <tr>
                                <div class="w3-container w3-center">
                                    <h2>Add New User</h2>
                                </div>
                            </tr>
                            <table>                                
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="newusername" type="text" placeholder="&#xf007; User Name">
                                </tr>
                                <br>
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="email" type="text" placeholder="&#xf0e0; Email">
                                </tr>
                                <br>
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="newpass" type="password" placeholder="&#xf023; New Password">
                                </tr>
                                <br>
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="newpass2" type="password" placeholder="&#xf023; Confirm Password">
                                </tr>
                                <br>
                                <tr>
                                <select class="w3-select w3-round-large w3-large w3-border" name="level">
                                    <option value="" disabled selected>Select Level of Access</option>
                                    <option value="Volunteer">Volunteer</option>
                                    <option value="Office">Office Staff</option>
                                    <option value="Nurse">Medical Professional</option>
                                    <option value="Doctor">Doctor</option>
                                </select>
                                <br>
                                <br>
                                <tr>
                                <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-green" 
                                        style="font-family:Arial, FontAwesome"type="submit">Add User                   
                                </button>
                                </tr>
                            </table>                             
                    </div>
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    <div class="w3-col s3 w3-opacity-min w3-white w3-round-large">                    
                        <table>
                            <tr>
                                <div class="w3-container w3-center">
                                    <h2>Change User Access</h2>
                                </div>
                            </tr>
                            <tr>
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="access_username" type="text" placeholder="&#xf007; User Name">
                            </tr>
                            <br>
                            <tr>
                            <select class="w3-select w3-round-large w3-large w3-border" name="access">
                                    <option value="" disabled selected>Select Level of Access</option>
                                    <option value="Volunteer">Volunteer</option>
                                    <option value="Office">Office Staff</option>
                                    <option value="Nurse">Medical Professional</option>
                                    <option value="Doctor">Doctor</option>
                                </select>
                            <br><br>
                            <tr>
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-red" 
                                    style="font-family:Arial, FontAwesome" type="submit">Change Access                 
                            </button>
                            </tr>
                        </table>
                    </div>
                
            </form>
        
        </div>
    </body>
</html>
