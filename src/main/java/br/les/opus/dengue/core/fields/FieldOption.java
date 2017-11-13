package br.les.opus.dengue.core.fields;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.les.opus.dengue.core.i18n.annotations.I18nId;
import br.les.opus.dengue.core.i18n.annotations.LocalizableEntity;
import br.les.opus.dengue.core.i18n.annotations.LocalizableProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@LocalizableEntity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "field_option")
public class FieldOption {

	@Id
	@I18nId
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_FIELD_OPTION")
	private Long id;
	
	@LocalizableProperty
	private String label;
	
	@LocalizableProperty
	private String value;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "field_id")
	private SelectField field;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SelectField getField() {
		return field;
	}

	public void setField(SelectField field) {
		this.field = field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldOption other = (FieldOption) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
