package br.les.opus.commons.persistence.filtering;

import java.lang.reflect.Method;

import br.les.opus.commons.persistence.filtering.converters.ValueConverter;

public class FieldValueConverter {
	
	private ValueConverter getConverter(Class<?> fieldClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String converterName = "br.les.opus.commons.persistence.filtering.converters." + fieldClass.getSimpleName() + "Converter";
		return (ValueConverter)Class.forName(converterName).newInstance();
	}
	
	private String getNextGetterName(String fieldPath) {
		String[] pathEntries = fieldPath.split("\\.");
		return "get" + new String(pathEntries[0].charAt(0) + "").toUpperCase() +  pathEntries[0].substring(1);
	}
	
	private String getNextPath(String fieldPath) {
		int index = fieldPath.indexOf(".");
		if (index != -1) {
			return fieldPath.substring(index + 1);
		} else {
			return fieldPath;
		}
	}
	
	/**
	 * Convertemos o valor em String de value para um objeto da sua 
	 * classe que está no campo field da classe clazz
	 * 
	 * @param field nome do campo que terá seu valor convertido
	 * @param value valor recebido como String do campo
	 * @param clazz classe onde o campo está inserido. Usamos essa classe
	 * para descobrir o tipo do objeto de field que está na classe clazz
	 * @return o valor do campo value convertido para um objeto do seu tipo da classe
	 */
	public Object convert(String field, String value, Class<?> clazz) {
		try {
			String fieldPath = field;
			Class<?> targetClass = clazz;
			while (fieldPath.contains(".")) {
				String methodName = getNextGetterName(fieldPath);
				Method method = targetClass.getMethod(methodName);
				targetClass = method.getReturnType();
				fieldPath = getNextPath(fieldPath);
			}
			
			Class<?> fieldClass = targetClass.getDeclaredField(fieldPath).getType();
			ValueConverter converter = this.getConverter(fieldClass);
			return converter.convert(value);
		} catch (Exception e) {
			throw new FilteringException(e.getMessage(), e);
		}
	}
}
