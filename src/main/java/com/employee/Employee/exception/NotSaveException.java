package com.employee.Employee.exception;

public class NotSaveException extends RuntimeException{
    public NotSaveException() {
    }

    public NotSaveException(String message) {
        super(message);
    }
}
