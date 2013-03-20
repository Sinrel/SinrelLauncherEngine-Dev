package org.sinrel.engine.exception;

public class SLException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SLException() {
        super();
    }

    public SLException(String message) {
        super(message);
    }

    public SLException(String message, Throwable cause) {
        super(message, cause);
    }

    public SLException(Throwable cause) {
        super(cause);
    }

}
