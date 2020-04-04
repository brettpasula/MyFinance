package ui.HomePageComponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.PopUp;

import java.sql.Connection;
import java.sql.SQLException;

public class LeftPanel implements EventHandler {
    Connection connection;
    Stage primaryStage;
    String username;
    ComboBox recordComboBox;
    Button addRecordButton, displayRecordsButton; 
    TableView bottomPanel = new TableView();
    BorderPane leftPanel;
    ObservableList data = FXCollections.observableArrayList();

    public BorderPane leftPanel(Connection connection, Stage primaryStage, String username) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.username = username;

        // Top panel - MyFinance at a glance.
        GridPane topPanel = new GridPane();
        Text myFinanceAtAGlance = new Text("MyFinance at a glance");
        myFinanceAtAGlance.setFont(new Font(20));
        topPanel.add(myFinanceAtAGlance, 0, 0); // Top left.

        // Center panel - menu bar.
        HBox centerPanel = new HBox(25);
        centerPanel.setPadding(new Insets(25, 0, 25, 0));
        recordComboBox = new ComboBox();
        recordComboBox.getItems().addAll("Account", "Bill", "Budget Category", "Budget", "Cash Transaction", "Corporation", "Credit Card", "Credit Transaction", "Investment", "Physical Item");
        addRecordButton = new Button("Add record");
        addRecordButton.setOnAction(this::handle);
        displayRecordsButton = new Button("Display records");
        displayRecordsButton.setOnAction(this::handle);
        centerPanel.getChildren().addAll(recordComboBox, addRecordButton, displayRecordsButton);

        // Parent panel.
        leftPanel = new BorderPane();
        leftPanel.setTop(topPanel);
        leftPanel.setCenter(centerPanel);
        leftPanel.setBottom(bottomPanel);

        return leftPanel;
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == displayRecordsButton) {
            try {
                bottomPanel = new TableRecordsRetriever().getRecords(recordComboBox.getValue().toString(), connection, username, bottomPanel, data);
            } catch (SQLException e) {
                PopUp.display("Error", "Something went wrong while retrieving your data.\n[Exception]: " +e+"\n[At]: "+recordComboBox.getValue().toString());
                System.exit(0); // Crash because something's weird...
            }
        }
    }
}
