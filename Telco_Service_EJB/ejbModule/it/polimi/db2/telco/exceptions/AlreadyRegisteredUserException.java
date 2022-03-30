package it.polimi.db2.telco.exceptions;

public class AlreadyRegisteredUserException extends Exception {
	private static final long serialVersionUID = 1L;

	public AlreadyRegisteredUserException(String message) {
		super(message);
	}
}
