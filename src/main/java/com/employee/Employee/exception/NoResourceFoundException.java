package com.employee.Employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoResourceFoundException extends RuntimeException {
    private String field;
    private String message;

    public NoResourceFoundException(String field, String message) {
        super(String.format("Error with field %s: %s", field, message));
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}

