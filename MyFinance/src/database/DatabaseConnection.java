package database;

import ui.PopUp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private Connection connection;

    public void instantiateConnection() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:stu", "ora_bpasula", "a27767145");
        } catch (Exception e) {
            PopUp.display("Database Connection Error", e.toString());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
