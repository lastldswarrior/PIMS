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
    </head>
    <body>
        <form action="ControllerServlet" method="post">
            <fieldset style="width: 300px">
                <legend> Login to App </legend>
                <table>
                    <tr>
                        <td>User ID</td>
                        <td><input type="text" name="username" required="required" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="userpass" required="required" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                    </tr>
                </table>
            </fieldset>
             ${pass}<%-- This one works --%>
            <input type="hidden" name="page" value='index.jsp' /></td><%-- This one works --%>
        </form>
    </body>
</html>
