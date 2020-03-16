package ru.manakov.operations.search;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.manakov.CustomException;
import ru.manakov.IOManager;
import ru.manakov.InvalidInputCustomException;
import ru.manakov.database.DBInterface;
import ru.manakov.operations.OperationHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

public class SearchHandler implements OperationHandler {
    private final static String criteria = "criterias";

    private final static String type = "type";
    private final static String search = "search";
    private final static String results = "results";

    private LinkedList<CriteriaHandler> criteriaList = null;
    @Override
    public void handle(IOManager ioManager, DBInterface dbInterface) throws CustomException {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(
                    ioManager.getInputFileReader()
            );
            JSONArray array = (JSONArray) object.get(criteria);
            CriteriaFactory factory = new CriteriaFactory();
            criteriaList = new LinkedList<>();

            Iterator<JSONObject> iterator = array.iterator();
            while(iterator.hasNext()){
                criteriaList.add(
                        factory.getHandler(
                                iterator.next()
                        )
                );
            }
            if (criteriaList.size() == 0){
                throw new CustomException(
                        CustomException.ExceptionType.INPUT_FILE_CANNOT_BE_PARSED
                );
            }
        } catch (ParseException| IOException | CustomException | NullPointerException e){
            throw new CustomException(
                    CustomException.ExceptionType.INPUT_FILE_CANNOT_BE_PARSED
            );
        }

        JSONArray array = new JSONArray();

        JSONObject typeObject = new JSONObject();
        typeObject.put(type, search);

        try {
            for (CriteriaHandler handler : criteriaList) {
                array.add(
                    handler.parseAnswer(
                            dbInterface.getQueryResult(
                                    handler.getQuery()
                            )
                    )
                );
            }
        } catch (SQLException e){
            throw new CustomException(
                    CustomException.ExceptionType.SQL_EXCEPTION
            );
        } catch (NullPointerException e){
            throw new InvalidInputCustomException(
                    InvalidInputCustomException.ExceptionType.INCORRECT_TYPE_OF_OPERATION
            );
        }

        JSONArray finalArray = new JSONArray();
        JSONObject finalObject = new JSONObject();
        finalObject.put(results, array);

        finalArray.add(typeObject);
        finalArray.add(finalObject);


        try {
            ioManager.getOutputFileWriter().write(finalArray.toString());
            ioManager.getOutputFileWriter().flush();
        } catch (IOException e){
            throw new CustomException(
                CustomException.ExceptionType.OUTPUT_FILE_IS_UNREACHABLE
            );
        }

    }
}
