package br.les.opus.dengue.core.fields;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.les.opus.dengue.core.i18n.annotations.I18nId;
import br.les.opus.dengue.core.i18n.annotations.LocalizableEntity;
import br.les.opus.dengue.core.i18n.annotations.LocalizableProperty;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@LocalizableEntity
@Table(name = "field")
@Inheritance(strategy=InheritanceType.JOINED)
public class Field {

	@Id
	@I18nId
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_FIELD")
	private Long id;
	
	@LocalizableProperty
	@Column(unique = true, nullable = false)
	private String name;
	
	@LocalizableProperty
	private String helpText;

	@Column(nullable = false)
	private Boolean required;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private FieldType type;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
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
		Field other = (Field) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
