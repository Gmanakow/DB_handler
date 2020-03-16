package ru.manakov.operations.stat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryArrayHandler {
    private final static String firstName = "firstName" ;
    private final static String lastName  = "lastName"  ;
    private final static String title     = "title" ;
    private final static String expenses  = "expenses"  ;
    private final static String id  = "id"  ;

    private final static String name  = "name"  ;
    private final static String purchases  = "purchases"  ;

    public String getQuery(String start, String end){
        String query = "select firstName, lastName, id, title, sum(price) as expenses from ("
                + "select firstName, lastName, id, title, price, timeDate from ("
                + "select firstName, lastName, customerId as id, title, price, timeDate, extract ( DOW from timeDate) as day from ("
                + "purch join customers on purch.customerId = customers.id join merch on purch.merchId = merch.id"
                + ")"
                + ") as weekday where day !=0 and day!=6"
                + "and timeDate > '"+ start +"' and timeDate < '"+ end +"'"
                + ") as bordered group by (firstName, lastName, title, id)"
                + "Order by id asc, expenses desc;";
        return query;
    }

    public JSONArray getArray(ResultSet set) throws SQLException {
        JSONArray result = new JSONArray();

        if (!set.next()){
            return null;
        }

        int currentID = (Integer) set.getObject(id);
        JSONObject nameObject = new JSONObject();
        nameObject.put(name, set.getObject(lastName) + " " + set.getObject(firstName));

        JSONObject purchObject = new JSONObject();

        JSONArray array = new JSONArray();
        array.add(nameObject);

        JSONArray purchArray = new JSONArray();
        while (true) {
            JSONArray titleExpense = new JSONArray();

            JSONObject titleObject = new JSONObject();
            titleObject.put(title, set.getObject(title));

            JSONObject expenseObject = new JSONObject();
            expenseObject.put(expenses, set.getObject(expenses));

            titleExpense.add(titleObject);
            titleExpense.add(expenseObject);

            purchArray.add(titleExpense);

            if (set.next()) {
                if (currentID != (Integer) set.getObject(id)) {
                    purchObject = new JSONObject();
                    purchObject.put(purchases, purchArray);
                    purchArray = new JSONArray();
                    array.add(purchObject);
                    result.add(array);
                    currentID = (Integer) set.getObject(id);

                    nameObject = new JSONObject();
                    nameObject.put(name, set.getObject(lastName) + " " + set.getObject(firstName));

                    array = new JSONArray();
                    array.add(nameObject);
                }
            } else {
                break;
            }
        }
        purchObject = new JSONObject();
        purchObject.put(purchases, purchArray);
        array.add(purchObject);
        result.add(array);
        return result;
    }
}
