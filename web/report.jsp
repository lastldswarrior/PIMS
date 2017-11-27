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
        <!-- Navbar (sit on top) -->
        <div class="w3-top">
            <div class="w3-bar w3-white w3-card-2" id="myNavbar">
                <div class="w3-bar-item w3-wide"><i class="fa fa-h-square"></i> PIMS</div>
                <a href="ReportServlet" class="w3-bar-item w3-button"><i class="fa fa-repeat"></i> Reset</a>
                <!-- Right-sided navbar links -->
                <div class="w3-right w3-hide-small">
                    <a href="officepanel.jsp" class="w3-bar-item w3-button"><i class="fa fa-desktop"></i> OFFICE</a>
                    <a class="w3-bar-item w3-button" 
                       onclick='javascript:window.open("help.pdf", "_doc", "scrollbars=1,resizable=1,height=500,width=750,centerscreen");'
                       title='Pop Up'><i class="fa fa-info-circle"></i> HELP</a> 
                    <a href="index.jsp" class="w3-bar-item w3-button"><i class="fa fa-power-off"></i> SIGN OUT</a>                    
                </div>
                <!-- Hide right-floated links on small screens and replace them with a menu icon -->

                <a href="javascript:void(0)" class="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium" onclick="w3_open()">
                    <i class="fa fa-bars"></i>
                </a>
            </div>
        </div>
        

        <form name="reportThis" id="vPage" action="ReportServlet" method="post">
            <input type="hidden" name="page" value='report.jsp' />          
            <br><br>
            <div class="w3-col s1">&zwnj;</div>
            <div class="w3-col s10 w3-light-grey">
                <table class="w3-table">
                    <tr>
                        <td>
                            <img src="ark2.jpg" style="width:75%"/>                           
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
                        <input class="w3-button w3-blue" type="submit" style="font-family:Arial, FontAwesome" value="&#xf013; Run Report" >
                        
                        <input type="hidden" id="download" name="download" value='' />
                        <input class="w3-button w3-red" type="submit" style="font-family:Arial, FontAwesome" value="&#xf019; Download Table" onclick="ckDownload()" />
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
            <div class="w3-col s1" >&zwnj;</div>
        </form>
</body>
</html>


<script type="text/javascript" >
    
    function ckDownload(){
        document.getElementById("download").value = true;
        document.getElementById('reportThis').submit();
    }
    
</script>