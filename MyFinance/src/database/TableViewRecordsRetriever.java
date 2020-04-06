package database;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.SQLException;

public class TableViewRecordsRetriever {

    public TableView getRecords(String table, Connection connection, String username, TableView tableView, ObservableList data) throws SQLException {

        data.clear(); // Clear old data...
        switch (table) {
            case ("Budget"):
                tableView = Budget.getBudgetTableView(connection, username, tableView, data);
        }

        // Return the newly updated panel...
        return tableView;
    }

    public TableView getFilteredRecords(String table, Connection connection, String username, TableView tableView, ObservableList data, String fromMMYYY, String toMMYYY) throws SQLException {
        data.clear(); // Clear old data...
        switch (table) {
            case ("Budget"):
                tableView = Budget.getFilteredBudgetTableView(connection, username, tableView, data, fromMMYYY, toMMYYY);
        }

        // Return the newly updated panel...
        return tableView;
    }
}

