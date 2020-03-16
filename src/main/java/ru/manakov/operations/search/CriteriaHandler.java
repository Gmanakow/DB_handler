package ru.manakov.operations.search;

import org.json.simple.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CriteriaHandler {

    public String getQuery();

    public JSONArray parseAnswer(
            ResultSet resultSet
    ) throws SQLException;
}
