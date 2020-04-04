package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.HomePageComponents.LeftPanel;
import ui.HomePageComponents.RightPanel;
import ui.HomePageComponents.TopToolbar;

import java.sql.Connection;

public class HomePage {

    Connection connection;
    Stage primaryStage;
    String username;

    public Scene display(Connection connection, Stage primaryStage, String username) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.username = username;
        BorderPane homePage = new BorderPane();
        homePage.setPrefSize(1280, 720);
        homePage.setPadding(new Insets(25, 25, 25, 25));

        HBox topToolbar = new TopToolbar().topToolbar(connection, primaryStage, username);
        homePage.topProperty().setValue(topToolbar);
        BorderPane leftPanel = new LeftPanel().leftPanel(connection, primaryStage, username);
        leftPanel.setPadding(new Insets(25, 25, 0, 0));
        homePage.setLeft(leftPanel);
        BorderPane rightPanel = new RightPanel().rightPanel(connection, primaryStage, username);
        rightPanel.setPadding(new Insets(25, 25, 0, 0));
        homePage.setRight(rightPanel);

        return new Scene(homePage, 1280, 720);
    }
}
