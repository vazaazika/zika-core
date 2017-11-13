package br.les.opus.instagram.api;

public class InstagramException extends RuntimeException {

	private static final long serialVersionUID = 2226443321795972398L;

	public InstagramException(String message, Throwable cause) {
		super(message, cause);
	}

	public InstagramException(String message) {
		super(message);
	}

	public InstagramException(Throwable cause) {
		super(cause);
	}
}
