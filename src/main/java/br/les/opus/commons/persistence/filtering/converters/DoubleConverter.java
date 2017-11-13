package br.les.opus.commons.persistence.filtering.converters;

public class DoubleConverter implements ValueConverter {

	@Override
	public Object convert(String value) {
		return Double.valueOf(value);
	}

}