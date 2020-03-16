package ru.manakov;

import ru.manakov.operations.Operation;

public class ArgParser {

    public void excludeInputErrors(String[] args) throws CustomException{
        if (args.length !=3 && args.length != 8) {
            throw new InvalidInputCustomException(
                    InvalidInputCustomException.ExceptionType.WRONG_NUMBER_OF_ARGUMENTS
            );
        } else {
            switch (args[0]) {
                case (Operation.search) :
                case (Operation.stat) :
                    return;
                default:
                    throw new InvalidInputCustomException(
                            InvalidInputCustomException.ExceptionType.INCORRECT_TYPE_OF_OPERATION
                    );
            }
        }
    }


}
