<%-- 
    Document   : volunteerpanel
    Created on : Sep 9, 2017, 7:10:02 PM
    Author     : Jared
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Volunteer Panel</title>
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
        <!-- Sidebar/menu -->
        <nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
            <div class="w3-container w3-row">
                <div class="w3-col s4">
                    <img src="/w3images/avatar2.png" class="w3-circle w3-margin-right" style="width:46px">
                </div>
                
                <div class="w3-col s8 w3-bar">
                    <span>Volunteer ${user}</strong></span><br>
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
                <a href="report.jsp" class="w3-bar-item w3-button w3-padding"><i class="fa fa-eye fa-fw"></i>  Reports</a>
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
            <a href="adminpanel.jsp" class="w3-btn w3-blue w3-round-xlarge">Return to Dashboard</a>
            <form name="Volunteer" id="vPage" action="ControllerServlet" method="post">
                <input type="hidden" name="page" value='volunteerpanel.jsp' /><%-- This one works --%>
                <br><br>
                <div class="w3-row-padding">
                    <div class="w3-col s4 w3-center">&zwnj;</div>
                    <div class="w3-col s4">
                    <table>
                        <tr>
                            <td colspan="3">
                                <div class="w3-display-container" style="width:400px"></div>
                                <div class="w3-round-large w3-border w3-black w3-center w3-block" style="font-family:Arial, FontAwesome;width:100%" >
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
            <div class="w3-col s2 w3-center" style="width:15%">&zwnj;</div>    
            <div class="w3-col s1 w3-center">&zwnj;</div>
                  
            </div>           
        </div>
        
        <input type="hidden" name="selected" value="none">
        <!-- !PAGE CONTENT! -->
        <div class="w3-main" style="margin-left:300px;margin-top:43px;">        
            
            <table class="w3-table w3-bordered w3-border w3-black">
                <tr>
                    <td>Patients</td>
                </tr>   
            </table>                  
          
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
            <input type="hidden" name="v_display_name" id="temp" value="${v_display_name}" /><%-- This one works --%>
            <table class="w3-table-all w3-striped w3-bordered w3-border w3-hoverable w3-white">
                <c:forEach items="${vol_info}" var="pa">
                    <tr >                        
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