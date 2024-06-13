package com.employee.Employee.exception;

import com.employee.Employee.response.Response;
import com.employee.Employee.service.EmployeeServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobleExceptionHandler {
    private static final Logger logger = LogManager.getLogger(EmployeeServiceImp.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> notFoundExceptionHandler(NotFoundException notFoundException){
        logger.error(notFoundException.getMessage());
        Response response = new Response(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response> noResourceFoundExceptionHandler(NoResourceFoundException noResourceFoundException) {
        logger.error(noResourceFoundException.getMessage());
        Response response = new Response(noResourceFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            logger.error("validation error fieldName : "+fieldName+" Message : "+errorMessage);
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception exception){
        Response response = new Response(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        logger.error(exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException){
        Response response = new Response(httpMessageNotReadableException.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
