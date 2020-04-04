package ui.HomePageComponents;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class LeftPanel implements EventHandler {
    Connection connection;
    Stage primaryStage;

    public GridPane leftPanel(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        GridPane leftPanel = new GridPane();

        return leftPanel;
    }

    @Override
    public void handle(Event event) {

    }
}
