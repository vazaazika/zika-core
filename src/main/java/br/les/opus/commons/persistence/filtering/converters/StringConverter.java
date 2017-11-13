package br.les.opus.commons.persistence.filtering.converters;

public class StringConverter implements ValueConverter {

	@Override
	public Object convert(String value) {
		return value;
	}

}
