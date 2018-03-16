package br.les.opus.dengue.core.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.les.opus.commons.persistence.IdAware;

@Entity
@Table(name = "poi_status_update_type")
public class PoiStatusUpdateType implements IdAware<Long> {

	@Id
	private Long id;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
