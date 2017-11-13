package br.les.opus.dengue.core.i18n;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.dengue.core.i18n.providers.LanguageProvider;
import br.les.opus.dengue.core.i18n.providers.MetaFieldProvider;
import br.les.opus.dengue.core.i18n.providers.MetaFieldProviderFactory;
import br.les.opus.dengue.core.repositories.TranslatedValueRepository;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractInternationalizer implements Internationalizer {

	@Autowired
	private TranslatedValueRepository translatedValueDao;
	
	private static Set<Class<?>> ignorableClasses;
	
	private FieldHelper fieldHelper;
	
	@Autowired
	private LanguageProvider languageProvider;
	
	@Autowired
	private MetaFieldProviderFactory providerFactory;
	
	static {
		ignorableClasses = new HashSet<>();
		ignorableClasses.add(Byte.class);
		ignorableClasses.add(Short.class);
		ignorableClasses.add(Integer.class);
		ignorableClasses.add(Long.class);
		ignorableClasses.add(Float.class);
		ignorableClasses.add(Double.class);
		ignorableClasses.add(Character.class);
		ignorableClasses.add(String.class);
		ignorableClasses.add(Boolean.class);
	}
	
	public AbstractInternationalizer() {
		this.fieldHelper = new FieldHelper();
	}
	
	private void translateFields(Object obj) throws I18nExpection {
		try {
			String lang = languageProvider.getUserLanguage();
			MetaFieldProvider provider = providerFactory.create(obj);
			List<I18nMetaField> metaFields = provider.getMetaFields(obj);
			for (I18nMetaField i18nMetaField : metaFields) {
				TranslatedValue translatedValue = translatedValueDao.findOne(i18nMetaField, lang);
				if (translatedValue != null) {
					Field field = i18nMetaField.getField();
					field.setAccessible(true);
					field.set(obj, translatedValue.getTranslation());
				}
			}
		} catch (Exception e) {
			throw new I18nExpection(e.getMessage(), e);
		}
	}

	private boolean shouldBeVisited(Field field)  {
		Class<?> type = field.getType();
		return !ignorableClasses.contains(type) && !type.isPrimitive() && !field.isAnnotationPresent(JsonIgnore.class);
	}

	@Override
	public void internationalize(Object obj) throws I18nExpection {
		try {
			Queue<Object> queue = new LinkedList<>();
			Set<Object> visited = new HashSet<>();
			queue.add(obj);
			visited.add(obj);
			while (!queue.isEmpty()) {
				Object current = queue.poll();
				if (fieldHelper.isLocalizable(current)) {
					translateFields(current);
				}
				List<Field> currentFields = fieldHelper.getAllFields(current);
				for (Field field : currentFields) {
					if (!shouldBeVisited(field)) {
						continue;
					}
					List<Object> values = fieldHelper.getAllFieldValues(field, current);
					for (Object fieldValue : values) {
						if (fieldValue != null && !visited.contains(fieldValue)) {
							queue.add(fieldValue);
							visited.add(fieldValue);
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new I18nExpection(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new I18nExpection(e.getMessage(), e);
		}
	}
}
