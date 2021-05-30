package main;

import java.sql.Connection;
import java.sql.DriverManager;

public  class DatabaseConnector {
    //private final static String JDBC_URL = "jdbc:oracle:thin:@//192.168.0.153:1521/XEPDB1"; //Piotra
    private final static String JDBC_URL = "jdbc:oracle:thin:@//192.168.0.180:1521/XEPDB1"; //Marcina
    private final static String JDBC_USERNAME = "hr";
    private final static String JDBC_PASSWORD = "hr";

    static private Connection connection;

    public static Connection getConnection() {
        return connection;
    }
    
    static public void setConnection(){
        try {
            connection = DriverManager.getConnection(JDBC_URL,JDBC_USERNAME,JDBC_PASSWORD);
            System.out.println("Connected to Oracle database server");
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
