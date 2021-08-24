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

        ResultSet results = databaseConnector.query("SELECT * FROM ");
    }
}
