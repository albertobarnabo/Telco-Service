package it.polimi.db2.telco.exceptions;

public class InvalidOrderException extends Exception{

	private static final long serialVersionUID = 1L;

    public InvalidOrderException(String message) {
        super(message);
    }
	
}
