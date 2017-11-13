package br.les.opus.dengue.core.i18n.providers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.les.opus.dengue.core.i18n.FieldHelper;
import br.les.opus.dengue.core.i18n.I18nExpection;
import br.les.opus.dengue.core.i18n.I18nMetaField;
import br.les.opus.dengue.core.i18n.annotations.I18nId;
import br.les.opus.dengue.core.i18n.annotations.LocalizableProperty;

@Component
public class DefaultMetaFieldProvider implements MetaFieldProvider {
	
	private FieldHelper fieldHelper;
	
	public DefaultMetaFieldProvider() {
		fieldHelper = new FieldHelper();
	}
	
	public List<I18nMetaField> getMetaFields(Object obj) throws I18nExpection {
		try {
			if (!fieldHelper.isLocalizable(obj)) {
				throw new I18nExpection(obj + " is not a LocalizableEntity");
			}

			/*
			 * Capture all fields and try to get the I18nId field
			 */
			List<Field> fields = fieldHelper.getAllFields(obj);
			Field idField = fieldHelper.findAnnotatedField(fields, I18nId.class);
			if (idField == null) {
				throw new I18nExpection(obj + " does not have a I18nId annotated field");
			}

			idField.setAccessible(true);
			String objectId = idField.get(obj).toString();
			String classFqn = obj.getClass().getName();
			List<Field> fieldsToTranslate = fieldHelper.findAnnotatedFields(fields, LocalizableProperty.class);
			List<I18nMetaField> metaFields = new ArrayList<I18nMetaField>();
			for (Field field : fieldsToTranslate) {
				field.setAccessible(true);
				I18nMetaField metaField = new I18nMetaField();
				metaField.setField(field);
				metaField.setFieldName(field.getName());
				metaField.setClassFqn(classFqn);
				metaField.setObjectId(objectId);
				metaFields.add(metaField);
			}
			return metaFields;
		} catch (Exception e) {
			throw new I18nExpection(e.getMessage(), e);
		}
	}

	@Override
	public boolean canProvide(Object obj) {
		return true;
	}
}
