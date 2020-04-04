package ui;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class MyAccountPage implements EventHandler {
    Connection connection;
    Stage primaryStage;
    TextField usernameField, passwordField, firstNameField, lastNameField, streetAddressField, cityField, postalCodeField;
    ChoiceBox provinceDropdown;
    Button updateButton, backButton;
    String username;


    public Scene display(Connection connection, Stage primaryStage, String username) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.username = username;

        // Page setup...
        GridPane myAccountPage = new GridPane();
        myAccountPage.setAlignment(Pos.CENTER);
        myAccountPage.setHgap(10);
        myAccountPage.setVgap(10);
        myAccountPage.setPadding(new Insets(25, 25, 25, 25));
        Text createUserPageTitle = new Text("My info");
        createUserPageTitle.setFont(new Font(20));

        // Username...
        Label usernameLabel = new Label("Username:");
        usernameField = new TextField(username);

        // Password...
        Label passwordLabel = new Label("Password:");
        passwordField = new TextField(getUserData(username, "user_password"));

        // First name...
        Label firstNameLabel = new Label("First name:");
        firstNameField = new TextField(getUserData(username, "first_name"));

        // Last name...
        Label lastNameLabel = new Label("Last name:");
        lastNameField = new TextField(getUserData(username, "last_name"));

        // Street address...
        Label streetAddressLabel = new Label("Street address:");
        String userStreetAddress = getUserData(username, "street_address");
        streetAddressField = new TextField(userStreetAddress);

        // City...
        Label cityLabel = new Label("City:");
        String userCity = getUserData(username, "city");
        cityField = new TextField(userCity);

        // Province...
        Label provinceLabel = new Label("Province:");
        String provincial_abbreviations[] = {"BC", "AB", "SK", "MB", "ON", "QC", "NS", "NB", "PE", "NL", "YT", "NT", "NU"};
        provinceDropdown = new ChoiceBox(FXCollections.observableArrayList(provincial_abbreviations));
        String userProvince = getUserData(username, "province");
        provinceDropdown.setValue(userProvince);

        // Postal code...
        Label postalCodeLabel = new Label("Postal code:");
        postalCodeField = new TextField(getPostalCode(userStreetAddress, userCity, userProvince));

        // Buttons...
        updateButton = new Button("Update");
        updateButton.setOnAction(this::handle);
        backButton = new Button("Back to home");
        backButton.setOnAction(this::handle);
        HBox buttonPane = new HBox(10);
        buttonPane.setAlignment(Pos.BOTTOM_RIGHT);
        buttonPane.getChildren().add(backButton);
        buttonPane.getChildren().add(updateButton);

        // Page layout...
        myAccountPage.add(createUserPageTitle, 0, 0, 2, 1);
        myAccountPage.add(usernameLabel, 0, 1);
        myAccountPage.add(usernameField, 1, 1);
        myAccountPage.add(passwordLabel, 0, 2);
        myAccountPage.add(passwordField, 1, 2);
        myAccountPage.add(firstNameLabel, 0, 3);
        myAccountPage.add(firstNameField, 1, 3);
        myAccountPage.add(lastNameLabel, 0, 4);
        myAccountPage.add(lastNameField, 1, 4);
        myAccountPage.add(streetAddressLabel, 0, 5);
        myAccountPage.add(streetAddressField, 1, 5);
        myAccountPage.add(cityLabel, 0, 6);
        myAccountPage.add(cityField, 1, 6);
        myAccountPage.add(provinceLabel, 0, 7);
        myAccountPage.add(provinceDropdown, 1, 7);
        myAccountPage.add(postalCodeLabel, 0, 8);
        myAccountPage.add(postalCodeField, 1, 8);
        myAccountPage.add(buttonPane, 1, 9);

        return new Scene(myAccountPage, 1280, 720);
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == backButton) {
            primaryStage.setScene(new HomePage().display(connection, primaryStage, username));
        }
    }

    private String getPostalCode(String street_address, String city, String province) {
        String result;
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery(String.format("select postal_code from postal_codes where street_address='%s' and city='%s' and province='%s'", street_address, city, province));
            r.next();
            result = r.getString("postal_code");
        } catch (SQLException e) {
            PopUp.display("Error", "Something went wrong while retrieving your data.\n" + e);
            result = "[ERROR]: "+e;
            System.exit(0); // Crash because something's weird...
        }
        return result;
    }

    private String getUserData(String username, String column) {
        String result;
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery(String.format("select %s from my_finance_users where username='%s'", column, username));
            r.next();
            result = r.getString(column);
        } catch (SQLException e) {
            PopUp.display("Error", "Something went wrong while retrieving your data.\n[Exception]: " +e+"\n[At]: "+column);
            System.exit(0); // Crash because something's weird...
            result = "[ERROR]: "+e;
        }
        return result;
    }
}
