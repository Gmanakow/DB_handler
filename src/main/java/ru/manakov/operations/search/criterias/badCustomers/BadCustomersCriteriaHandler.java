package ru.manakov.operations.search.criterias.badCustomers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.manakov.operations.search.CriteriaHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BadCustomersCriteriaHandler implements CriteriaHandler {
    private JSONObject object = null;

    private final static String badCustomers  = "badCustomers"  ;

    private final static String firstName = "firstName" ;
    private final static String lastName  = "lastName"  ;

    private final static String criteria = "criteria";
    private final static String results = "results";

    public BadCustomersCriteriaHandler(JSONObject object){
        this.object = object;
    }

    @Override
    public String getQuery() {
        String query = "select firstName, lastName, count (id) as count from ("
            + "select customers.firstName, customers.lastName, purch.id as id "
                + "from purch join customers on purch.customerId = customers.id"
        + ") as customersPurch group by (firstName, lastName) order by count ASC limit " + object.get(badCustomers) +" ;";
        return query;
    }

    @Override
    public JSONArray parseAnswer(ResultSet resultSet) throws SQLException {
        JSONArray resultArray = new JSONArray();
        JSONArray array = new JSONArray();
        JSONObject criteriaObject = new JSONObject();
        JSONObject resultsObject = new JSONObject();

        boolean checkIfNotEmpty = false;

        criteriaObject.put(criteria, object);

        while (resultSet.next()){
            checkIfNotEmpty = true;
            JSONObject object = new JSONObject();
            object.put(lastName, resultSet.getObject(lastName));
            object.put(firstName,resultSet.getObject(firstName));
            array.add(object);
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
