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
                <legend> Change User Password </legend>
                <table>
                    <tr>
                        <td>User ID</td>
                        <td><input type="text" name="username" required="required" /></td>
                    </tr>
                    <tr>
                        <td>New Password</td>
                        <td><input type="password" name="newpass" required="required" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Change Password" /></td>
                    </tr>
                </table>
            </fieldset>
        
    </form>
</html>
