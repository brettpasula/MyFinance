package ui.HomePageComponents;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRecordsRetriever {

    public TableView getRecords(String table, Connection connection, String username, TableView bottomPanel, ObservableList data) throws SQLException {

        data.clear(); // Clear old data...
        switch (table) {
            case ("Budget"):
                System.out.println("Data: " + data);
                // Get the raw data and advance the ResultSet pointer to the first row...
                Statement s = connection.createStatement();
                ResultSet r = s.executeQuery(String.format("select budget_month, budget_year, amount from budgets where username='%s'", username));
                r.next();

                // Add each row to temporary List<Budget>...
                while (r.next()) {
                    data.add(new Budget(r.getString("budget_month"), r.getString("budget_year"), r.getString("amount")));
                }

                // Setup the columns...
                TableColumn monthColumn = new TableColumn("Month");
                monthColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("month"));
                TableColumn yearColumn = new TableColumn("Year");
                yearColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("year"));
                TableColumn amountColumn = new TableColumn("Amount");
                amountColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("amount"));
                bottomPanel.getColumns().setAll(monthColumn, yearColumn, amountColumn);
        }

        // Send the data to the TableView and refresh...
        bottomPanel.setItems(data);
        return bottomPanel;
    }

    public static class Budget {
        private String month, year, amount;

        private Budget(String month, String year, String amount) {
            this.month = month;
            this.year = year;
            this.amount = amount;
        }

        public String getMonth() { return month; }
        public String getYear() { return year; }
        public String getAmount() { return amount; }
    }
}

