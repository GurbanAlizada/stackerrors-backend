package com.stackerrors.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericException extends RuntimeException{

    private HttpStatus httpStatus;
    private ErrorCode errorCode;
    private String errorMessage;

    public GenericException(HttpStatus httpStatus , ErrorCode errorCode  ){
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }



    // getter and setter
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
