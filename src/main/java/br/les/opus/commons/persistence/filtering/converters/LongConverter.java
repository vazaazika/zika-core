package br.les.opus.commons.persistence.filtering.converters;

public class LongConverter implements ValueConverter {

	@Override
	public Object convert(String value) {
		return Long.valueOf(value);
	}

}
