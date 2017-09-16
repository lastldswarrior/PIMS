<%-- 
    Document   : volunteerpanel
    Created on : Sep 9, 2017, 7:10:02 PM
    Author     : Jared
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <title>Volunteer Panel</title>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
    </style>
    <form name="Volunteer" action="ControllerServlet" method="post">
    <input type="hidden" name="page" value='volunteerpanel.jsp' /></td><%-- This one works --%>
    == Volunteer Page == ${announcement}<%-- This one works --%>
    <fieldset style="width: 300px">
        <legend> Search for Patient </legend>    
        <table>
            <tr>
                <td>First Name</td>
                <td><input type="text" name="v_first_name" /></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><input type="text" name="v_last_name"  /></td>
            </tr>
            <tr>
                <td><input type="submit" value="Search" onclick="this.form.submit()"/></td>
            </tr>
        </table>
    </fieldset>
    <br>
    <div class="w3-container">
        <table class="w3-table w3-striped w3-bordered w3-border w3-black w3-white">
            <tr>
                <td>Account Names</td>
                <td>Level of Access</td>
            </tr>   
        </table>                  

        <table class="w3-table w3-striped w3-bordered w3-border w3-hoverable w3-white">
            <c:forEach items="${patients}" var="p">
                <tr>
                    <td><c:out value="${p.userName}" /></td>

                </tr>
            </c:forEach>
        </table><br>
        <button class="w3-button w3-dark-grey">More Countries  <i class="fa fa-arrow-right"></i></button>
    </div>
    <br>
</form>
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
</html>