package it.polimi.db2.telco.exceptions;

public class EmptyCredentialsException extends Exception {
    private static final long serialVersionUID = 1L;

    public EmptyCredentialsException(String message) {
        super(message);
    }
}