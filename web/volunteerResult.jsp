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

                background-image: url("loginScreen.jpg");

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
        <!-- Sidebar/menu -->
        <nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
            <div class="w3-container w3-row">
                <div class="w3-col s4">
                    <img src="/w3images/avatar2.png" class="w3-circle w3-margin-right" style="width:46px">
                </div>
                <div class="w3-col s8 w3-bar">
                    <span>Welcome, <strong> ${user}</strong></span><br>
                    <a href="#" class="w3-bar-item w3-button"><i class="fa fa-envelope"></i></a>
                    <a href="#" class="w3-bar-item w3-button"><i class="fa fa-user"></i></a>
                    <a href="#" class="w3-bar-item w3-button"><i class="fa fa-cog"></i></a>
                </div>
            </div>
            <hr>
            <div class="w3-container">
                <h5>Dashboard</h5>
            </div>
            <div class="w3-bar-block">
                <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>  Close Menu</a>
                <a href="#" class="w3-bar-item w3-button w3-padding w3-blue"><i class="fa fa-users fa-fw"></i>  Overview</a>
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-eye fa-fw"></i>  Views</a>
                <a href="ControllerServlet?page=adminpanel.jsp&dashboard=user" class="w3-bar-item w3-button w3-padding" ><i class="fa fa-users fa-fw" ></i>  Users</a>
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bullseye fa-fw"></i>  Geo</a>
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-diamond fa-fw"></i>  Orders</a>
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bell fa-fw"></i>  News</a>
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bank fa-fw"></i>  General</a>
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-history fa-fw"></i>  History</a>
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-cog fa-fw"></i>  Settings</a><br><br>
            </div>
        </nav>
        <div class="bg">
            <form name="vResult" id="vResult" action="ControllerServlet" method="post">
                <br><br>
                <div class="w3-row-padding">
                    <div class="w3-col s3 w3-center">&zwnj;</div>
                    <div class="w3-col s3 w3-white">
                        <br>
                        <label class="w3-text-blue"><b>First Name</b></label>
                        <input class="w3-input w3-border w3-round-large" type="text" value="${v_first_name}">

                        <label class="w3-text-blue"><b>Last Name</b></label>
                        <input class="w3-input w3-border w3-round-large" type="text" value="${v_last_name}">

                        <label class="w3-text-blue"><b>Number of Visitors</b></label>
                        <input class="w3-input w3-border w3-round-large" type="text">

                        <label class="w3-text-blue"><b>List of Approved Visitors</b></label>
                        <input class="w3-input w3-border w3-round-large" type="text">
                        <br>
                    </div>
                    <div class="w3-col s1 w3-center">&zwnj;</div>
                    <div class="w3-col s3 w3-white">
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
                    <div class="w3-col s2 w3-center">&zwnj;</div>
                </div>
            </form>    
        </div>
        
    </body>
</html>
