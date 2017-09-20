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
    </head>
    <body>
        <div class="bg">
            <form name="vResult" id="vResult" action="ControllerServlet" method="post">
                <br><br>
                <div class="w3-row-padding">
                    <div class="w3-col s1 w3-center w3-red">1</div>
                    <div class="w3-col s4 w3-center w3-blue">
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
                                    <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                            style="font-family:Arial, FontAwesome">Full Name:                
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
                                            style="font-family:Arial, FontAwesome">Room Number:                
                                    </button>
                                </td> 
                                <td colspan="2">
                                    <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                           name="v_display_room" type="text" value="${v_display_room}">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                            style="font-family:Arial, FontAwesome"># of Vistors:                
                                    </button>
                                </td>
                                <td colspan="2">
                                    <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                           name="v_display_count" type="text" value="${v_display_count}">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black" 
                                            style="font-family:Arial, FontAwesome">Approved Visitors:                
                                    </button>
                                </td>
                                <td colspan="2">
                                    <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                           name="v_display_room" type="text" >
                                </td>
                            </tr>

                        </table>
                    </div>
                    <div class="w3-col s2 w3-center w3-yellow">2</div>
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
                    <div class="w3-col s2 w3-center w3-red">2</div>
                </div>
            </form>    
        </div>
        <div class="w3-row-padding">
            <div class="w3-col s1 w3-center w3-blue">1</div>
            <div class="w3-col s4">
                <label class="w3-text-blue"><b>First Name</b></label>
                <input class="w3-input w3-border" type="text" value="${v_display_name}">

                <label class="w3-text-blue"><b>Last Name</b></label>
                <input class="w3-input w3-border" type="text">
            </div>
            <div class="w3-col s2 w3-center w3-green">2</div>
            <div class="w3-col s3"> 
                <label class="w3-text-blue"><b>Number of Visitors</b></label>
                <input class="w3-input w3-border" type="text">

                <label class="w3-text-blue"><b>List of Approved Visitors</b></label>
                <input class="w3-input w3-border" type="text">
            </div>
            <div class="w3-col s2 w3-center w3-yellow">2</div>
    </body>
</html>
