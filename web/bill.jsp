<%-- 
    Document   : bill
    Created on : Nov 13, 2017, 10:46:39 AM
    Author     : Jared
--%>

<%-- 
    Document   : forgot
    Created on : Oct 18, 2017, 6:43:33 PM
    Author     : Jared
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Bill Patient</title>
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
        <!-- Navbar (sit on top) -->
        <div class="w3-top">
            <div class="w3-bar w3-white w3-card-2" id="myNavbar">
                <div class="w3-bar-item w3-wide"><i class="fa fa-h-square"></i> PIMS</div>
                <!-- Right-sided navbar links -->
                <div class="w3-right w3-hide-small">          
                    <a href="officepanel.jsp" class="w3-bar-item w3-button"><i class="fa fa-desktop"></i> OFFICE</a> 
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
        <div class="bg">
            <form action="BillServlet" method="post">
                <div class="w3-display-middle w3-opacity w3-white w3-round-large" style="height:190px;width:400px">
                    <table>
                        <tr>
                            <div class="w3-container w3-center">
                                <h2>Generate Bill for Patient</h2>                                
                            </div>
                        </tr>
                        <tr>
                        <input class="w3-input w3-round-large w3-large w3-border" style="font-family:Arial, FontAwesome" 
                               name="bill_patient" type="text" placeholder="&#xf007; Patient Name" >
                        </tr>
                        <br>
                        <tr>
                        <button class="w3-button w3-block w3-round-large w3-large w3-border w3-black w3-hover-white" 
                                style="font-family:Arial, FontAwesome" type="submit">Download Bill                 
                        </button>
                        </tr>
                    </table>   
                    <input type="hidden" name="page" value='bill.jsp' /><%-- This one works --%>
                </div>
            </form>
        </div>
    </body>
</html>
