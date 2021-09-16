package com.rmit.sept.bk_loginservices.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class DatabaseConnector {

    public ResultSet query(String query) throws java.sql.SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sept-db.cvy7szpnhyfp.us-east-1.rds.amazonaws.com:5432/sept_moving_houses?user=postgres&password=postgres5")) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void runCommand(String query) throws java.sql.SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sept-db.cvy7szpnhyfp.us-east-1.rds.amazonaws.com:5432/sept_moving_houses?user=postgres&password=postgres")) {
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
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sept-db.cvy7szpnhyfp.us-east-1.rds.amazonaws.com:5432/sept_moving_houses?user=postgres&password=postgres")) {
            Statement statement = connection.createStatement();
            String query = usingBufferedReader("BackEnd/loginmicroservices/src/main/resources/schema-postgres.sql");

            statement.execute(query);
//            while(rs.next()){
//                rs.getString(2);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String usingBufferedReader(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
 
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}