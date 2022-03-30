package it.polimi.db2.telco.exceptions;

public class ServicePackageNotAvailableException extends Exception{
	
	private static final long serialVersionUID = 1L;

    public ServicePackageNotAvailableException(String message) {
        super(message);
    }
	
}
