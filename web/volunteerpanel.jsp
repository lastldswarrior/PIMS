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

                height: 50%; 

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
            
            <form name="Volunteer" action="ControllerServlet" method="post">
                <input type="hidden" name="page" value='volunteerpanel.jsp' /><%-- This one works --%>
                <div class="w3-display-middle" style="height:750px;width:400px">
                    <table>
                        <tr>
                            <div class="w3-round-large w3-border w3-black w3-center" style="font-family:Arial, FontAwesome" >
                                <h2>Patient Search</h2>
                            </div>
                        </tr>
                        <br>
                        <tr>
                            <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                                   name="v_first_name" type="text" placeholder="&#xf007; First Name">
                            <br>
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-blue" 
                                    style="font-family:Arial, FontAwesome" type="submit">Search by First Name                  
                            </button>
                        </tr>
                        <br>
                        <tr>
                        <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                               name="v_last_name" type="password" placeholder="&#xf007; Last Name">
                        <br>
                            <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-red" 
                                    style="font-family:Arial, FontAwesome" type="submit">Search by Last Name                  
                            </button>
                        </tr>
                        <br>
                        
                    </table>   
                    
                </div>
            </form>
            </form>
        </div>
        <a href="adminpanel.jsp" class="w3-btn w3-blue w3-round-xlarge">Return to Dashboard</a>
        <br>
        <div class="w3-container">
            <table class="w3-table w3-striped w3-bordered w3-border w3-black w3-white">
                <tr>
                    <td>Patient</td>
                    <td>Room Number</td>
                    <td>Max # of Vistors</td>
                    <td>List of Approved Vistors</td>
                </tr>   
            </table>                  

            <table class="w3-table w3-striped w3-bordered w3-border w3-hoverable w3-white">
                <c:forEach items="${vol_info}" var="pa">
                    <tr>
                        <td><c:out value="${pa.displayName}"/></td>
                    </tr>
                </c:forEach>
            </table><br>
            <button class="w3-button w3-dark-grey">More Countries  <i class="fa fa-arrow-right"></i></button>
        </div>

    </body>
</html>