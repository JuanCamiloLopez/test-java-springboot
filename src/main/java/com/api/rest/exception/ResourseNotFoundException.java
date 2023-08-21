package com.api.rest.exception;

public class ResourseNotFoundException extends  RuntimeException{

    public ResourseNotFoundException(String messege) {
        super(messege);
    }
}
