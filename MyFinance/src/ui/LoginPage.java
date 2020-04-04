package ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class LoginPage implements EventHandler {

    Stage primaryStage;
    Connection connection;
    Button loginButton, createUserButton;
    TextField usernameField;
    PasswordField passwordField;

    public Scene display(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;

        // Page setup.
        GridPane loginPage = new GridPane();
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.setPadding(new Insets(25, 25, 25, 25));

        // Main form components.
        Text loginPageTitle = new Text("Welcome to MyFinance");
        loginPageTitle.setFont(new Font(20));
        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        createUserButton = new Button("Create user"); 
        createUserButton.setOnMouseClicked(this::handle);
        loginButton = new Button("Login");
        loginButton.setOnMouseClicked(this::handle);
        HBox buttonPane = new HBox(10);
        buttonPane.setAlignment(Pos.BOTTOM_RIGHT);
        buttonPane.getChildren().add(createUserButton);
        buttonPane.getChildren().add(loginButton);

        // Page format.
        loginPage.add(loginPageTitle, 0, 0, 2, 1);
        loginPage.add(usernameLabel, 0, 1);
        loginPage.add(usernameField, 1, 1);
        loginPage.add(passwordLabel, 0, 2);
        loginPage.add(passwordField, 1, 2);
        loginPage.add(buttonPane, 1, 4);

        return new Scene(loginPage, 1280, 720);
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == loginButton) {

            // Get the necessary fields...
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();

            // Get the password from the database...
            try {
                Statement s = connection.createStatement();
                ResultSet r = s.executeQuery("select user_password from my_finance_users where username='"+enteredUsername+"'");
                r.next();
                String password = r.getString("user_password");
                if (enteredPassword.equals(password)) primaryStage.setScene(new HomePage().display(connection, primaryStage, enteredUsername));
                else PopUp.display("Error", "Password incorrect.");

                // Exception will be thrown if the username doesn't exist...
            } catch (SQLException e) {
                System.out.println("[ERROR]:" + e);
                PopUp.display("Error", "Username does not exist.");
            }
        }
        
        if (event.getSource() == createUserButton) {
            primaryStage.setScene(new CreateUserPage().display(connection, primaryStage));
        } 
    }
}
