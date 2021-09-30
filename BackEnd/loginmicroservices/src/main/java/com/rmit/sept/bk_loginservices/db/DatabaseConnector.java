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

    public static ResultSet query(String query) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sept-db.cvy7szpnhyfp.us-east-1.rds.amazonaws.com:5432/sept_moving_houses?user=postgres&password=postgres5")) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
        if (args.length > 0 && args[0].equals("-r")) {
        }
        resetDatabase();
        //printDatabase();
    }
    
    public static void resetDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sept-db.cvy7szpnhyfp.us-east-1.rds.amazonaws.com:5432/sept_moving_houses?user=postgres&password=postgrespassword")) {
            Statement statement = connection.createStatement();
            statement.execute(usingBufferedReader("BackEnd/loginmicroservices/src/main/resources/schema-postgres.sql"));
            System.out.println("schema success");
            statement.execute(usingBufferedReader("BackEnd/loginmicroservices/src/main/resources/data-postgres.sql"));
            System.out.println("data success");
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://sept-db.cvy7szpnhyfp.us-east-1.rds.amazonaws.com:5432/sept_moving_houses?user=postgres&password=postgrespassword")) {
            String[] tables = new String[]{"users", "business_users", "books", "transactions", "book_images", "requests", "book_reviews", "incentives", "user_incentive"};

            Statement statement = connection.createStatement();
            for (String table : tables) {
                printTable(table, statement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printTable(String table, Statement statement) throws java.sql.SQLException {
        System.out.println(table);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);
        int colNum = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i < colNum + 1; i++) {
                System.out.print(resultSet.getString(i) + " ");
            }
            System.out.println();
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