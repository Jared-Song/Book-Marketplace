package com.rmit.sept.bk_loginservices.db;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

public class DatabaseConnector {

    public ResultSet query(String query) throws java.sql.SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sql6.freemysqlhosting.net:3306/sql6431875", "sql6431875", "NVbbclqYc9")) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void runCommand(String query) throws java.sql.SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sql6.freemysqlhosting.net:5432/sql6431875", "sql6431875", "i76-tfo9YWGAWwzzhYelRwVOSQ3kccnd")) {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Incorrect sql command");
        }
    }


    public List<List<String>> loadTable(String table) throws java.sql.SQLException {
        ResultSet resultSet = query("SELECT * FROM " + table);
        List<String> row;
        List<List<String>> itemTable = new ArrayList<List<String>>();
        int colNum = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            row = new ArrayList<>();
            for (int i = 1; i < colNum + 1; i++) {
                row.add(resultSet.getString(i));
            }
            itemTable.add(row);
        }
        return itemTable;
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://topsy.db.elephantsql.com:5432/ppjpkqmd", "ppjpkqmd", "i76-tfo9YWGAWwzzhYelRwVOSQ3kccnd")) {
            Statement statement = connection.createStatement();
            String query = Files.readString(Path.of("tables.sql"));
            statement.execute(query);
            System.out.println(query);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}