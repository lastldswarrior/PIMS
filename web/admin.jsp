<%-- 
    Document   : admin
    Created on : Sep 6, 2017, 11:50:42 AM
    Author     : Jared
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <form name="Admin" action="ControllerServlet" method="post">
        <input type="hidden" name="page" value='admin.jsp' /></td><%-- This one works --%>
    == Admin Page == ${announcement}<%-- This one works --%>
    <fieldset style="width: 300px">
        <legend> Change User Password - Works</legend>        
        <table>
            <tr>
                <td>User ID</td>
                <td><input type="text" name="username"  /></td>
            </tr>
            <tr>
                <td>New Password</td>
                <td><input type="password" name="a_pass"  /></td>
            </tr>
            <tr>
                <td><input type="submit" value="Change Password" onclick=""/></td>
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset style="width: 300px">
        <legend> Add New User </legend>
        <table>
            <tr>
                <td>User ID</td>
                <td><input type="text" name="newusername"  /></td>
            </tr>
            <tr>
                <td>New Password</td>
                <td><input type="password" name="newpass"  /></td>
            </tr>
            <tr>
                <td>Access</td>
                <td><input type="text" name="newaccess" /></td>
            </tr>
            <tr>
                <td><input type="submit" value="Add User" /></td>
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset style="width: 300px">
        <legend> Change User Access - Works</legend>
        <table>
            <tr>
                <td>User ID</td>
                <td><input type="text" name="access_username"  /></td>
            </tr>
            <tr>
                <td>New Access</td>
                <td><input type="test" name="access"  /></td>
            </tr>
            <tr>
                <td><input type="submit" value="Change Access" /></td>
            </tr>
        </table>
    </fieldset>
</form>
    <a href="adminpanel.jsp" class="w3-btn w3-blue w3-round-xlarge">Return to Dashboard</a>
</html>
