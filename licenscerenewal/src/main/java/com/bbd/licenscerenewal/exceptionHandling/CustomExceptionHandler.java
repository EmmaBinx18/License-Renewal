package com.bbd.licenscerenewal.exceptionHandling;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> getRuntimeExceptionData(RuntimeException exception) {
        ErrorMessage error = new ErrorMessage(500, "Something has gone wrong. Please contact your service provider");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMessage> getHttpClientErrorData(HttpClientErrorException ex) {
        ErrorMessage error;
        switch (ex.getRawStatusCode()) {
            case 400:
                error = new ErrorMessage(400, "Bad request");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            case 401:
                error = new ErrorMessage(401, "Unauthorized");
                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            case 403:
                error = new ErrorMessage(403, "Forbidden");
                return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
            case 404:
                error = new ErrorMessage(404, "Resource not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            case 405:
                error = new ErrorMessage(405, "Method not allowed");
                return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
            case 422:
                error = new ErrorMessage(422, "Unprocessable Entity");
                return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            default:
                break;
        }
        return null;
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorMessage> getHttpServerError(HttpServerErrorException ex) {
        ErrorMessage error = new ErrorMessage(ex.getRawStatusCode(), "Internal Server Error");
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessage> getSQLError(SQLException ex) {
        ErrorMessage error = new ErrorMessage(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLTimeoutException.class)
    public ResponseEntity<ErrorMessage> getSQLError(SQLTimeoutException ex) {
        ErrorMessage error = new ErrorMessage(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

}