<%-- 
    Document   : volunteerResult
    Created on : Sep 19, 2017, 3:55:22 PM
    Author     : Jared &zwnj;
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Volunteer Result</title>
        <style>
            body, html {
                height: 100%;
                margin: 0;
            }

            .bg {

                background-image: url("vol.jpg");

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
                    <a href="volunteerpanel.jsp" class="w3-bar-item w3-button"><i class="fa fa-search"></i> SEARCH</a>
                    <a class="w3-bar-item w3-button" 
                       onclick='javascript:window.open("help.pdf", "_doc", "scrollbars=1,resizable=1,height=500,width=750,centerscreen");'
                       title='Pop Up'><i class="fa fa-info-circle"></i> HELP</a> 
                    <a href="index.jsp" class="w3-bar-item w3-button"><i class="fa fa-power-off"></i> SIGN OUT</a>                    
                </div>
                <!-- Hide right-floated links on small screens and replace them with a menu icon -->

                <a href="javascript:void(0)" class="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium" onclick="w3_open()">
                    <i class="fa fa-bars"></i>
                </a>
            </div>
        </div>
        
        
        <div class="bg">
            <form name="vResult" id="vResult" action="ControllerServlet" method="post">
                <br><br><br><br>
                <div class="w3-row-padding">
                    <!-- Empty space for design look and feel -->
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    
                    <!-- Patient Information for Volunteer to use for Visitors -->
                    <div class="w3-col s3 w3-white">
                        <br>
                        <label class="w3-text-blue"><b>First Name</b></label>
                        <input class="w3-input w3-border w3-round-large" type="text" value="${v_first_name}">

                        <label class="w3-text-blue"><b>Last Name</b></label>
                        <input class="w3-input w3-border w3-round-large" type="text" value="${v_last_name}">

                        <label class="w3-text-blue"><b>Number of Visitors</b></label>
                        <input class="w3-input w3-border w3-round-large" type="text" value="${v_count}">
                        
                        <br>
                    </div>
                        
                    <!-- Empty space for design look and feel -->    
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    
                    <!-- List of Approved Visitors Box -->
                    <div class="w3-col s3 w3-white">
                        <br>                        
                        <label class="w3-text-blue"><b>List of Approved Visitors</b></label>
                        <ul class="w3-ul w3-hoverable">
                            <c:forEach var="visitor" items="${v_list}" >
                                <li>${visitor}</li>                            
                            </c:forEach>
                        </ul>                        
                        <br>
                    </div>
                    
                    <!-- Empty space for design look and feel -->
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    
                    <!-- Location info on Patient -->
                    <div class="w3-col s2 w3-white">
                        <div class="w3-row w3-section">
                                <div class="w3-rest">
                                    <label class="w3-text-blue"><b>Facility</b></label>
                                    <input class="w3-input w3-round-large w3-large w3-border"
                                           name="facility" type="text" value="${v_facility}">
                                </div>
                            </div>
                            <div class="w3-row w3-section">
                                <div class="w3-rest">
                                    <label class="w3-text-blue"><b>Floor</b></label>
                                    <input class="w3-input w3-round-large w3-large w3-border"
                                           name="floor" type="text" value="${v_floor}">
                                </div>
                            </div>    
                            <div class="w3-row w3-section">
                                <div class="w3-rest">
                                    <label class="w3-text-blue"><b>Room Number</b></label>
                                    <input class="w3-input w3-round-large w3-large w3-border"
                                           name="room" id="temp" type="text" value="${v_room}">
                                </div>
                            </div>
                            <div class="w3-row w3-section">
                                <div class="w3-rest">
                                    <label class="w3-text-blue"><b>Bed Number</b></label>
                                    <input class="w3-input w3-round-large w3-large w3-border"
                                           name="bed" type="text" value="${v_bed}">
                                </div>
                            </div>
                        </div>
                                
                    <!-- Empty space for design look and feel -->            
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    
                </div>
            </form>    
        </div>        
    </body>
</html>
