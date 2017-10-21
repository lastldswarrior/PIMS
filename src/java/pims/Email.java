
package pims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Jared
 */
public class Email {
    
    private Connection conn;
    private String emailAddress;
    private String user;
    private String password;
       
    public Email(Connection connection, String email){
        this.conn = connection;
        this.emailAddress = email;
        getUser();
    }
    
    private boolean getUser() {

        try {
            String getUser
                    = "SELECT "
                    + "USER_NAME, "
                    + "USER_PASSWORD "
                    + "FROM "
                    + "PATIENTDB.USERS "
                    + "WHERE "
                    + "USERS.EMAIL = '" + emailAddress + "'";
                    
            System.out.println(getUser); 
            PreparedStatement pst = conn.prepareStatement(getUser);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                user = rs.getString("USER_NAME");
                password= rs.getString("USER_PASSWORD");   
            }
            return true;
            
        } catch (SQLException ex) {
            return false;
        }
    }
    
    
    public void send() {
        
        String userName = "lastldswarrior@gmail.com";
        String sysPass = "bob20427";
        
        String to = emailAddress;
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");      //587 works...465 doesn't

        // Get the Session object.
        Session session = Session.getInstance(props, new Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, sysPass);
          }
      });
        
        StringBuilder sb = new StringBuilder();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            
            message.setSubject("(PIMS)Your Password Notification");
            sb.append("You have requested your password for Patient Information Management System").append('\n');
            sb.append('\n');
            sb.append("UserName: ").append(user).append('\n');
            sb.append("Password: ").append(password).append('\n');
            
            sb.append('\n');
            sb.append("http://localhost:8080/PIMS/").append('\n');
            sb.append('\n');
            sb.append("NOTE: Please do not respond to this automatic email.");

            message.setContent(sb.toString(), "text/plain");

            Transport.send(message);    //working==

        } catch (MessagingException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
