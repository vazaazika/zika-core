package br.les.opus.commons.persistence.filtering.converters;

public class BooleanConverter implements ValueConverter {

	@Override
	public Object convert(String value) {
		return Boolean.valueOf(value);
	}

}