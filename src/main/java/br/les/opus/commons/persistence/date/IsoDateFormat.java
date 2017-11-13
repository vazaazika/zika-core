package br.les.opus.commons.persistence.date;

import java.text.SimpleDateFormat;

public class IsoDateFormat extends SimpleDateFormat {
	
	private static final long serialVersionUID = -638918164127472151L;
	
	private static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public IsoDateFormat() {
		super(ISO_DATE_FORMAT);
	}
}
