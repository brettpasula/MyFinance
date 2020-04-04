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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;

public class MyAccountPage implements EventHandler {
    Connection connection;
    Stage primaryStage;
    TextField usernameField, passwordField, firstNameField, lastNameField, streetAddressField, cityField, postalCodeField;
    ComboBox provinceComboBox;
    Button createButton, backButton;


    public Scene display(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;

        GridPane myAccountPage = new GridPane();
        myAccountPage.setAlignment(Pos.CENTER);
        myAccountPage.setHgap(10);
        myAccountPage.setVgap(10);
        myAccountPage.setPadding(new Insets(25, 25, 25, 25));
        Text createUserPageTitle = new Text("Create user");
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
        myAccountPage.add(provinceComboBox, 1, 7);
        myAccountPage.add(postalCodeLabel, 0, 8);
        myAccountPage.add(postalCodeField, 1, 8);
        myAccountPage.add(buttonPane, 1, 9);

        return new Scene(myAccountPage, 1280, 720);
    }

    @Override
    public void handle(Event event) {

    }
}
