package com.charlie.exceptions;

public class ApplicationException extends BaseException {

    public ApplicationException(String messageCode) {
        super(messageCode);
    }

    public ApplicationException(Exception ex) {
        super("exception.unknown", ex);
    }

    public ApplicationException(String messageCode, Exception ex) {
        super(messageCode, ex);
    }

}