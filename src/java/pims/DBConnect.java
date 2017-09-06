package pims;

import java.sql.*;
/**
 *
 * @author Jared
 */
public class DBConnect {
    
    private Connection conn;
    
    public DBConnect() {

    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Creates a connection to the Derby Database
     * @throws Exception 3 types(no connection, driver name, or sql exception)
     */
    public void connect() throws Exception{
        try {
            //Connect to the database under the PatientDB schema
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/localDB", "PatientDB", "ninjas");
            
            //Set the connection, else throw an exception
            if (!connection.isClosed()) {
                this.conn = connection;
            }else{
                throw new Exception("No connection to the Database");
            }
            
        } catch (ClassNotFoundException ex) {
            throw new Exception("Class not found or Driver Name has error", ex);
        } catch (SQLException ex) {
            throw new Exception("A SQL Exception has occurred", ex);
        }
    }
}


