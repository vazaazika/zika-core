package br.les.opus.dengue.core.i18n;

public class I18nExpection extends RuntimeException {

	private static final long serialVersionUID = -5223491051947011129L;

	public I18nExpection(String message, Throwable cause) {
		super(message, cause);
	}

	public I18nExpection(String message) {
		super(message);
	}

	public I18nExpection(Throwable cause) {
		super(cause);
	}

	
}
