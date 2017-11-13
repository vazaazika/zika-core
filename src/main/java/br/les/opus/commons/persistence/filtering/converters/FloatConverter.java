package br.les.opus.commons.persistence.filtering.converters;

public class FloatConverter implements ValueConverter {

	@Override
	public Object convert(String value) {
		return Float.valueOf(value);
	}

}