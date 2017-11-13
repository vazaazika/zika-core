package br.les.opus.dengue.core.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonView;

import br.les.opus.commons.persistence.IdAware;
import br.les.opus.dengue.core.fields.Field;
import br.les.opus.dengue.core.i18n.annotations.I18nId;
import br.les.opus.dengue.core.i18n.annotations.LocalizableEntity;
import br.les.opus.dengue.core.i18n.annotations.LocalizableProperty;
import br.les.opus.dengue.core.json.View;

@Entity
@LocalizableEntity
@Table(name = "poi_type")
public class PoiType implements IdAware<Long> {

	@Id
	@I18nId
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_POI_TYPE")
	private Long id;
	
	@LocalizableProperty
	private String name;
	
	@ManyToMany  
	@JsonView(View.Detail.class)
	@LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="poi_type_fields", 
    	joinColumns=@JoinColumn(name="poi_type_id"), 
    	inverseJoinColumns=@JoinColumn(name="field_id")
    )  
	private List<Field> fields;

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

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
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
		PoiType other = (PoiType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
