<%-- 
    Document   : forgot
    Created on : Oct 18, 2017, 6:43:33 PM
    Author     : Jared
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Forgot Password</title>
        <style>
            body, html {
                height: 100%;
                margin: 0;
                background-color: black;
            }

            .bg {
                background-image: url("forgot2.jpg");                
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
        <div class="bg">
            <form action="ControllerServlet" method="post">
                <div class="w3-display-middle w3-opacity w3-white w3-round-large" style="height:250px;width:400px">
                    <table>
                        <tr>
                            <div class="w3-container w3-center">
                                <h2>Forgot Your Password?</h2>
                                <p>Enter your email address and we will send you your password on a postcard via USPS</p>
                            </div>
                        </tr>
                        <tr>
                        <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                               name="f_email" type="text" placeholder="&#xf0e0; Your Email" value="${newUser}">
                        </tr>
                        <br>
                        <tr>
                        <div class="w3-cell-row">
                            <div class="w3-container w3-cell">
                                <a href="index.jsp" ><< Back to Login</a>
                            </div>
                            
                        </div>    
                        </tr>
                        <br>
                        <tr>
                        <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-white" 
                                style="font-family:Arial, FontAwesome" type="submit">Send My Password                 
                        </button>
                        </tr>
                    </table>   
                    <input type="hidden" name="page" value='forgot.jsp' /><%-- This one works --%>
                </div>
            </form>
        </div>
    </body>
</html>