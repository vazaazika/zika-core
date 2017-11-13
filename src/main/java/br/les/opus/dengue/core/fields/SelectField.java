package br.les.opus.dengue.core.fields;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.les.opus.dengue.core.i18n.annotations.LocalizableEntity;

@Entity
@LocalizableEntity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "select_field")
@PrimaryKeyJoinColumn(name="field_id")
public class SelectField extends Field {
	
	@OneToMany(mappedBy = "field")
	@OrderBy("label ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FieldOption> options;

	public List<FieldOption> getOptions() {
		return options;
	}

	public void setOptions(List<FieldOption> options) {
		this.options = options;
	}
	
	
}
