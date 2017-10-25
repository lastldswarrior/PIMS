<%-- 
    Document   : index
    Created on : Sep 6, 2017, 7:07:51 AM
    Author     : Jared
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Application</title>
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
        <!-- Navbar (sit on top) -->
        <div class="w3-top">
            <div class="w3-bar w3-white w3-card-2" id="myNavbar">
                <div class="w3-bar-item w3-wide"><i class="fa fa-h-square"></i> PIMS</div>                
            </div>
        </div>
        <div class="bg">
            <form action="ControllerServlet" method="post">
                <div class="w3-display-middle w3-opacity-min w3-black w3-round-large" style="height:250px;width:400px">
                    <table>
                        <tr>
                            <div class="w3-container w3-center">
                                <h2>Welcome to PIMS</h2>
                            </div>
                        </tr>
                        <tr>
                        <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                               name="username" type="text" placeholder="&#xf007; User ID" value="${newUser}">
                        </tr>
                        <br>
                        <tr>
                        <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                               name="userpass" type="password" placeholder="&#xf023; Password">
                        </tr>
                        <br>
                        <tr>
                        <div class="w3-cell-row">
                            <div class="w3-container w3-cell" style="width:50%">
                                <a href="register.jsp" >Register</a>
                            </div>
                            <div class="w3-container w3-cell" >
                                <a href="forgot.jsp" >Forgot Password?</a>
                            </div>    
                        </div>    
                        </tr>
                        <br>
                        <tr>
                        <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-blue" 
                                style="font-family:Arial, FontAwesome"type="submit">Log In                   
                        </button>
                        </tr>
                    </table>   
                    <input type="hidden" name="page" value='index.jsp' /><%-- This one works --%>
                </div>
            </form>
        </div>
    </body>
</html>
