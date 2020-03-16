package ru.manakov.operations.search;

import org.json.simple.JSONObject;
import ru.manakov.CustomException;
import ru.manakov.operations.search.criterias.badCustomers.BadCustomersCriteriaHandler;
import ru.manakov.operations.search.criterias.lastName.LastNameCriteriaHandler;
import ru.manakov.operations.search.criterias.minExpense.MinExpenseCriteriaHandler;
import ru.manakov.operations.search.criterias.productName.ProductNameCriteriaHandler;

import java.util.Set;

public class CriteriaFactory {
    private final static class Criteria{
        public final static String lastName = "lastName";
        public final static String productName = "productName";
        public final static String minExpenses = "minExpenses";
        public final static String badCustomers = "badCustomers";
    }

    public CriteriaHandler getHandler(JSONObject object) throws CustomException{
        for (String line : (Set<String>) object.keySet()) {
            switch (line) {
                case (Criteria.lastName) :
                    return new LastNameCriteriaHandler      (object);
                case (Criteria.productName) :
                    return new ProductNameCriteriaHandler   (object);
                case (Criteria.minExpenses) :
                    return new MinExpenseCriteriaHandler    (object);
                case (Criteria.badCustomers) :
                    return new BadCustomersCriteriaHandler  (object);
           }
        }
        throw new CustomException(
                CustomException.ExceptionType.INPUT_FILE_CANNOT_BE_PARSED
        );
    }
}
