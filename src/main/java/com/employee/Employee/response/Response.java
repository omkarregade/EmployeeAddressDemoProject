package com.employee.Employee.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    public String message;
    public HttpStatus httpStatus;

    public Response(){

    }

    public Response(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
