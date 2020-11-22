/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp3415.telehealth.db;
import java.sql.Connection;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
/**
 *
 * @author Gautam
 */
public class MySQLConnections {
    public static Connection getConnection() throws Exception{
    String url = "jdbc:mysql://remotemysql.com:3306/6Skh2ICVv6";                                 //credentials for external host
    
    String hostUsername = "6Skh2ICVv6";                                                               // Host credentials
    String hostPassword = "u3mslv6gsd";
    
    Class.forName("com.mysql.cj.jdbc.Driver");                                                     // registering driver
    Connection sqlConnection = (Connection)DriverManager.getConnection(url, hostUsername, hostPassword);        //SQL Connection
            
    return sqlConnection;
    }
}
