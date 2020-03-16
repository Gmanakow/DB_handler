package ru.manakov.database;

import ru.manakov.CustomException;

import java.sql.*;

public class DBInterface {
    private String suffix = "jdbc:postgresql://";
    private String address = "localhost";
    private String port = "5432";
    private String dbName = "purch_db";
    private String user = "postgres";
    private String password = "Ywk3lila";

    public DBInterface(
            String address,
            String port,
            String dbName,
            String user,
            String password
    ){
        this.address = address;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    public DBInterface(){
    }

    private String buildURL(){
        return (suffix + address + ":" + port + "/" + dbName);
    }

    public ResultSet getQueryResult(String query) throws CustomException{
        ResultSet resultSet = null;
        try {
            String url = buildURL();
            Connection connection = DriverManager.getConnection(
                    url,
                    user,
                    password
            );
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    query
            );
        } catch (SQLException e){
            throw new CustomException(
                    e.getMessage()
            );
        }
        return resultSet;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
