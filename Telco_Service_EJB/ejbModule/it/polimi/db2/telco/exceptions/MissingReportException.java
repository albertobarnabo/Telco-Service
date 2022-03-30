package it.polimi.db2.telco.exceptions;

public class MissingReportException extends Exception{
	private static final long serialVersionUID = 1L;

    public MissingReportException(String message) {
        super(message);
    }
}
