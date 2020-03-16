package ru.manakov.operations.search.criterias.minExpense;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.manakov.operations.search.CriteriaHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MinExpenseCriteriaHandler implements CriteriaHandler {
    private JSONObject object = null;

    private final static String minExpenses = "minExpenses";
    private final static String maxExpenses = "maxExpenses";

    private final static String firstName = "firstName" ;
    private final static String lastName  = "lastName"  ;

    private final static String criteria = "criteria";
    private final static String results = "results";

    public MinExpenseCriteriaHandler(JSONObject object){
        this.object = object;
    }

    @Override
    public String getQuery() {
        Long min = (Long) object.get(minExpenses);
        Long max = (Long) object.get(maxExpenses);
        String query = null;

        if ((min != null)&&(max!=null)) {
            query = "select firstName, lastName from ("
                    + "select firstName, lastName, sum (price) as sum from ( "
                    + "select customers.firstName, customers.lastName, merch.price as price from "
                    + " purch join customers on purch.customerId = customers.id join merch on purch.merchId = merch.Id"
                    + ") as purchPrice group by (firstName, lastName)"
                    + ") as purchSum where sum >= " + min + " and sum <= " + max + " ;";
        }
        return query;
    }

    @Override
    public JSONArray parseAnswer(ResultSet resultSet) throws SQLException {
        JSONArray resultArray = new JSONArray();
        JSONArray array = new JSONArray();
        JSONObject criteriaObject = new JSONObject();
        JSONObject resultsObject = new JSONObject();

        criteriaObject.put(criteria, object);

        boolean checkIfNotEmpty = false;

        while (resultSet.next()){
            JSONObject object = new JSONObject();
            object.put(
                    resultSet.getObject(lastName),
                    resultSet.getObject(firstName)
            );
            array.add(object);
            checkIfNotEmpty = true;
        }

        if (checkIfNotEmpty) {
            resultsObject.put(results, array);
        } else
            resultsObject.put(results, "nothing");



        resultArray.add(criteriaObject);
        resultArray.add(resultsObject);

        return resultArray;
    }
}
