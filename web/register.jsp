<%-- 
    Document   : register
    Created on : Oct 18, 2017, 6:41:59 PM
    Author     : Jared
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
            body, html {
                height: 100%;
                margin: 0;
            }

            .bg {

                background-image: url("tmp.jpg");

                height: 100%; 

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
    </head>
    <body>
        <!-- Navbar (sit on top) -->
        <div class="w3-top">
            <div class="w3-bar w3-white w3-card-2" id="myNavbar">
                <div class="w3-bar-item w3-wide"><i class="fa fa-h-square"></i> PIMS</div>
                <!-- Right-sided navbar links -->
                <div class="w3-right w3-hide-small">
                    <a class="w3-bar-item w3-button" 
                       onclick='javascript:window.open("help.pdf", "_doc", "scrollbars=1,resizable=1,height=500,width=750,centerscreen");'
                       title='Pop Up'><i class="fa fa-info-circle"></i> HELP</a> 
                    <a href="index.jsp" class="w3-bar-item w3-button"><i class="fa fa-arrow-left"></i> BACK TO LOGIN</a>                    
                </div>
                <!-- Hide right-floated links on small screens and replace them with a menu icon -->

                <a href="javascript:void(0)" class="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium" onclick="w3_open()">
                    <i class="fa fa-bars"></i>
                </a>
            </div>
        </div>
        <title>Register</title>
        <div class="bg">
            <form action="ControllerServlet" method="post">
                <div class="w3-container" style="height: 150px;">&zwnj;</div>
                <div class="w3-row">                    
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    <div class="w3-col s4 w3-green w3-center w3-round-large">
                        <div class="w3-opacity-min w3-black">
                            <h1>New User Registration</h1>
                            <h3>Because who wouldn't want to sign up for this?</h3>
                        </div>  
                    </div>
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    <div class="w3-col s4 w3-green w3-center w3-round-large">
                        <div class="w3-opacity-min w3-black">
                            <h1>Register Yourself</h1>
                            <table>                                
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="rname" type="text" placeholder="&#xf007; User Name">
                                </tr>
                                <br>
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="email" type="text" placeholder="&#xf0e0; Email">
                                </tr>
                                <br>
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="userpass" type="password" placeholder="&#xf023; New Password">
                                </tr>
                                <br>
                                <tr>
                                <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                       name="userpass2" type="password" placeholder="&#xf023; Confirm Password">
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
                                        style="font-family:Arial, FontAwesome"type="submit">Sign Up                   
                                </button>
                                </tr>
                            </table>   
                            <input type="hidden" name="page" value='register.jsp' /><%-- This one works --%>
                        </div>                    
                    </div>  
                </div> 
            </form>
        </div>
    </body>
</html>

