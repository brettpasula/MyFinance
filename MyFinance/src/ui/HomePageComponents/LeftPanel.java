package ui.HomePageComponents;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.PopUp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeftPanel implements EventHandler {
    Connection connection;
    Stage primaryStage;
    String username;
    ComboBox monthComboBox;
    Button generateSummaryButton;
    GridPane summaryBox = new GridPane();

    public BorderPane leftPanel(Connection connection, Stage primaryStage, String username) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.username = username;
        summaryBox.setPadding(new Insets(25, 0, 25, 0));
        summaryBox.setVgap(10);
        summaryBox.setHgap(20);

        VBox topPanel = new VBox(25);
        Text myFinanceSummary = new Text("MyFinance summary");
        myFinanceSummary.setFont(new Font(20)); 
        
        // Month selection row:
        HBox monthSelectionRow = new HBox(10);
        monthSelectionRow.setAlignment(Pos.CENTER_LEFT);
        Label monthLabel = new Label("Month:");
        monthComboBox = new ComboBox();
        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        generateSummaryButton = new Button("Generate");
        generateSummaryButton.setOnAction(this::handle);
        monthSelectionRow.getChildren().addAll(monthLabel, monthComboBox, generateSummaryButton);
        topPanel.getChildren().addAll(myFinanceSummary, monthSelectionRow);

        BorderPane panel = new BorderPane();
        panel.setTop(topPanel);
        panel.setCenter(summaryBox);
        panel.setBottom(createSameCorporationsBox());

        return panel;
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == generateSummaryButton) {
            try {
                createSummaryBox();
            } catch (SQLException e) {
                PopUp.display("No Records Found", "We couldn't find any records for that month.\n[ERROR]: "+e);
            }
        }
    }

    private void createSummaryBox() throws SQLException {
        summaryBox.getChildren().clear();
        String summaryMonth = monthComboBox.getValue().toString();
        Text assets = new Text();
        ResultSet totalAssetValue = connection.createStatement().executeQuery(String.format("select sum(account_value)+sum(investment_value)+sum(item_value) as total_asset_value from accounts a, investments i, physical_items p where a.username='%s' and i.username='%s' and p.username='%s'", username, username, username));
        totalAssetValue.next();
        assets.setText("$"+totalAssetValue.getString("total_asset_value"));
        Text bills = new Text();
        ResultSet monthlyBillsTotal = connection.createStatement().executeQuery(String.format("select mb.amount_due from (select to_char(b.due_date, 'MON') as mon, sum(amount_due) as amount_due from ( select * from bills where username = '%s') b group by to_char(b.due_date, 'MON')) mb where mb.mon='%s'", username, summaryMonth.substring(0,3).toUpperCase()));
        monthlyBillsTotal.next();
        bills.setText("$" + monthlyBillsTotal.getString("amount_due"));
        Label assetsLabel = new Label("Total asset value");
        summaryBox.add(assetsLabel, 0, 0);
        summaryBox.add(assets, 1, 0);
        Label billsLabel = new Label(summaryMonth + "'s bills");
        summaryBox.add(billsLabel, 0, 1);
        summaryBox.add(bills, 1, 1);
    }

    private VBox createSameCorporationsBox()  {
        VBox sameCorporationBox = new VBox();
        try {
            Text sameCorporationsTitle = new Text("These users have shopped with their credit cards at the same corporations as you!");
            ResultSet r = connection.createStatement().executeQuery(String.format("select u.first_name, u.last_name from my_finance_users u where u.username <> '%s' and not exists (select c.corporation_id from credit_transactions c where not exists (select distinct corporation_id from (select * from credit_transactions where username='%s')))", username, username));
            ListView users = new ListView();
            HBox padding = new HBox();
            padding.setMinHeight(10);
            while (r.next()) {
                users.getItems().add(r.getString("first_name")+" "+r.getString("last_name"));
            }
            sameCorporationBox.getChildren().addAll(sameCorporationsTitle, padding, users);
        } catch (SQLException e) {
            PopUp.display("Error", e.getMessage());
        }
        return sameCorporationBox;
    }
}
