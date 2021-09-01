package com.rmit.sept.bk_loginservices.dao;

import com.rmit.sept.bk_loginservices.db.DatabaseConnector;
import com.rmit.sept.bk_loginservices.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO{

    private List<User> users;
    private DatabaseConnector databaseConnector;

    public UserDAO(DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;
        try {
            loadData();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() throws SQLException {
        users = new ArrayList<User>();

        ResultSet rs = databaseConnector.query("SELECT * FROM users");
        while (rs.next()) {
            add(rs.getLong("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getInt("rating"), rs.getInt("rating_no"), rs.getString("address"));
        }
    }

    public void add(Long id, String username, String password, String email, String first_name, String middle_name, String last_name, int rating, int rating_no, String address) {
        users.add(new User(id, username, password, email, first_name, middle_name, last_name, rating, rating_no, address));
    }

}
