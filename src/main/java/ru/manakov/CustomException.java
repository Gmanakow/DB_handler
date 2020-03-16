package ru.manakov;

public class CustomException extends Exception {
    public enum ExceptionType {
        INPUT_FILE_CANNOT_BE_PARSED,
        SQL_EXCEPTION,
        OUTPUT_FILE_IS_UNREACHABLE
    }
    private String errorLine = null;

    public CustomException(){
        this.errorLine = null;
    }

    public CustomException(String line){
        super();
        this.errorLine = line;
    }

    public CustomException(CustomException.ExceptionType type){
        this.errorLine = type.name().toLowerCase().replace("_", " ");
    }

    public String getErrorLine(){
        return ( " Error " + this.errorLine);
    }
}
