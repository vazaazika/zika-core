package br.les.opus.dengue.core.i18n.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.les.opus.dengue.core.fields.FieldOption;
import br.les.opus.dengue.core.fields.FieldValue;
import br.les.opus.dengue.core.fields.SelectField;
import br.les.opus.dengue.core.i18n.I18nExpection;
import br.les.opus.dengue.core.i18n.I18nMetaField;
import br.les.opus.dengue.core.repositories.FieldValueRepository;

@Component
@Transactional
public class FieldValueMetaFieldsProvider implements MetaFieldProvider {
	
	@Autowired
	private FieldValueRepository fieldValueDao;

	@Override
	public List<I18nMetaField> getMetaFields(Object obj) throws I18nExpection {
		try {
			FieldValue fieldValue = (FieldValue)obj;
			FieldOption option = fieldValueDao.findSelectedOption(fieldValue);
			if (option == null) {
				return new ArrayList<>();
			}
			
			I18nMetaField metaField = new I18nMetaField();
			metaField.setObjectId(option.getId().toString());
			metaField.setClassFqn("br.les.opus.dengue.core.fields.FieldOption");
			metaField.setField(obj.getClass().getDeclaredField("value"));
			metaField.setFieldName("label");
			
			return Arrays.asList(metaField);
		} catch (Exception e) {
			throw new I18nExpection(e.getMessage(), e);
		} 
	}

	@Override
	public boolean canProvide(Object obj) {
		if (obj instanceof FieldValue) {
			FieldValue fieldValue = (FieldValue)obj;
			return fieldValue.getField() instanceof SelectField;
		}
		return false;
	}

}
