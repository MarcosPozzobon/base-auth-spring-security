package com.marcos.desenvolvimento.exceptions;

public class InvalidObjectException extends RuntimeException{

    public InvalidObjectException(){}

    public InvalidObjectException(String msg){
        super(msg);
    }
}
