package ru.manakov.operations;

import ru.manakov.operations.search.SearchHandler;
import ru.manakov.operations.stat.StatHandler;

public class OperationFactory {
    public OperationHandler getOperationHandler(
            String operationType
    ){
        OperationHandler handler = null;

        switch(operationType){
            case(Operation.search):
                handler = new SearchHandler();
                break;
            case(Operation.stat):
                handler = new StatHandler();
                break;
        }

        return handler;
    }
}
