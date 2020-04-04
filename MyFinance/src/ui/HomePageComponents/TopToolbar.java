package ui.HomePageComponents;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import ui.HomePage;
import ui.LoginPage;
import ui.MyAccountPage;

import java.sql.Connection;

public class TopToolbar implements EventHandler {

    Connection connection;
    Stage primaryStage;
    Button logoutButton, myAccountButton;
    String username;
    HomePage homePage;

    public HBox topToolbar(Connection connection, Stage primaryStage, String username) {
        this.username = username;
        this.connection = connection;
        this.primaryStage = primaryStage;
        HBox topToolbar = new HBox();

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        myAccountButton = new Button("My account");
        myAccountButton.setOnAction(this::handle);

        logoutButton = new Button("Logout");
        logoutButton.setOnAction(this::handle);

        topToolbar.getChildren().addAll(myAccountButton, spacer, logoutButton);
        return topToolbar;
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == logoutButton) {
            primaryStage.setScene(new LoginPage().display(connection, primaryStage));
        }

        if (event.getSource() == myAccountButton) {
            primaryStage.setScene(new MyAccountPage().display(connection, primaryStage, username));
        }
    }
}
