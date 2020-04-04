package ui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatabaseConnection db = new DatabaseConnection();
        db.instantiateConnection();
        primaryStage.setTitle("MyFinance");
        LoginPage loginPage = new LoginPage();
        primaryStage.setScene(loginPage.display(db.getConnection(), primaryStage));
        primaryStage.show();
    }
}
