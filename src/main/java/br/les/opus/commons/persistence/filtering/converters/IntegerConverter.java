package br.les.opus.commons.persistence.filtering.converters;

public class IntegerConverter implements ValueConverter {

	@Override
	public Object convert(String value) {
		return Integer.valueOf(value);
	}

}
