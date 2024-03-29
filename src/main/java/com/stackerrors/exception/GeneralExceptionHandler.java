package com.stackerrors.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex,
             @NotNull HttpHeaders headers,
             @NotNull HttpStatus status,
             @NotNull WebRequest request)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> genericException(GenericException genericException){
        Map<String , Object> errors = new HashMap<>();
        errors.put("error" , genericException.getErrorCode());
        errors.put("errorMessage" , genericException.getErrorMessage());
        errors.put("httpStatus" , genericException.getHttpStatus());
        return ResponseEntity
                .status(genericException.getHttpStatus())
                .body(errors);
    }





    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception){
        return new ResponseEntity<>(exception.getMessage() , HttpStatus.NOT_FOUND);
    }


}