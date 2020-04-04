package ui.HomePageComponents;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class RightPanel implements EventHandler {
    Connection connection;
    Stage primaryStage;

    public GridPane rightPanel(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        GridPane rightPanel = new GridPane();

        return rightPanel;
    }

    @Override
    public void handle(Event event) {

    }
}
