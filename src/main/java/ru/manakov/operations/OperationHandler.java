package ru.manakov.operations;

import ru.manakov.CustomException;
import ru.manakov.IOManager;
import ru.manakov.database.DBInterface;

public interface OperationHandler {
    public void handle(IOManager ioManager, DBInterface dbInterface) throws CustomException;
}
