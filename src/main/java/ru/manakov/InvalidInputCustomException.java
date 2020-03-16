package ru.manakov;

public class InvalidInputCustomException extends CustomException {
    public enum ExceptionType {
        WRONG_NUMBER_OF_ARGUMENTS,
        INCORRECT_TYPE_OF_OPERATION,
        INCORRECT_DATA_INPUT,
    }

    private String errorLine = null;

    public InvalidInputCustomException(String line){
        super();
        this.errorLine = line;
    }

    public InvalidInputCustomException(ExceptionType type){
        super();
        this.errorLine = type.name().toLowerCase().replace("_", " ");
    }

    public String getErrorLine(){
        return ( " Invalid input: " + this.errorLine + ";");
    }
}
