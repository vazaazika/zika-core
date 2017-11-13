package br.les.opus.commons.persistence.filtering.converters;

import java.text.ParseException;

import br.les.opus.commons.persistence.date.IsoDateFormat;
import br.les.opus.commons.persistence.filtering.FilteringException;

public class DateConverter implements ValueConverter {
	
	private IsoDateFormat isoDateFormat;
	
	public DateConverter() {
		isoDateFormat = new IsoDateFormat();
	}

	@Override
	public Object convert(String value) {
		try {
			return isoDateFormat.parse(value);
		} catch (ParseException e) {
			throw new FilteringException(e.getMessage(), e);
		}
	}

}
