package database;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Budget {

    private String month, year, amount;
    private List<String> budgetCategories;

    private Budget(String month, String year, String amount, List<String> budgetCategories) {
        this.month = month;
        this.year = year;
        this.amount = amount;
        this.budgetCategories = budgetCategories;
    }

    public String getMonth() { return month; }
    public String getYear() { return year; }
    public String getAmount() { return amount; }
    public List<String> getBudgetCategories() { return budgetCategories; }


    public static TableView getBudgetTableView(Connection connection, String username, TableView tableView, ObservableList data) throws SQLException {
        String query = String.format("select budget_month, budget_year, amount from budgets where username='%s'", username);
        return retrieveData(query, connection, username, tableView, data);
    }

    public static TableView getFilteredBudgetTableView(Connection connection, String username, TableView tableView, ObservableList data, String fromMMYYYY, String toMMYY) throws SQLException {
        String fromMonth, toMonth, fromYear, toYear;
        fromMonth = fromMMYYYY.substring(0,2); // MM
        toMonth = toMMYY.substring(0,2);
        fromYear = fromMMYYYY.substring(2,6); // YYYY
        toYear = toMMYY.substring(2,6);
        String query = String.format("select budget_month, budget_year, amount from budgets where username='%s' and budget_month >= '%s' and budget_month <= '%s' and budget_year >= '%s' and budget_year <= 's'", username, fromMonth, toMonth, fromYear, toYear);
        return retrieveData(query, connection, username, tableView, data);
    }

    private static List<String> setBudgetCategories(Connection connection, String username, String month, String year) throws SQLException {
        ArrayList budgetCategories = new ArrayList<>();
        Statement s = connection.createStatement();
        ResultSet r = s.executeQuery(String.format("select budget_name from budget_categories where budget_month='%s' and budget_year='%s' and username='%s'", month, year, username));
        while(r.next()) {
            budgetCategories.add(r.getString("budget_name"));
        }
        return budgetCategories;
    }

    private static TableView retrieveData(String query, Connection connection, String username, TableView tableView, ObservableList data) throws SQLException {

        // Clear old data...
        data.clear();

        // Get the raw data and advance the ResultSet pointer to the first row...
        Statement s = connection.createStatement();
        ResultSet r = s.executeQuery(query);

        // Add each row...
        while (r.next()) {
            String month = r.getString("budget_month");
            String year = r.getString("budget_year");
            data.add(new Budget(month, year, r.getString("amount"), Budget.setBudgetCategories(connection, username, month, year)));
        }

        // Setup the columns...
        TableColumn monthColumn = new TableColumn("Month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("month"));
        TableColumn yearColumn = new TableColumn("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("year"));
        TableColumn amountColumn = new TableColumn("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("amount"));
        TableColumn categoriesColumn = new TableColumn("Categories");
        categoriesColumn.setCellValueFactory(new PropertyValueFactory<Budget, List<String>>("budgetCategories"));

        tableView.getColumns().setAll(monthColumn, yearColumn, amountColumn, categoriesColumn);
        tableView.setItems(data);

        return tableView;
    }
}
