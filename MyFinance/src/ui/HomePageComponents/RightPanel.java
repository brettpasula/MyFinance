package ui.HomePageComponents;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class RightPanel implements EventHandler {
    Connection connection;
    Stage primaryStage;
    String username;

    public BorderPane rightPanel(Connection connection, Stage primaryStage, String username) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.username = username;
        BorderPane rightPanel = new BorderPane();

        return rightPanel;
    }

    @Override
    public void handle(Event event) {

    }
}
