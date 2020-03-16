package ru.manakov.operations.stat;

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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StatHandler implements OperationHandler {
    private final static String type  = "type"  ;
    private final static String stat  = "stat"  ;

    private final static String totalDays = "totalDays";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final static String startDate = "startDate";
    private final static String endDate   = "endDate";


    private LocalDate start = null;
    private LocalDate end   = null;

    private String query = null;

    @Override
    public void handle(IOManager ioManager, DBInterface dbInterface) throws CustomException {
        JSONParser parser = new JSONParser();

        String dateStart = null;
        String dateEnd   = null;

        try{
            JSONObject object = (JSONObject) parser.parse(
                    ioManager.getInputFileReader()
            );
            dateStart = (String) object.get( startDate );
            dateEnd   = (String) object.get( endDate   );

        } catch (ParseException | IOException e){
            throw new CustomException(
                    CustomException.ExceptionType.INPUT_FILE_CANNOT_BE_PARSED
            );
        }

        try {
                start = LocalDate.parse( dateStart, formatter );
                end   = LocalDate.parse( dateEnd,   formatter );

                if (end.isBefore(start)) throw new InvalidInputCustomException(
                        InvalidInputCustomException.ExceptionType.INCORRECT_DATA_INPUT
                );
        } catch (DateTimeParseException| NullPointerException | InvalidInputCustomException e){
            throw new InvalidInputCustomException(
                    InvalidInputCustomException.ExceptionType.INCORRECT_DATA_INPUT
            );
        }

        QueryArrayHandler qahandler = new QueryArrayHandler();
        query = qahandler.getQuery(
                start.format(formatter),
                end.format(formatter)
        );

        JSONArray result = new JSONArray();

        JSONObject typeObject = new JSONObject();
        typeObject.put(type, stat);
        result.add(typeObject);

        JSONObject totalDaysObject = new JSONObject();
        totalDaysObject.put(totalDays, getWorkDays(start,end));
        result.add(totalDaysObject);

        JSONObject customersObject = null;

        try {
            customersObject = new JSONObject();
            customersObject.put(
                    "customers",
                    qahandler.getArray(
                        dbInterface.getQueryResult(
                            query
                    )
                )
            );
            result.add(customersObject);
        } catch (SQLException | NullPointerException e){
            throw new CustomException(
                    CustomException.ExceptionType.SQL_EXCEPTION
            );
        }

        try {
            ioManager.getOutputFileWriter().write(result.toString());
            ioManager.getOutputFileWriter().flush();
        } catch (IOException e){
            throw new CustomException(
                    CustomException.ExceptionType.OUTPUT_FILE_IS_UNREACHABLE
            );
        }
    }

    public int getWorkDays(LocalDate start, LocalDate end){
        LocalDate curr = start;
        int counter = 0;
        while (curr.isBefore(end) || curr.isEqual(end)){
            DayOfWeek dow = curr.getDayOfWeek();
            if (dow != DayOfWeek.SUNDAY && dow != DayOfWeek.SATURDAY){
                counter++;
            }
            curr = curr.plusDays(1);
        }
        return counter;
    }


}

