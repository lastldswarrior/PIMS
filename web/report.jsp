<%-- 
    Document   : report
    Created on : Sep 27, 2017, 6:46:31 PM
    Author     : Jared
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Reports</title>
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
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
        <style>
            html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
        </style>
    </head>
    
    <body>
        <!-- Sidebar/menu -->
        <nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br />
            <div class="w3-container w3-row">
                <div class="w3-col s4">
                    <img src="/w3images/avatar2.png" class="w3-circle w3-margin-right" style="width:46px" />
                </div>
                
                <div class="w3-col s8 w3-bar">
                    <span><strong>Report - Click Mail</strong></span><br />
                    <a href="ReportServlet" class="w3-bar-item w3-button"><i class="fa fa-envelope"></i></a>
                    <a href="#" class="w3-bar-item w3-button"><i class="fa fa-user"></i></a>
                    <a href="#" class="w3-bar-item w3-button"><i class="fa fa-cog"></i></a>
                </div>
            </div>
            <hr />
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
                <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-cog fa-fw"></i>  Settings</a> <br /><br />
            </div>
        </nav>

        <form name="reportThis" id="vPage" action="ReportServlet" method="post">
            <input type="hidden" name="page" value='report.jsp' />          

            <div class="w3-col s3" style="width:300px">&zwnj;</div>
            <div class="w3-col s9 w3-light-grey">
                <table class="w3-table">
                    <tr>
                        <td>
                            <img src="arkham.png" style="width:100%"/>                           
                        </td>
                        <td>
                            <table class="w3-table">
                                <tr>
                                    <td><font size="7">Arkham Asylum Reporting Tool</font>
                                </tr>                                
                                <tr>
                                    <font size="5">&zwnj;</font>
                                </tr>
                            </table>
                        </td>
                        
                    </tr>
                    
                </table>
                SELECT
                <div class="w3-row-padding">
                    <div class="w3-half">                        
                        <input class="w3-radio" type="radio" name="patients" value="allPatients" checked>
                        <label>All Patients</label>
                    </div>
                    <div class="w3-half">
                        <input class="w3-radio" type="radio" name="patients" value="onePatient">
                        <label>One Patient</label>
                    </div>
                </div>
                WHERE
                <div class="w3-row-padding">
                    <div class="w3-third">
                        <select class="w3-select w3-border" name="table" onchange="this.form.submit()">
                            <option value="" disabled selected>Choose your option</option>
                            <c:forEach var="table" items="${tableList}" >
                                <c:choose>
                                    <c:when test="${not empty selectedTable && selectedTable eq table}">
                                        <option value="${table}" selected = "true">${table}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${table}">${table}</option>                                        
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>                            
                        </select>
                    </div>
                    <div class="w3-third">
                        <select class="w3-select w3-border" name="columns" onchange="this.form.submit()">
                            <option value="" disabled selected>Choose your option</option>
                            <c:forEach var="cols" items="${colList}" >
                                <c:choose>
                                    <c:when test="${not empty selectedCol && selectedCol eq cols}">
                                        <option value="${cols}" selected = "true">${cols}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${cols}">${cols}</option>                                        
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>                            
                        </select>
                    </div>
                </div>
                CONDITION
                <div class="w3-row-padding">
                    <div class="w3-third">
                        <select class="w3-select w3-border" name="condition" onchange="this.form.submit()">
                            <option value="" disabled selected>Choose your option</option>
                            <c:forEach var="condition" items="${conditionList}" >
                                <c:choose>
                                    <c:when test="${not empty selectedCondition && selectedCondition eq condition}">
                                        <option value="${condition}" selected = "true">${condition}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${condition}">${condition}</option>                                        
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>                            
                        </select>
                    </div>
                    <div class="w3-third">
                        <input class="w3-input w3-border" type="text" name="input" value="${inputValue}">
                    </div>
                    <div class="w3-third">
                        <input class="w3-button w3-blue" type="submit" value="Run Report" >
                        
                        <input type="hidden" id="download" name="download" value='' />
                        <input class="w3-button w3-red" type="submit" value="Download Table" onclick="ckDownload()" />
                    </div>
                </div>
                
                <table class="w3-table-all w3-striped w3-bordered w3-border w3-hoverable w3-white">
                    <tr class="w3-bordered w3-border w3-black">
                        <td>Patient</td>
                        <td>${selectedCol}</td>
                    </tr>   
                    <c:forEach items="${rows}" var="row">
                        <tr >                        
                            <td class="myrow" id="${row.getIndex(0)}"><c:out value="${row.getIndex(0)}" /></td>
                            <td class="myrow" id="${row.getIndex(1)}"><c:out value="${row.getIndex(1)}" /></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </form>
</body>
</html>


<script type="text/javascript" >
    
    function ckDownload(){
        document.getElementById("download").value = true;
        document.getElementById('reportThis').submit();
    }
    
</script>