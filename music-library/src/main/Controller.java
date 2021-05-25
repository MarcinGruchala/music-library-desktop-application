package main;

import java.sql.Connection;
import java.sql.DriverManager;

public class Controller {
    Connection connection;

    public void getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//192.168.0.180:1521/XEPDB1","hr","hr");
            System.out.println("Connected to Oracle database server");
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
