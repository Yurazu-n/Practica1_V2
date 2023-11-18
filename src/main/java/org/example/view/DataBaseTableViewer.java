package org.example.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseTableViewer {
    public void dataTableViewer(Statement statement, String database) throws SQLException {
        String sqlQuery = "SELECT * FROM " + database;
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.printf("%-15s", resultSet.getMetaData().getColumnName(i));
        }
        System.out.println();

        while (resultSet.next()) {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();
        }
    }
}
