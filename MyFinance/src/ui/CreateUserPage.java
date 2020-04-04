package ui;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUserPage implements EventHandler {

    Stage primaryStage;
    GridPane createUserPage;
    Connection connection;
    TextField usernameField, passwordField, firstNameField, lastNameField, streetAddressField, cityField, postalCodeField;
    ComboBox provinceComboBox;
    Button createButton, backButton;

    public Scene display(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;

        createUserPage = new GridPane();
        createUserPage.setAlignment(Pos.CENTER);
        createUserPage.setHgap(10);
        createUserPage.setVgap(10);
        createUserPage.setPadding(new Insets(25, 25, 25, 25));
        Text createUserPageTitle = new Text("Create user");
        createUserPageTitle.setFont(new Font(20));
        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        passwordField = new TextField();
        Label firstNameLabel = new Label("First name:");
        firstNameField = new TextField();
        Label lastNameLabel = new Label("Last name:");
        lastNameField = new TextField();
        Label streetAddressLabel = new Label("Street address:");
        streetAddressField = new TextField();
        Label cityLabel = new Label("City:");
        cityField = new TextField();
        Label provinceLabel = new Label("Province:");
        String provincial_abbreviations[] = {"BC", "AB", "SK", "MB", "ON", "QC", "NS", "NB", "PE", "NL", "YT", "NT", "NU"};
        provinceComboBox = new ComboBox(FXCollections.observableArrayList(provincial_abbreviations));
        Label postalCodeLabel = new Label("Postal code:");
        postalCodeField = new TextField();
        createButton = new Button("Submit");
        createButton.setOnMouseClicked(this::handle);
        backButton = new Button("Back to login");
        backButton.setOnMouseClicked(this::handle);
        HBox buttonPane = new HBox(10);
        buttonPane.setAlignment(Pos.BOTTOM_RIGHT);
        buttonPane.getChildren().add(backButton);
        buttonPane.getChildren().add(createButton);

        createUserPage.add(createUserPageTitle, 0, 0, 2, 1);
        createUserPage.add(usernameLabel, 0, 1);
        createUserPage.add(usernameField, 1, 1);
        createUserPage.add(passwordLabel, 0, 2);
        createUserPage.add(passwordField, 1, 2);
        createUserPage.add(firstNameLabel, 0, 3);
        createUserPage.add(firstNameField, 1, 3);
        createUserPage.add(lastNameLabel, 0, 4);
        createUserPage.add(lastNameField, 1, 4);
        createUserPage.add(streetAddressLabel, 0, 5);
        createUserPage.add(streetAddressField, 1, 5);
        createUserPage.add(cityLabel, 0, 6);
        createUserPage.add(cityField, 1, 6);
        createUserPage.add(provinceLabel, 0, 7);
        createUserPage.add(provinceComboBox, 1, 7);
        createUserPage.add(postalCodeLabel, 0, 8);
        createUserPage.add(postalCodeField, 1, 8);
        createUserPage.add(buttonPane, 1, 9);

        return new Scene(createUserPage, 1280, 720);
    }

    @Override
    public void handle(Event event) {
        LoginPage loginPage = new LoginPage();

        if (event.getSource() == backButton) {
            primaryStage.setScene(loginPage.display(connection, primaryStage));
        }

        if (event.getSource() == createButton) {
            try {
                // First check if the postal code exists.
                if (DoesPostalCodeExist()) {
                    // If it doesn't enter it first since it's a foreign key to MY_FINANCE_USERS.
                    PreparedStatement postal_code = connection.prepareStatement("insert into postal_codes values (?,?,?,?)");
                    postal_code.setString(1, streetAddressField.getText());
                    postal_code.setString(2, cityField.getText());
                    postal_code.setString(3, provinceComboBox.getValue().toString());
                    postal_code.setString(4, postalCodeField.getText());
                    postal_code.executeUpdate();
                }

                // Add the new user data to the database.
                PreparedStatement new_user = connection.prepareStatement("insert into my_finance_users values (?,?,?,?,?,?,?)");
                new_user.setString(1, usernameField.getText());
                new_user.setString(2, passwordField.getText());
                new_user.setString(3, firstNameField.getText());
                new_user.setString(4, lastNameField.getText());
                new_user.setString(5, streetAddressField.getText());
                new_user.setString(6, cityField.getText());
                new_user.setString(7, provinceComboBox.getValue().toString());
                new_user.executeUpdate();

                // Return the user to the login page and prompt them to login.
                primaryStage.setScene(loginPage.display(connection, primaryStage));
                PopUp.display("Login", "Welcome to My Finance, "+firstNameField.getText()+".\nPlease login with your newly created credentials.");
            } catch (SQLException e) {
                PopUp.display("Error", e.toString());
            }
        }
    }

    // Checks the database to see if there is already a postal_codes record.
    // Returns true if there is, false otherwise.
    private Boolean DoesPostalCodeExist() {
        Boolean exists;
        try {
            PreparedStatement postal_code_check = connection.prepareStatement("select 1 from postal_codes where street_address=? and city=? and province=?");
            postal_code_check.setString(1, streetAddressField.getText());
            postal_code_check.setString(2, cityField.getText());
            postal_code_check.setString(3, provinceComboBox.getValue().toString());
            exists = postal_code_check.execute();
        } catch (SQLException e) {
            PopUp.display("Error", e.toString());
            exists = false;
        }
        return exists;
    }
}


