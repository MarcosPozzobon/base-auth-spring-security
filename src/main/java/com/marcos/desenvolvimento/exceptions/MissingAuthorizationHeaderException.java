package com.marcos.desenvolvimento.exceptions;

public class MissingAuthorizationHeaderException extends RuntimeException{

    public MissingAuthorizationHeaderException(){}

    public MissingAuthorizationHeaderException(String msg){
        super(msg);
    }

}
