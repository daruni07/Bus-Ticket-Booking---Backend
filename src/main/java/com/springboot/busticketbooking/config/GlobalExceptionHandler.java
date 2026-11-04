package com.springboot.busticketbooking.config;

import com.springboot.busticketbooking.dto.response.ErrorDto;
import com.springboot.busticketbooking.exception.InactiveUserException;
import com.springboot.busticketbooking.exception.InvalidCallException;
import com.springboot.busticketbooking.exception.OwnershipException;
import com.springboot.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ControllerAdvice
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger("GlobalExceptionHandler.class");
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        //reply the obj not the string
        return ResponseEntity.badRequest()
                .body(new ErrorDto(e.getMessage(), "ID not found in db", Instant.now()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        Map<String,String> map=new HashMap<>();
        BindingResult bindingResult=e.getBindingResult();
        List<FieldError> list=bindingResult.getFieldErrors();
        list.forEach(fieldError -> {
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                    log.warn("Field {} , failed validation: {}", fieldError.getField(),fieldError.getDefaultMessage());
                }
        );

        return ResponseEntity
                .badRequest()
                .body(map);
    }
    @ExceptionHandler(InvalidCallException.class)
    public ResponseEntity<ErrorDto> handleInvalidCallException(
            InvalidCallException e
    ){
        //always reply the object not the string

        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorDto(e.getMessage(),
                                "Call is Invalid due to malformed data",
                                Instant.now())
                );
    }
    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<ErrorDto> handleOwnershipException(
            OwnershipException e
    ){
        //always reply the object not the string

        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorDto(e.getMessage(),
                                "Logged-in User cannot perform this operation",
                                Instant.now())
                );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(
            Exception e
    ){
        //always reply the object not the string

        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorDto(e.getMessage(),
                                "Invalid data passed",
                                Instant.now())
                );
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDto> handleIOException(
            IOException e
    ){
        //always reply the object not the string

        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(
                        new ErrorDto(e.getMessage(),
                                "File is mandatory",
                                Instant.now())
                );
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerException(
            NullPointerException e
    ){
        //always reply the object not the string

        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(
                        new ErrorDto(e.getMessage(),
                                "File is mandatory",
                                Instant.now())
                );
    }
    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<ErrorDto> handleInactiveUserException(
            InactiveUserException e
    ){
        //always reply the object not the string

        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(
                        new ErrorDto(e.getMessage(),
                                "Contact ADMIN admin@helpdesk.com",
                                Instant.now())
                );
    }
}
