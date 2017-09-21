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
        <div class="bg">
            <a href="adminpanel.jsp" class="w3-btn w3-blue w3-round-xlarge">Return to Dashboard</a>
            <form name="Volunteer" id="vPage" action="ControllerServlet" method="post">
                <input type="hidden" name="page" value='volunteerpanel.jsp' /><%-- This one works --%>
                <br><br>
                <div class="w3-row-padding">
                    <div class="w3-col s1 w3-center">&zwnj;</div>
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
            <div class="w3-col s4 w3-center">&zwnj;</div>
            <div class="w3-col s1 w3-center">&zwnj;</div>            
            </div>
                                 
        </div>
        


        <div class="w3-container">
            <input type="hidden" name="selected" value="none">
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