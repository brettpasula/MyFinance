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

    public Scene display(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        BorderPane homePage = new BorderPane();
        homePage.setPrefSize(1280, 720);
        homePage.setPadding(new Insets(25, 25, 25, 25));

        HBox topToolbar = new TopToolbar().topToolbar(connection, primaryStage);
        homePage.topProperty().setValue(topToolbar);
        GridPane leftPanel = new LeftPanel().leftPanel(connection, primaryStage);
        homePage.setLeft(leftPanel);
        GridPane rightPanel = new RightPanel().rightPanel(connection, primaryStage);
        homePage.setRight(rightPanel);

        return new Scene(homePage, 1280, 720);
    }
}
