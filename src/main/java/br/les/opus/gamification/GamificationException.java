package br.les.opus.gamification;

public class GamificationException extends RuntimeException {
	private static final long serialVersionUID = -101170041415323593L;

	public GamificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public GamificationException(String message) {
		super(message);
	}

	public GamificationException(Throwable cause) {
		super(cause);
	}
	
}
