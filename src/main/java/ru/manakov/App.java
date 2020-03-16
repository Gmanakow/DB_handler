package ru.manakov;

import ru.manakov.database.DBInterface;
import ru.manakov.operations.OperationFactory;
import ru.manakov.operations.OperationHandler;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {
        ArgParser parser = new ArgParser();
        IOManager ioManager = new IOManager();
        DBInterface dbInterface = null;
        try{
            parser.excludeInputErrors(args);
            ioManager.setIOFiles(args[1], args[2]);

            if (args.length == 8){
                dbInterface = new DBInterface(
                        args[3], args[4], args[5], args[6], args[7]
                );
            } else dbInterface = new DBInterface();

            OperationHandler operationHandler = (new OperationFactory()).getOperationHandler(args[0]);
            operationHandler.handle(ioManager, dbInterface);
        } catch (CustomException e){
            ioManager.writeError(e.getErrorLine());
        }
        ioManager.nullify();

    }


}
