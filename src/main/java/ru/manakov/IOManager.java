package ru.manakov;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class IOManager {
    private BufferedReader inputFileReader    = null;
    private BufferedWriter outputFileWriter   = null;

    private final static String type = "type";
    private final static String error = "error";
    private final static String message = "message";

    private final static String charsetName = "UTF-8";

    public void setIOFiles(
            String inputFileName,
            String outputFileName
    )throws CustomException{
        try{
            setInputFileReader(inputFileName);
            setOutputFileWriter(outputFileName);
        } catch (CustomException e){
            inputFileReader = null;
            outputFileWriter = null;
            throw e;
        }
    }

    private void setInputFileReader(String fileName) throws CustomException{
        try {
            inputFileReader  = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    fileName
                            ),
                            charsetName
                    )
            );
        } catch (IOException e){
            throw new CustomException(
                    CustomException.ExceptionType.INPUT_FILE_CANNOT_BE_PARSED
            );
        }
    }
    private void setOutputFileWriter(String fileName) throws CustomException{
        try {
            outputFileWriter  = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    fileName
                            ),
                            charsetName
                    )
            );
        } catch (IOException e){
            throw new CustomException(
                    CustomException.ExceptionType.INPUT_FILE_CANNOT_BE_PARSED
            );
        }
    }

    public BufferedReader getInputFileReader() {
        return inputFileReader;
    }
    public BufferedWriter getOutputFileWriter() {
        return outputFileWriter;
    }

    public void nullify(){
        this.inputFileReader  = null;
        this.outputFileWriter = null;
    }


    public void writeError(String errorLine){
        if (outputFileWriter!= null){
            JSONArray errorArray = new JSONArray();
            JSONObject object = new JSONObject();
            object.put(type, error);
            object.put(message,errorLine);
            errorArray.add(object);
            try {
                outputFileWriter.write(errorArray.toString());
                outputFileWriter.flush();
            } catch (IOException e){}
        }
    }
}
