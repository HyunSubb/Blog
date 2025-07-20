package com.hyunsub.Blog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class MyCustomException extends RuntimeException {

    private Map<String, String> validation = new HashMap<>();

    public abstract int getStatusCode();

    public MyCustomException(String message) {
        super(message);
    }

    public MyCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
