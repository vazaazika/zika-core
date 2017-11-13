package br.les.opus.commons.persistence.filtering;

import br.les.opus.commons.persistence.PersistenceException;

public class FilteringException extends PersistenceException {

	private static final long serialVersionUID = 288639216129394506L;

	public FilteringException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilteringException(String message) {
		super(message);
	}

	public FilteringException(Throwable cause) {
		super(cause);
	}
	
}
