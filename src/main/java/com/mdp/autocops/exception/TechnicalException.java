package com.mdp.autocops.exception;

import org.springframework.http.HttpStatus;


public class TechnicalException extends ApiGlobalException {

    private String errorCode;
    private String internalErrorCode;
    private String errorDescription;

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, String internalErrorCode, String errorDescription) {
        super(message);
        this.internalErrorCode = internalErrorCode;
        this.errorDescription = errorDescription;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getInternalErrorCode() {
        return internalErrorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}