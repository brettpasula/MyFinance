package ui.HomePageComponents;

import database.TableViewRecordsRetriever;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.PopUp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class RightPanel implements EventHandler {
    Connection connection;
    Stage primaryStage;
    String username;
    ComboBox recordComboBox;
    Button addRecordButton, displayAllButton, filterButton, deleteRecordButton, generateSummaryButton;
    TableView bottomPanelTableView = new TableView();
    DatePicker fromDate, toDate;
    BorderPane leftPanel;
    ObservableList data = FXCollections.observableArrayList();
    Font labelFont = new Font(15);
    HBox aggregateStatsRow = new HBox();

    public BorderPane rightPanel(Connection connection, Stage primaryStage, String username) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.username = username;

        // TOP PANEL - My records:
        GridPane topPanel = new GridPane();
        Text myFinanceAtAGlance = new Text("My records");
        myFinanceAtAGlance.setFont(new Font(20));
        topPanel.add(myFinanceAtAGlance, 0, 0);
        topPanel.setVgap(10);

        // CENTER PANEL - menu:
        VBox centerPanel = new VBox();
        centerPanel.setPadding(new Insets(25, 0, 0, 0));

        // Top row - select category, display all.
        HBox centerPanelTopRow = new HBox(10);
        Text recordTypeLabel = new Text("Record type: ");
        recordTypeLabel.setFont(labelFont);
        recordComboBox = new ComboBox();
        recordComboBox.getItems().addAll("Account", "Bill", "Budget Category", "Budget", "Cash Transaction", "Corporation", "Credit Card", "Credit Transaction", "Investment", "Physical Item");
        displayAllButton = new Button("Display all records");
        displayAllButton.setOnAction(this::handle);
        centerPanelTopRow.setAlignment(Pos.CENTER_LEFT);
        centerPanelTopRow.getChildren().addAll(recordTypeLabel, recordComboBox, displayAllButton);

        // Center row - filter by date:
        HBox centerPanelCenterRow = new HBox(10);
        centerPanelCenterRow.setPadding(new Insets(10, 0, 10, 0));
        Text fromDateLabel = new Text("From: ");
        fromDateLabel.setFont(labelFont);
        fromDate = new DatePicker();
        Text toDateLabel = new Text("To: ");
        toDateLabel.setFont(labelFont);
        toDate = new DatePicker();
        filterButton = new Button("Filter");
        filterButton.setOnAction(this::handle);
        centerPanelCenterRow.setAlignment(Pos.CENTER_LEFT);
        centerPanelCenterRow.getChildren().addAll(fromDateLabel, fromDate, toDateLabel, toDate, filterButton);

        // Bottom row - add/delete record:
        HBox centerPanelBottomRow = new HBox(10);
        addRecordButton = new Button("Add record");
        addRecordButton.setOnAction(this::handle);
        deleteRecordButton = new Button("Delete record");
        deleteRecordButton.setOnAction(this::handle);
        centerPanelBottomRow.setAlignment(Pos.CENTER_LEFT);
        centerPanelBottomRow.getChildren().addAll(addRecordButton, deleteRecordButton);

        centerPanel.getChildren().addAll(centerPanelTopRow, centerPanelCenterRow, centerPanelBottomRow);

        // BOTTOM PANEL - table view and aggregate stats:
        VBox bottomPanel = new VBox();
        bottomPanel.getChildren().addAll(bottomPanelTableView, aggregateStatsRow);

        // PARENT PANEL:
        leftPanel = new BorderPane();
        leftPanel.setTop(topPanel);
        leftPanel.setCenter(centerPanel);
        leftPanel.setBottom(bottomPanel);

        return leftPanel;
    }

    @Override
    public void handle(Event event) {
        try {
            if (event.getSource() == displayAllButton) {
                bottomPanelTableView = new TableViewRecordsRetriever().getRecords(recordComboBox.getValue().toString(), connection, username, bottomPanelTableView, data);
                aggregateStatsRow = generateAggregateStatsRow();
            }
            if (event.getSource() == filterButton) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMuuuu");
                String fromMMYYYY = fromDate.getValue().format(dateTimeFormatter);
                String toMMYYYY = toDate.getValue().format(dateTimeFormatter);
                bottomPanelTableView = new TableViewRecordsRetriever().getFilteredRecords(recordComboBox.getValue().toString(), connection, username, bottomPanelTableView, data, fromMMYYYY, toMMYYYY);
            }
        }
        catch (SQLException e) {
            PopUp.display("Error", "Something went wrong while retrieving your data.\n[Exception]: " +e+"\n[At]: "+recordComboBox.getValue().toString());
            System.exit(0); // Crash because something's weird...
        }
    }

    private HBox generateAggregateStatsRow() {
        aggregateStatsRow.getChildren().clear();
        aggregateStatsRow.setAlignment(Pos.BOTTOM_RIGHT);
        String recordCount = null;
        try {
            ResultSet r = connection.createStatement().executeQuery(String.format("select count(*) as count_ from %s where username='%s'", recordComboBox.getValue().toString()+"s", username));
            r.next();
            recordCount = r.getString("count_");
        } catch (SQLException e) {
            PopUp.display("Error", "[Error]: "+e);
        }
        aggregateStatsRow.getChildren().add(new Text("Total record count pre-filter: "+recordCount));
        return aggregateStatsRow;
    }
}
