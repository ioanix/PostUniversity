package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    private String dbName;


    public DBConnection(String dbName) {

        this.dbName = dbName;
    }

    public Connection getConnection() {

        String connection_string = "jdbc:mysql://localhost/" + dbName + "?serverTimezone=UTC";

        try {

            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException e) {

            System.err.println("Error" + e);
            e.printStackTrace();
        }

        System.out.println("JDBC DRIVER HAS BEEN REGISTERED");

        try {

            conn = DriverManager.getConnection(connection_string, "root", "Univers10");

            return conn;

        } catch(SQLException e) {

            System.out.println("Connection failed " + e.getMessage());
            e.printStackTrace();
        }

        return null;

    }


    public void dbDisconnect() {

        try {

            if(conn != null && !conn.isClosed()) {

                conn.close();
                System.out.println("Connection closed");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
