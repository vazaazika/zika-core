package br.les.opus.dengue.core.i18n;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class I18nMetaField {

	@Column(nullable = false, name = "class_fqn")
	private String classFqn;
	
	@Column(nullable = false, name = "field_name")
	private String fieldName;
	
	@Column(nullable = false, name = "object_id")
	private String objectId;
	
	@Transient
	@JsonIgnore
	private Field field;

	public String getClassFqn() {
		return classFqn;
	}

	public void setClassFqn(String classFqn) {
		this.classFqn = classFqn;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
}
