package br.les.opus.dengue.core.i18n;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import br.les.opus.dengue.core.i18n.annotations.LocalizableEntity;

@Component
public class FieldHelper {

	public Field findAnnotatedField(List<Field> fields, Class<? extends Annotation> annotationClass) {
		for (Field field : fields) {
			if (field.isAnnotationPresent(annotationClass)) {
				return field;
			}
		}
		return null;
	}

	public List<Field> findAnnotatedFields(List<Field> fields, Class<? extends Annotation> annotationClass) {
		List<Field> annotatedFields = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(annotationClass)) {
				annotatedFields.add(field);
			}
		}
		return annotatedFields;
	}

	public boolean isLocalizable(Object obj) {
		return obj.getClass().isAnnotationPresent(LocalizableEntity.class);
	}
	
	/**
	 * Returns all entity fields along with the owner object. All fields
	 * of the hierarchy is returned
	 * @param obj
	 * @return
	 */
	public List<Field> getAllFields(Object obj) {
		List<Field> neighbors = new ArrayList<>();
		Class<?> clazz = obj.getClass();
		while (!clazz.equals(Object.class)) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				neighbors.add(field);
			}
			clazz = clazz.getSuperclass();
		}
		return neighbors;
	}

	public List<Object> getAllFieldValues(Field field, Object owner) throws IllegalAccessError, IllegalAccessException {
		List<Object> values = new ArrayList<>();
		field.setAccessible(true);
		if (field.get(owner) instanceof Collection) {
			Collection<?> collection = (Collection<?>)field.get(owner);
			values.addAll(collection);
		} else {
			values.add(field.get(owner));
		}
		return values;
	}
}
