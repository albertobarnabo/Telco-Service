package it.polimi.db2.telco.exceptions;

public class NotLoggedUserException extends Exception{
	
	private static final long serialVersionUID = 1L;

    public NotLoggedUserException(String message) {
        super(message);
    }

}
