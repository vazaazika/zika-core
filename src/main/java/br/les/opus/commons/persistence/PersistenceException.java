package br.les.opus.commons.persistence;

public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = 7313490068352604608L;

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(Throwable cause) {
		super(cause);
	}
	
}
