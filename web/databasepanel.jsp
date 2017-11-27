<%-- 
    Document   : clearpanel
    Created on : Nov 9, 2017, 5:20:17 PM
    Author     : Jared
--%>

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
        <title>Database Panel</title>
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
        <%-- Java Bean: User --%>
        
        <%--<input type="hidden" name="myuser" value='${user}'/></td><%-- This one works --%> 
    <!-- Navbar (sit on top) -->
    <div class="w3-top">
        <div class="w3-bar w3-black w3-card-2" id="myNavbar">
            <div class="w3-bar-item w3-wide"><i class="fa fa-h-square"></i> PIMS</div>
            <div class="w3-bar-item "><i class="fa fa-bullhorn"></i> Announcement: ${announcement}</div>
            <!-- Right-sided navbar links -->
            <div class="w3-right w3-hide-small">     
                <%--<div class="w3-bar-item"><i class="fa fa-user"></i> ${user}</div> --%> 
                <a href="adminpanel.jsp" class="w3-bar-item w3-button"><i class="fa fa-id-badge"></i> Admin Panel</a> 
                <a class="w3-bar-item w3-button" 
                       onclick='javascript:window.open("help.pdf", "_doc", "scrollbars=1,resizable=1,height=500,width=750,centerscreen");'
                       title='Pop Up'><i class="fa fa-info-circle"></i> HELP</a> 
                <a href="index.jsp" class="w3-bar-item w3-button"><i class="fa fa-arrow-left"></i> BACK TO LOGIN</a>                    
            </div>                
        </div>
    </div>

    <!-- !PAGE CONTENT! -->            
    <div class="bg">
        <form action="ControllerServlet" method="post">      
            <input type="hidden" name="page" value='databasepanel.jsp' /></td><%-- This one works --%>  
            <div class="w3-row-padding">
                <br><br>  
                <div class="w3-row">
                    <div class="w3-col s2 w3-center">
                        <div class="w3-container w3-red w3-padding-16">
                            <div class="w3-left"><i class="fa fa-user-times w3-xxxlarge"></i></div>
                            <div class="w3-right">
                                <h3>${num_2012}</h3>
                            </div>
                            <div class="w3-clear"></div>
                            <h4>2012</h4>
                        </div>
                    </div>
                    <div class="w3-col s2 w3-center">
                        <div class="w3-container w3-blue w3-padding-16">
                            <div class="w3-left"><i class="fa fa-users w3-xxxlarge"></i></div>
                            <div class="w3-right">
                                <h3>${num_2013}</h3>
                            </div>
                            <div class="w3-clear"></div>
                            <h4>2013</h4>
                        </div>
                    </div>
                    <div class="w3-col s2 w3-center">
                        <div class="w3-container w3-brown w3-padding-16">
                            <div class="w3-left"><i class="fa fa-users w3-xxxlarge"></i></div>
                            <div class="w3-right">
                                <h3>${num_2014}</h3>
                            </div>
                            <div class="w3-clear"></div>
                            <h4>2014</h4>
                        </div>
                    </div>
                    <div class="w3-col s2 w3-center">
                        <div class="w3-container w3-teal w3-padding-16">
                            <div class="w3-left"><i class="fa fa-users w3-xxxlarge"></i></div>
                            <div class="w3-right">
                                <h3>${num_2015}</h3>
                            </div>
                            <div class="w3-clear"></div>
                            <h4>2015</h4>
                        </div>
                    </div>
                    <div class="w3-col s2 w3-center">
                        <div class="w3-container w3-orange w3-text-white w3-padding-16">
                            <div class="w3-left"><i class="fa fa-users w3-xxxlarge"></i></div>
                            <div class="w3-right">
                                <h3>${num_2016}</h3>
                            </div>
                            <div class="w3-clear"></div>
                            <h4>2016</h4>
                        </div>
                    </div>
                    <div class="w3-col s2 w3-center">
                        <div class="w3-container w3-green w3-padding-16">
                            <div class="w3-left"><i class="fa fa-user-plus w3-xxxlarge"></i></div>
                            <div class="w3-right">
                                <h3>${num_2017}</h3>
                            </div>
                            <div class="w3-clear"></div>
                            <h4>2017</h4>
                        </div>
                    </div>
                </div>


                <div class="w3-display-middle w3-black w3-round-large" style="height:200px;width:400px">
                    <table>
                        <tr>
                        <div class="w3-container w3-center">
                            <h2>Purge Patients</h2>
                            <p>This will remove patient records that are 6 years old</p>
                        </div>
                        </tr>                        
                        <br>
                        <tr>
                        <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-red" 
                                style="font-family:Arial, FontAwesome" type="submit" >Purge 2012 Patients               
                        </button>
                        </tr>
                    </table>   
                    
                </div>
            </div>

        </form>

    </div>
</body>
</html>
